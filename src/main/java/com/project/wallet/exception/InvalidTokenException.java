package com.project.wallet.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String s) {
        super(s);
    }
}
