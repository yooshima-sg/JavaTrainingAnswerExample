package com.s_giken.training.webapp.exception;

public class TooManyResultException extends RuntimeException {
    public TooManyResultException(String msg) {
        super(msg);
    }
}
