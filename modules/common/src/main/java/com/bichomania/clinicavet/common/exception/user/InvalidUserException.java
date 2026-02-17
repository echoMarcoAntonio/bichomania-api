package com.bichomania.clinicavet.common.exception.user;

import com.bichomania.clinicavet.common.exception.BaseException;

public class InvalidUserException extends BaseException {
    public InvalidUserException(String message) {
        super(message);
    }
}