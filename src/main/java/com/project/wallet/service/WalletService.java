package com.project.wallet.service;

import com.project.wallet.entity.Wallet;
import com.project.wallet.entity.WalletEvent;
import com.project.wallet.exception.AmountNotValidException;
import com.project.wallet.exception.EmptyListException;
import com.project.wallet.repository.WalletRepository;
import com.project.wallet.repository.WalletStatementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@Slf4j
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletStatementRepository walletStatementRepository;

    public Wallet getBalanceById(String id){
        var balance = walletRepository.findById(id).orElseThrow(() -> new NotFoundException("Nenhuma carteira informada com o cpf informado"));
        return Wallet
                .builder()
                .balance(balance.getBalance())
                .build();
    }

    public Wallet getStatement(String fromDate, String toDate, String cpf){
        var eventList = walletStatementRepository.findByCpfAndTransactionDateBetween(cpf,fromDate,toDate);
        if(eventList.isEmpty()){
            throw new EmptyListException("Nenhum registro a ser mostrado no período informado");
        }
        return  Wallet.
                builder().
                events(eventList)
                .build();
    }

    public void calculateBalance(Wallet wallet, WalletEvent walletEvent){
        Double balance = wallet.getBalance();

        switch (walletEvent.getTransactionType()) {
            case ADICAO, CANCELAMENTO, ESTORNO -> {
                setId(wallet, walletEvent);
                wallet.setBalance(balance + walletEvent.getAmount());
                walletRepository.save(wallet);
            }
            case RETIRADA, COMPRA -> {
                if(balance < walletEvent.getAmount()) {
                    throw new AmountNotValidException("Saldo insuficiente para realizar a transaçao");
                }
                setId(wallet, walletEvent);
                wallet.setBalance(balance - walletEvent.getAmount());
                walletRepository.save(wallet);
            }
            default -> {
                log.error("Tipo de transaçao nao encontrada");
                setId(wallet, walletEvent);
                wallet.setBalance(balance);
                walletRepository.save(wallet);
            }
        }
    }
    private static void setId(Wallet wallet, WalletEvent walletEvent) {
        wallet.setId(walletEvent.getCpf());
    }
}
