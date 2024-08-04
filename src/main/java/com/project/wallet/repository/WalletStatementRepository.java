package com.project.wallet.repository;

import com.project.wallet.entity.WalletEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WalletStatementRepository extends JpaRepository<WalletEvent, UUID> {

//    @Query(value = "SELECT * FROM WALLET_EVENT t WHERE t.transaction_date BETWEEN :fromDate AND :toDate", nativeQuery = true)
//    List<WalletEvent> findByTransactionDateBetween(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query(value = "SELECT * FROM WALLET_EVENT t WHERE t.cpf = :cpf AND t.transaction_date BETWEEN :fromDate AND :toDate", nativeQuery = true)
    List<WalletEvent> findByCpfAndTransactionDateBetween(
            @Param("cpf") String cpf,
            @Param("fromDate") String fromDate,
            @Param("toDate") String toDate
    );

}
