package com.bichomania.clinicavet.infrastructure.persistence.guardian;

import com.bichomania.clinicavet.common.exception.guardian.GuardianNotFoundException;
import com.bichomania.clinicavet.common.types.ContactType;
import com.bichomania.clinicavet.domain.guardian.Guardian;
import com.bichomania.clinicavet.domain.shared.valueobjects.Contact;
import com.bichomania.clinicavet.domain.shared.valueobjects.Cpf;
import com.bichomania.clinicavet.domain.shared.valueobjects.Email;
import com.bichomania.clinicavet.infrastructure.mapper.guardian.GuardianMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class GuardianRepositoryAdapterTest {

    @Mock
    private GuardianJpaRepository jpaRepository;

    @Mock
    private GuardianMapper mapper;

    @InjectMocks
    private GuardianRepositoryAdapter adapter;

    private Guardian guardian;
    private GuardianEntity entity;

    @BeforeEach
    void setup() {
        guardian = Guardian.create(
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        );

        entity = new GuardianEntity();
        entity.setId(UUID.randomUUID());
        entity.setActive(true);
    }

    @Test
    void deveSalvarGuardian() {
        when(mapper.toEntity(guardian)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(guardian);

        Guardian saved = adapter.save(guardian);

        assertThat(saved).isNotNull();
        verify(mapper).toEntity(guardian);
        verify(jpaRepository).save(entity);
        verify(mapper).toDomain(entity);
    }

    @Test
    void deveBuscarPorIdApenasSeSinativoTrue() {
        UUID id = UUID.randomUUID();
        when(jpaRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(guardian);

        Optional<Guardian> result = adapter.findById(id);

        assertThat(result).isPresent();
        verify(jpaRepository).findByIdAndActiveTrue(id);
        verify(jpaRepository, never()).findById(id);
    }

    @Test
    void deveBuscarPorCpfApenasSeSinativoTrue() {
        String cpf = "12345678909";
        when(jpaRepository.findByCpfAndActiveTrue(cpf)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(guardian);

        Optional<Guardian> result = adapter.findByCpf(cpf);

        assertThat(result).isPresent();
        verify(jpaRepository).findByCpfAndActiveTrue(cpf);
    }

    @Test
    void deveVerificarExistenciaPorCpfApenasSeSinativoTrue() {
        String cpf = "12345678909";
        when(jpaRepository.existsByCpfAndActiveTrue(cpf)).thenReturn(true);

        boolean exists = adapter.existsByCpf(cpf);

        assertThat(exists).isTrue();
        verify(jpaRepository).existsByCpfAndActiveTrue(cpf);
    }

    @Test
    void deveDesativarGuardian() {
        UUID id = UUID.randomUUID();
        when(jpaRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.of(entity));

        adapter.deactivate(id);

        assertThat(entity.isActive()).isFalse();
        verify(jpaRepository).save(entity);
    }

    @Test
    void deveLancarExcecaoAoDesativarGuardianInexistente() {
        UUID id = UUID.randomUUID();
        when(jpaRepository.findByIdAndActiveTrue(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.deactivate(id))
                .isInstanceOf(GuardianNotFoundException.class);

        verify(jpaRepository, never()).save(any());
    }

    @Test
    void deveReativarGuardian() {
        UUID id = UUID.randomUUID();
        entity.setActive(false);
        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));

        adapter.reactivate(id);

        assertThat(entity.isActive()).isTrue();
        verify(jpaRepository).save(entity);
    }

    @Test
    void deveLancarExcecaoAoReativarGuardianInexistente() {
        UUID id = UUID.randomUUID();
        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.reactivate(id))
                .isInstanceOf(GuardianNotFoundException.class);
    }
}