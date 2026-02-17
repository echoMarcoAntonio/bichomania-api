package com.bichomania.clinicavet.common.exception.guardian;

public class DuplicateCpfException extends RuntimeException {
    private final String cpf;

    public DuplicateCpfException(String cpf) {
        super(String.format("Já existe um tutor com o CPF: %s cadastrado.", cpf));
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
