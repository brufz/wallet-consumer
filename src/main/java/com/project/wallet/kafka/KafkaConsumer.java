package com.project.wallet.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.wallet.entity.Wallet;
import com.project.wallet.entity.WalletEvent;
import com.project.wallet.repository.WalletRepository;
import com.project.wallet.repository.WalletStatementRepository;
import com.project.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {
    @Autowired
    private WalletStatementRepository walletStatementRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletService walletService;

    @Value("${topic.name.consumer")
    private String topicName;

    @KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
    public void consume(ConsumerRecord<String, String> payload){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            var walletEvent = objectMapper.readValue(payload.value(), WalletEvent.class);
            verifyDuplicatedEvent(walletEvent);
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar mensagem: formato inválido. Mensagem: {}", payload.value(), e);
        }
    }

    void verifyDuplicatedEvent(WalletEvent walletEvent) {
        if(!walletStatementRepository.existsById(walletEvent.getId())){
            persistOnTableWalletIfNotExist(walletEvent);
            persistEvents(walletEvent);
            walletService.calculateBalance(walletService.getBalanceById(walletEvent.getCpf()), walletEvent);
        }
    }

    private void persistEvents(WalletEvent walletEvent) {
        walletStatementRepository.save(walletEvent);
        log.info("Transaçao id {} salvo com sucesso", walletEvent.getId());
    }

    private void persistOnTableWalletIfNotExist(WalletEvent walletEvent) {
        walletRepository.findById(walletEvent.getCpf()).orElseGet(() -> {
            var wallet = walletRepository.save(Wallet.builder().id(walletEvent.getCpf()).balance(0.0).build());
            log.info("Carteira id {} criada com sucesso", wallet.getId());
            return wallet;
        });
    }
}
