package com.project.wallet.service;

import com.project.wallet.entity.Wallet;
import com.project.wallet.entity.WalletEvent;
import com.project.wallet.enuns.EnumTransactionType;
import com.project.wallet.exception.AmountNotValidException;
import com.project.wallet.exception.EmptyListException;
import com.project.wallet.repository.WalletRepository;
import com.project.wallet.repository.WalletStatementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletStatementRepository walletStatementRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBalanceById_Sucesso() {
        Wallet wallet = Wallet.builder().id("123").balance(100.0).build();
        when(walletRepository.findById("123")).thenReturn(Optional.of(wallet));

        Wallet result = walletService.getBalanceById("123");

        assertEquals(100.0, result.getBalance());
    }

    @Test
    void testGetBalanceById_NotFound() {
        when(walletRepository.findById("123")).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            walletService.getBalanceById("123");
        });

        assertEquals("Nenhuma carteira informada com o cpf informado", exception.getMessage());
    }

    @Test
    void testGetStatement_Sucesso() {
        WalletEvent walletEvent = WalletEvent.builder().build();
        List<WalletEvent> events = new ArrayList<>();
        events.add(walletEvent);
        when(walletStatementRepository.findByCpfAndTransactionDateBetween("123", "2024-01-01", "2024-12-31"))
                .thenReturn(events);

        Wallet result = walletService.getStatement("2024-01-01", "2024-12-31", "123");

        assertNotNull(result.getEvents());
        assertFalse(result.getEvents().isEmpty());
    }

    @Test
    void testGetStatement_SemRegistros() {
        when(walletStatementRepository.findByCpfAndTransactionDateBetween("123", "2024-01-01", "2024-12-31"))
                .thenReturn(new ArrayList<>());

        Exception exception = assertThrows(EmptyListException.class, () -> {
            walletService.getStatement("2024-01-01", "2024-12-31", "123");
        });

        assertEquals("Nenhum registro a ser mostrado no período informado", exception.getMessage());
    }

    @Test
    void testCalculateBalance_Adicao() {
        Wallet wallet = Wallet.builder().id("123").balance(100.0).build();
        WalletEvent walletEvent = WalletEvent.builder()
                .transactionType(EnumTransactionType.ADICAO)
                .amount(50.0)
                .cpf("123")
                .build();
        when(walletRepository.findById("123")).thenReturn(Optional.of(wallet));

        walletService.calculateBalance(wallet, walletEvent);

        assertEquals(150.0, wallet.getBalance());
        verify(walletRepository).save(wallet);
    }

    @Test
    void testCalculateBalance_Estorno() {
        Wallet wallet = Wallet.builder().id("123").balance(100.0).build();
        WalletEvent walletEvent = WalletEvent.builder()
                .transactionType(EnumTransactionType.ESTORNO)
                .amount(50.0)
                .cpf("123")
                .build();
        when(walletRepository.findById("123")).thenReturn(Optional.of(wallet));

        walletService.calculateBalance(wallet, walletEvent);

        assertEquals(150.0, wallet.getBalance());
        verify(walletRepository).save(wallet);
    }

    @Test
    void testCalculateBalance_SaldoInsuficiente() {
        Wallet wallet = Wallet.builder().id("123").balance(30.0).build();
        WalletEvent walletEvent = WalletEvent.builder()
                .transactionType(EnumTransactionType.COMPRA)
                .amount(50.0)
                .cpf("123")
                .build();
        when(walletRepository.findById("123")).thenReturn(Optional.of(wallet));

        Exception exception = assertThrows(AmountNotValidException.class, () -> {
            walletService.calculateBalance(wallet, walletEvent);
        });

        assertEquals("Saldo insuficiente para realizar a transaçao", exception.getMessage());
    }

}
