package com.bichomania.clinicavet.domain.guardian;

import com.bichomania.clinicavet.common.exception.guardian.InvalidGuardianException;
import com.bichomania.clinicavet.common.types.ContactType;
import com.bichomania.clinicavet.domain.shared.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class GuardianTest {

    // ========== CRIAÇÃO ==========

    @Test
    void deveCriarGuardianValidoComUmContato() {
        Guardian guardian = Guardian.create(
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        );

        assertThat(guardian.getId()).isNotNull();
        assertThat(guardian.getName()).isEqualTo("João Silva");
        assertThat(guardian.getCpf().value()).isEqualTo("12345678909");
        assertThat(guardian.getEmail().getValue()).isEqualTo("joao@email.com");
        assertThat(guardian.isActive()).isTrue();
        assertThat(guardian.getCreatedAt()).isNotNull();
        assertThat(guardian.getUpdatedAt()).isNotNull();
    }

    @Test
    void deveCriarGuardianComMultiplosContatos() {
        Guardian guardian = Guardian.create(
                "Maria Santos",
                new Cpf("98765432100"),
                new Email("maria@email.com"),
                Set.of(
                        new Contact(ContactType.WHATSAPP, "11987654321", true),
                        new Contact(ContactType.MOBILE, "11999998888", false),
                        new Contact(ContactType.LANDLINE, "1133334444", false)
                ),
                Set.of()
        );

        assertThat(guardian.getContacts()).hasSize(3);
    }

    @Test
    void deveCriarGuardianComEnderecos() {
        Guardian guardian = Guardian.create(
                "Pedro Oliveira",
                new Cpf("11122233344"),
                new Email("pedro@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of(
                        new Address("São Paulo", "SP", "01310100", "Av Paulista", true),
                        new Address("Rio de Janeiro", "RJ", "20040020", "Copacabana", false)
                )
        );

        assertThat(guardian.getAddresses()).hasSize(2);
    }

    // ========== VALIDAÇÕES DE NOME ==========

    @Test
    void deveRejeitarNomeNulo() {
        assertThatThrownBy(() -> Guardian.create(
                null,
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        )).isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("Nome do tutor é obrigatório");
    }

    @Test
    void deveRejeitarNomeVazio() {
        assertThatThrownBy(() -> Guardian.create(
                "",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        )).isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("Nome do tutor é obrigatório");
    }

    @Test
    void deveRejeitarNomeApenasEspacos() {
        assertThatThrownBy(() -> Guardian.create(
                "   ",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        )).isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("obrigatório");
    }

    @Test
    void deveRejeitarNomeMuitoLongo() {
        String nomeLongo = "a".repeat(101);

        assertThatThrownBy(() -> Guardian.create(
                nomeLongo,
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        )).isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("100 caracteres");
    }

    @Test
    void deveAceitarNomeCom100Caracteres() {
        String nomeMaximo = "a".repeat(100);

        Guardian guardian = Guardian.create(
                nomeMaximo,
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        );

        assertThat(guardian.getName()).hasSize(100);
    }

    @Test
    void deveRemoverEspacosExtrasDosNomes() {
        Guardian guardian = Guardian.create(
                "  João   Silva  ",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        );

        assertThat(guardian.getName()).isEqualTo("João   Silva");
    }

    // ========== VALIDAÇÕES DE CONTATO ==========

    @Test
    void deveRejeitarSemContatos() {
        assertThatThrownBy(() -> Guardian.create(
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(),
                Set.of()
        )).isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("pelo menos um contato");
    }

    @Test
    void deveRejeitarContatosNulos() {
        assertThatThrownBy(() -> Guardian.create(
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                null,
                Set.of()
        )).isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("contato");
    }

    @Test
    void deveRejeitarSemContatoPrincipal() {
        assertThatThrownBy(() -> Guardian.create(
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(
                        new Contact(ContactType.WHATSAPP, "11987654321", false),
                        new Contact(ContactType.MOBILE, "11999998888", false)
                ),
                Set.of()
        )).isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("exatamente um contato principal");
    }

    @Test
    void deveRejeitarMaisDeUmContatoPrincipal() {
        assertThatThrownBy(() -> Guardian.create(
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(
                        new Contact(ContactType.WHATSAPP, "11987654321", true),
                        new Contact(ContactType.MOBILE, "11999998888", true)
                ),
                Set.of()
        )).isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("exatamente um contato principal");
    }

    // ========== COMPORTAMENTOS ==========

    @Test
    void deveAtualizarNome() {
        Guardian guardian = criarGuardianValido();

        guardian.updateName("Maria Santos");

        assertThat(guardian.getName()).isEqualTo("Maria Santos");
        assertThat(guardian.getUpdatedAt()).isAfter(guardian.getCreatedAt());
    }

    @Test
    void deveRejeitarAtualizacaoComNomeInvalido() {
        Guardian guardian = criarGuardianValido();

        assertThatThrownBy(() -> guardian.updateName(""))
                .isInstanceOf(InvalidGuardianException.class);
    }

    @Test
    void deveAtualizarEmail() {
        Guardian guardian = criarGuardianValido();

        guardian.updateEmail(new Email("novo@email.com"));

        assertThat(guardian.getEmail().getValue()).isEqualTo("novo@email.com");
        assertThat(guardian.getUpdatedAt()).isAfter(guardian.getCreatedAt());
    }

    @Test
    void deveRejeitarEmailNuloNaAtualizacao() {
        Guardian guardian = criarGuardianValido();

        assertThatThrownBy(() -> guardian.updateEmail(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void deveAtualizarContatos() {
        Guardian guardian = criarGuardianValido();

        Set<Contact> novosContatos = Set.of(
                new Contact(ContactType.MOBILE, "11999998888", true),
                new Contact(ContactType.LANDLINE, "1133334444", false)
        );

        guardian.updateContacts(novosContatos);

        assertThat(guardian.getContacts()).hasSize(2);
    }

    @Test
    void deveRejeitarAtualizacaoContatosSemPrincipal() {
        Guardian guardian = criarGuardianValido();

        assertThatThrownBy(() -> guardian.updateContacts(
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", false))
        )).isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("exatamente um contato principal");
    }

    @Test
    void deveAtualizarEnderecos() {
        Guardian guardian = criarGuardianValido();

        Set<Address> novosEnderecos = Set.of(
                new Address("Curitiba", "PR", "80000000", "Centro", true)
        );

        guardian.updateAddresses(novosEnderecos);

        assertThat(guardian.getAddresses()).hasSize(1);
    }

    @Test
    void deveDesativarGuardian() {
        Guardian guardian = criarGuardianValido();

        guardian.deactivate();

        assertThat(guardian.isActive()).isFalse();
        assertThat(guardian.getUpdatedAt()).isAfter(guardian.getCreatedAt());
    }

    @Test
    void deveRejeitarDesativarGuardianJaInativo() {
        Guardian guardian = criarGuardianValido();
        guardian.deactivate();

        assertThatThrownBy(guardian::deactivate)
                .isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("já está inativo");
    }

    @Test
    void deveReativarGuardian() {
        Guardian guardian = criarGuardianValido();
        guardian.deactivate();

        guardian.reactivate();

        assertThat(guardian.isActive()).isTrue();
        assertThat(guardian.getUpdatedAt()).isAfter(guardian.getCreatedAt());
    }

    @Test
    void deveRejeitarReativarGuardianJaAtivo() {
        Guardian guardian = criarGuardianValido();

        assertThatThrownBy(guardian::reactivate)
                .isInstanceOf(InvalidGuardianException.class)
                .hasMessageContaining("já está ativo");
    }

    @Test
    void deveRetornarContatoPrincipal() {
        Contact principal = new Contact(ContactType.WHATSAPP, "11987654321", true);
        Guardian guardian = Guardian.create(
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(
                        principal,
                        new Contact(ContactType.MOBILE, "11999998888", false)
                ),
                Set.of()
        );

        Contact resultado = guardian.getPrincipalContact();

        assertThat(resultado).isEqualTo(principal);
    }

    // ========== IMUTABILIDADE ==========

    @Test
    void collectionsDevemSerImutaveis() {
        Guardian guardian = criarGuardianValido();

        assertThatThrownBy(() -> guardian.getContacts().clear())
                .isInstanceOf(UnsupportedOperationException.class);

        assertThatThrownBy(() -> guardian.getAddresses().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void cpfDeveSerImutavel() {
        Guardian guardian = criarGuardianValido();
        Cpf cpfOriginal = guardian.getCpf();

        assertThat(guardian.getCpf()).isEqualTo(cpfOriginal);
    }

    // ========== RECONSTITUTE ==========

    @Test
    void deveReconstituirGuardianComEstadoCompleto() {
        UUID id = UUID.randomUUID();

        Guardian guardian = Guardian.reconstitute(
                id,
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of(),
                true,
                null,
                null,
                1L
        );

        assertThat(guardian.getId()).isEqualTo(id);
        assertThat(guardian.getVersion()).isEqualTo(1L);
        assertThat(guardian.isActive()).isTrue();
    }

    @Test
    void deveReconstituirGuardianInativo() {
        Guardian guardian = Guardian.reconstitute(
                UUID.randomUUID(),
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of(),
                false,
                null,
                null,
                null
        );

        assertThat(guardian.isActive()).isFalse();
    }

    // ========== EQUALS/HASHCODE ==========

    @Test
    void guardiansComMesmoIdDevemSerIguais() {
        UUID id = UUID.randomUUID();

        Guardian g1 = Guardian.reconstitute(
                id, "João", new Cpf("12345678909"), new Email("j@e.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of(), true, null, null, null
        );

        Guardian g2 = Guardian.reconstitute(
                id, "Maria", new Cpf("98765432100"), new Email("m@e.com"),
                Set.of(new Contact(ContactType.MOBILE, "11999998888", true)),
                Set.of(), true, null, null, null
        );

        assertThat(g1).isEqualTo(g2);
        assertThat(g1.hashCode()).isEqualTo(g2.hashCode());
    }

    @Test
    void guardiansComIdsdiferentesDevemSerDiferentes() {
        Guardian g1 = criarGuardianValido();
        Guardian g2 = criarGuardianValido();

        assertThat(g1).isNotEqualTo(g2);
    }

    // ========== HELPERS ==========

    private Guardian criarGuardianValido() {
        return Guardian.create(
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        );
    }
}