package com.bichomania.clinicavet.domain.shared.valueobjects;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.guardian.InvalidGuardianException;
import com.bichomania.clinicavet.common.validator.CpfValidator;

public record Cpf(String value) {

    private static final String CPF_INVALID = ExceptionMessages.GUARDIAN_CPF_INVALID;

    public Cpf {
        String cleaned = CpfValidator.clean(value);
        if (!CpfValidator.isValid(cleaned)) {
            throw new InvalidGuardianException(CPF_INVALID);
        }
        value = cleaned;
    }

    public String getFormatted() {
        return CpfValidator.format(value);
    }
}

