package com.bichomania.clinicavet.common.exception.guardian;

import com.bichomania.clinicavet.common.exception.BaseException;

public class DuplicateCpfException extends BaseException {
    public DuplicateCpfException(String cpf) {
        super(String.format("CPF já cadastrado: %s", cpf));
    }
}