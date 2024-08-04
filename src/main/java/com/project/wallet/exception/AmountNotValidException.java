package com.project.wallet.exception;

import lombok.Getter;

@Getter
public class AmountNotValidException extends RuntimeException {
    public AmountNotValidException(String s) {
        super(s);
    }
}
