package com.bichomania.clinicavet.domain.shared.valueobjects;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AddressTest {

    @Test
    void deveCriarEnderecoComCep() {
        Address address = new Address("São Paulo", "SP", "01310100",
                "Av Paulista 1000", true);

        assertThat(address.getCity()).isEqualTo("São Paulo");
        assertThat(address.getState()).isEqualTo("SP");
        assertThat(address.getCep()).isEqualTo("01310100");
        assertThat(address.getFormattedCep()).isEqualTo("01310-100");
        assertThat(address.isPrincipal()).isTrue();
    }

    @Test
    void deveCriarEnderecoSemCep() {
        Address address = new Address("Rio de Janeiro", "RJ",
                "Copacabana", true);

        assertThat(address.getCity()).isEqualTo("Rio de Janeiro");
        assertThat(address.getCep()).isNull();
    }

    @Test
    void deveNormalizarEstadoParaMaiusculo() {
        Address address = new Address("Curitiba", "pr", "80000000", null, false);

        assertThat(address.getState()).isEqualTo("PR");
    }

    @Test
    void deveLimparCep() {
        Address address = new Address("São Paulo", "SP", "01310-100", null, true);

        assertThat(address.getCep()).isEqualTo("01310100");
    }

    @Test
    void deveRejeitarCidadeVazia() {
        assertThatThrownBy(() ->
                new Address("", "SP", "01310100", null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cidade");
    }

    @Test
    void deveRejeitarCidadeNula() {
        assertThatThrownBy(() ->
                new Address(null, "SP", "01310100", null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("obrigatória");
    }

    @Test
    void deveRejeitarCidadeMuitoLonga() {
        String cidadeLonga = "a".repeat(101);

        assertThatThrownBy(() ->
                new Address(cidadeLonga, "SP", null, null, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("100 caracteres");
    }

    @Test
    void deveRejeitarCepInvalido() {
        assertThatThrownBy(() ->
                new Address("São Paulo", "SP", "123", null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CEP");
    }

    @Test
    void enderecosIguaisDevemSerIguais() {
        Address a1 = new Address("São Paulo", "SP", "01310100", "Detalhe A", true);
        Address a2 = new Address("São Paulo", "SP", null, "Detalhe A", false);

        assertThat(a1).isEqualTo(a2);
    }
}