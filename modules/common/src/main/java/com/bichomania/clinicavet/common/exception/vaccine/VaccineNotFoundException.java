package com.bichomania.clinicavet.common.exception.vaccine;

import com.bichomania.clinicavet.common.exception.BaseException;

/**
 * Exceção lançada quando uma vacina não é encontrada no sistema.
 *
 * Casos de uso:
 * - Busca por ID que não existe
 * - Tentativa de atualizar vacina inexistente
 * - Tentativa de deletar vacina inexistente
 * - Referência a vacina que foi removida
 */
public class VaccineNotFoundException extends BaseException {

    public VaccineNotFoundException(String message) {
        super(message);
    }

    public VaccineNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}