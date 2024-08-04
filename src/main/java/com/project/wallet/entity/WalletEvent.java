package com.project.wallet.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.wallet.enuns.EnumTransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletEvent {
    @Id
    private UUID id;
    @CPF
    private String cpf;
    private Double amount;
    private EnumTransactionType transactionType;
    private String transactionDate;
}
