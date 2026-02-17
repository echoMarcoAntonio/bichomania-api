package com.bichomania.clinicavet.common.exception.vaccine;

import com.bichomania.clinicavet.common.exception.BaseException;

/**
 * Exceção lançada quando uma vacina contém dados inválidos.
 *
 * Casos de uso:
 * - Nome ou fabricante ausentes/vazios
 * - Campos excedendo tamanhos máximos
 * - Validade em meses fora do intervalo permitido (1-24)
 * - Qualquer violação de regra de negócio do domínio
 */
public class InvalidVaccineException extends BaseException {

    public InvalidVaccineException(String message) {
        super(message);
    }

    public InvalidVaccineException(String message, Throwable cause) {
        super(message, cause);
    }
}