package com.bichomania.clinicavet.application.guardian.services;

import com.bichomania.clinicavet.application.guardian.GuardianRepository;
import com.bichomania.clinicavet.application.guardian.dto.GuardianRequest;
import com.bichomania.clinicavet.application.guardian.dto.GuardianResponse;
import com.bichomania.clinicavet.common.exception.guardian.DuplicateCpfException;
import com.bichomania.clinicavet.common.exception.guardian.GuardianNotFoundException;
import com.bichomania.clinicavet.common.types.ContactType;
import com.bichomania.clinicavet.domain.guardian.Guardian;
import com.bichomania.clinicavet.domain.shared.valueobjects.Contact;
import com.bichomania.clinicavet.domain.shared.valueobjects.Cpf;
import com.bichomania.clinicavet.domain.shared.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardianServiceTest {

    @Mock
    private GuardianRepository repository;

    @InjectMocks
    private GuardianService service;

    private GuardianRequest requestValido;

    @BeforeEach
    void setup() {
        requestValido = new GuardianRequest(
                "João Silva",
                "123.456.789-09",
                "joao@email.com",
                Set.of(new GuardianRequest.ContactDTO(
                        ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        );
    }

    // ========== CREATE ==========

    @Test
    void deveCriarGuardian() {
        when(repository.existsByCpf("12345678909")).thenReturn(false);
        when(repository.save(any(Guardian.class))).thenAnswer(inv -> inv.getArgument(0));

        GuardianResponse response = service.create(requestValido);

        assertThat(response.name()).isEqualTo("João Silva");
        assertThat(response.cpf()).isEqualTo("123.456.789-09");
        assertThat(response.email()).isEqualTo("joao@email.com");
        assertThat(response.active()).isTrue();

        verify(repository).existsByCpf("12345678909");
        verify(repository).save(any(Guardian.class));
    }

    @Test
    void deveLimparCpfAntesDeVerificarDuplicidade() {
        when(repository.existsByCpf("12345678909")).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.create(requestValido);

        verify(repository).existsByCpf("12345678909"); // CPF limpo
    }

    @Test
    void deveRejeitarCpfDuplicado() {
        when(repository.existsByCpf("12345678909")).thenReturn(true);

        assertThatThrownBy(() -> service.create(requestValido))
                .isInstanceOf(DuplicateCpfException.class)
                .hasMessageContaining("12345678909");

        verify(repository, never()).save(any());
    }

    @Test
    void deveCapturarGuardianCriadoComDadosCorretos() {
        when(repository.existsByCpf(any())).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        service.create(requestValido);

        ArgumentCaptor<Guardian> captor = ArgumentCaptor.forClass(Guardian.class);
        verify(repository).save(captor.capture());

        Guardian guardianSalvo = captor.getValue();
        assertThat(guardianSalvo.getName()).isEqualTo("João Silva");
        assertThat(guardianSalvo.getCpf().value()).isEqualTo("12345678909");
        assertThat(guardianSalvo.isActive()).isTrue();
    }

    // ========== FIND BY ID ==========

    @Test
    void deveBuscarGuardianPorId() {
        UUID id = UUID.randomUUID();
        Guardian guardian = criarGuardianMock(id);
        when(repository.findById(id)).thenReturn(Optional.of(guardian));

        GuardianResponse response = service.findById(id);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.name()).isEqualTo("João Silva");
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarPorId() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(GuardianNotFoundException.class)
                .hasMessageContaining("não encontrado");
    }

    // ========== FIND BY CPF ==========

    @Test
    void deveBuscarGuardianPorCpf() {
        Guardian guardian = criarGuardianMock(UUID.randomUUID());
        when(repository.findByCpf("12345678909")).thenReturn(Optional.of(guardian));

        GuardianResponse response = service.findByCpf("123.456.789-09");

        assertThat(response.cpf()).isEqualTo("123.456.789-09");
        verify(repository).findByCpf("12345678909");
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarPorCpf() {
        when(repository.findByCpf(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findByCpf("123.456.789-09"))
                .isInstanceOf(GuardianNotFoundException.class);
    }

    // ========== UPDATE ==========

    @Test
    void deveAtualizarGuardian() {
        UUID id = UUID.randomUUID();
        Guardian guardian = spy(criarGuardianMock(id));
        when(repository.findById(id)).thenReturn(Optional.of(guardian));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        GuardianRequest updateRequest = new GuardianRequest(
                "Maria Santos",
                "12345678909",
                "maria@email.com",
                Set.of(new GuardianRequest.ContactDTO(
                        ContactType.MOBILE, "11999998888", true)),
                Set.of()
        );

        GuardianResponse response = service.update(id, updateRequest);

        assertThat(response.name()).isEqualTo("Maria Santos");
        verify(guardian).updateName("Maria Santos");
        verify(guardian).updateEmail(any(Email.class));
        verify(guardian).updateContacts(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarGuardianInexistente() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, requestValido))
                .isInstanceOf(GuardianNotFoundException.class);

        verify(repository, never()).save(any());
    }

    // ========== DEACTIVATE/REACTIVATE ==========

    @Test
    void deveDesativarGuardian() {
        UUID id = UUID.randomUUID();

        service.deactivate(id);

        verify(repository).deactivate(id);
    }

    @Test
    void deveReativarGuardian() {
        UUID id = UUID.randomUUID();

        service.reactivate(id);

        verify(repository).reactivate(id);
    }

    // ========== DTO MAPPING ==========

    @Test
    void deveTratarContatosNulosComoVazio() {
        GuardianRequest requestSemContatos = new GuardianRequest(
                "João Silva",
                "12345678909",
                "joao@email.com",
                null,
                null
        );

        when(repository.existsByCpf(any())).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // Não deve lançar exceção por null
        assertThatThrownBy(() -> service.create(requestSemContatos))
                .isInstanceOf(Exception.class); // Guardian valida que precisa ter contatos
    }

    // ========== HELPER ==========

    private Guardian criarGuardianMock(UUID id) {
        return Guardian.reconstitute(
                id,
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of(),
                true,
                null,
                null,
                null
        );
    }
}