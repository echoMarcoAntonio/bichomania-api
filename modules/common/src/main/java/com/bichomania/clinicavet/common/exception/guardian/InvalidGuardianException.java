package com.bichomania.clinicavet.common.exception.guardian;

public class InvalidGuardianException extends RuntimeException{
    public InvalidGuardianException(String message) {
        super(message);
    }

    public InvalidGuardianException(String message, Throwable cause) {
        super(message, cause);
    }
}
