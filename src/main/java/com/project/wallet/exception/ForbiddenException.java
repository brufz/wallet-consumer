package com.project.wallet.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String s) {
        super(s);
    }
}
