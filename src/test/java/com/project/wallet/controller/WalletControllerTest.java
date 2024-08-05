package com.project.wallet.controller;

import com.project.wallet.entity.Wallet;
import com.project.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class WalletControllerTest {

    private String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdGF0dXMiOiJBQ1RJVkUiLCJyb2xlIjoiVVNFUiJ9.rtYrujHxaPj9i0eYeRCKVWdHzn76hpZrs3fppvrSfeYeCIQb0Em5k1RbzqCXkXtCtuisgJIZmlSido6G96TJxw";
    String cpf = "43693769800";

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBalance() {
        Wallet wallet = new Wallet(cpf, 100.0);
        when(walletService.getBalanceById(cpf)).thenReturn(wallet);
        ResponseEntity<Wallet> response = walletController.getBalance(token, cpf);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(wallet, response.getBody());
    }

    @Test
    void testGetStatement() {
        String dataInicial = "2024-01-01";
        String dataFinal = "2024-01-31";
        Wallet wallet = new Wallet(cpf, 200.0);
        when(walletService.getStatement(dataInicial, dataFinal, cpf)).thenReturn(wallet);
        ResponseEntity<Wallet> response = walletController.getStatement(token, cpf, dataInicial, dataFinal);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(wallet, response.getBody());
    }
}
