package com.bichomania.clinicavet.common.exception;

public final class ExceptionMessages {

    private ExceptionMessages() {} // evita instanciamento

    public static final String PET_FIELD_REQUIRED = "Campos obrigatórios do pet não foram preenchidos.";
    public static final String PET_BIRTH_DATE_IN_FUTURE = "A data de nascimento não pode ser no futuro.";
}
