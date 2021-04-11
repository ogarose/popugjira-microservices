package com.ogarose.popugjira.common.types;

public class DomainException extends RuntimeException {
    private final String errorMessage;

    public DomainException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public DomainException(String errorMessage, Throwable err) {
        super(errorMessage, err);
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return String.format("Domain Exception. Message: %s .", this.errorMessage);
    }

}
