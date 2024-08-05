package com.project.wallet.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Wallet {
    @Id
    private String id;
    private Double balance;
    @OneToMany
    private List<WalletEvent> events;

    public Wallet(String id, Double balance) {
        this.id = id;
        this.balance = balance;
    }
}
