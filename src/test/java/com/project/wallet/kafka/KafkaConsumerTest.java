package com.project.wallet.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.wallet.entity.Wallet;
import com.project.wallet.entity.WalletEvent;
import com.project.wallet.enuns.EnumTransactionType;
import com.project.wallet.repository.WalletRepository;
import com.project.wallet.repository.WalletStatementRepository;
import com.project.wallet.service.WalletService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class KafkaConsumerTest {

    @Mock
    private WalletStatementRepository walletStatementRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsumeValidPayload() throws JsonProcessingException {
        WalletEvent walletEvent = new WalletEvent(UUID.randomUUID(), "12345678900", 100.0, EnumTransactionType.ADICAO, LocalDate.now().toString());
        String validPayload = objectMapper.writeValueAsString(walletEvent);
        ConsumerRecord<String, String> record = new ConsumerRecord<>("topic", 0, 0, "key", validPayload);
        when(walletRepository.save(any(Wallet.class))).thenReturn(new Wallet());
        kafkaConsumer.consume(record);
        verify(walletStatementRepository).existsById(walletEvent.getId());
    }

    @Test
    void testVerifyDuplicatedEventNotExist() {
        WalletEvent walletEvent = new WalletEvent(UUID.randomUUID(), "12345678900", 100.0, EnumTransactionType.ADICAO, LocalDate.now().toString());
        when(walletStatementRepository.existsById(walletEvent.getId())).thenReturn(false);
        when(walletRepository.findById(walletEvent.getCpf())).thenReturn(Optional.empty());
        when(walletRepository.save(any(Wallet.class))).thenReturn(new Wallet());
        kafkaConsumer.verifyDuplicatedEvent(walletEvent);
        verify(walletStatementRepository).save(walletEvent);
    }

    @Test
    void testVerifyDuplicatedEventExist() {
        WalletEvent walletEvent = new WalletEvent(UUID.randomUUID(), "12345678900", 100.0, EnumTransactionType.ADICAO, LocalDate.now().toString());
        when(walletStatementRepository.existsById(walletEvent.getId())).thenReturn(true);
        kafkaConsumer.verifyDuplicatedEvent(walletEvent);
        verify(walletStatementRepository, never()).save(walletEvent);
        verify(walletService, never()).calculateBalance(any(Wallet.class), eq(walletEvent));
    }
}

