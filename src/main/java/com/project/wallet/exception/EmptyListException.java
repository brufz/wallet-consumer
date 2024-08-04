package com.project.wallet.exception;

import lombok.Getter;

@Getter
public class EmptyListException extends RuntimeException {
    public EmptyListException(String s) {
        super(s);
    }
}
