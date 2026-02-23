package com.bichomania.clinicavet.common.exception.guardian;

import com.bichomania.clinicavet.common.exception.BaseException;

public class GuardianNotFoundException extends BaseException {
    public GuardianNotFoundException(String message) {
        super(message);
    }
}