package com.bichomania.clinicavet.domain.shared.valueobjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class EmailTest {

    @Test
    void deveCriarEmailValido() {
        Email email = new Email("usuario@exemplo.com");

        assertThat(email.getValue()).isEqualTo("usuario@exemplo.com");
    }

    @Test
    void deveConverterParaMinusculo() {
        Email email = new Email("USUARIO@EXEMPLO.COM");

        assertThat(email.getValue()).isEqualTo("usuario@exemplo.com");
    }

    @Test
    void deveRemoverEspacos() {
        Email email = new Email("  usuario@exemplo.com  ");

        assertThat(email.getValue()).isEqualTo("usuario@exemplo.com");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "   ",
            "invalido",
            "@exemplo.com",
            "usuario@",
            "usuario@.com",
            "usuario exemplo@email.com"
    })
    void deveRejeitarEmailInvalido(String emailInvalido) {
        assertThatThrownBy(() -> new Email(emailInvalido))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("mail");
    }

    @Test
    void deveRejeitarEmailNulo() {
        assertThatThrownBy(() -> new Email(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("obrigatório");
    }

    @Test
    void emailsIguaisDevemSerIguais() {
        Email email1 = new Email("teste@email.com");
        Email email2 = new Email("TESTE@EMAIL.COM");

        assertThat(email1).isEqualTo(email2);
    }
}