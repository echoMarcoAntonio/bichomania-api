package com.bichomania.clinicavet.domain.shared.valueobjects;

import com.bichomania.clinicavet.common.exception.guardian.InvalidGuardianException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CpfTest {

    @Test
    void deveCriarCpfValido() {
        Cpf cpf = new Cpf("12345678909");

        assertThat(cpf.value()).isEqualTo("12345678909");
        assertThat(cpf.getFormatted()).isEqualTo("123.456.789-09");
    }

    @Test
    void deveLimparCpfAoCriar() {
        Cpf cpf = new Cpf("123.456.789-09");

        assertThat(cpf.value()).isEqualTo("12345678909");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "00000000000",
            "11111111111",
            "22222222222",
            "12345678900",
            "123",
            ""
    })
    void deveRejeitarCpfInvalido(String cpfInvalido) {
        assertThatThrownBy(() -> new Cpf(cpfInvalido))
                .isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("CPF inválido");
    }

    @Test
    void deveRejeitarCpfNulo() {
        assertThatThrownBy(() -> new Cpf(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void cpfsIguaisDevemSerIguais() {
        Cpf cpf1 = new Cpf("12345678909");
        Cpf cpf2 = new Cpf("123.456.789-09");

        assertThat(cpf1).isEqualTo(cpf2);
        assertThat(cpf1.hashCode()).isEqualTo(cpf2.hashCode());
    }

    @Test
    void cpfsDiferentesDevemSerDiferentes() {
        Cpf cpf1 = new Cpf("12345678909");
        Cpf cpf2 = new Cpf("98765432100");

        assertThat(cpf1).isNotEqualTo(cpf2);
    }
}