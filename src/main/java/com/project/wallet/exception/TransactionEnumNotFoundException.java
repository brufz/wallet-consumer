package com.project.wallet.exception;

import lombok.Getter;

@Getter
public class TransactionEnumNotFoundException extends RuntimeException {
    public TransactionEnumNotFoundException(String s) {
        super(s);
    }
}
