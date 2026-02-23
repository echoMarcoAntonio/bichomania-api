package com.bichomania.clinicavet.infrastructure.persistence.guardian;

import com.bichomania.clinicavet.common.types.ContactType;
import com.bichomania.clinicavet.domain.guardian.Guardian;
import com.bichomania.clinicavet.domain.shared.valueobjects.Contact;
import com.bichomania.clinicavet.domain.shared.valueobjects.Cpf;
import com.bichomania.clinicavet.domain.shared.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class GuardianMapperTest {

    private GuardianMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new GuardianMapper();
    }

    @Test
    void deveConverterDomainParaEntity() {
        Guardian guardian = Guardian.create(
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        );

        GuardianEntity entity = mapper.toEntity(guardian);

        assertThat(entity.getName()).isEqualTo("João Silva");
        assertThat(entity.getCpf()).isEqualTo("12345678909");
        assertThat(entity.getEmail().getValue()).isEqualTo("joao@email.com");
        assertThat(entity.isActive()).isTrue();
        assertThat(entity.getContacts()).hasSize(1);
    }

    @Test
    void deveConverterEntityParaDomain() {
        GuardianEntity entity = new GuardianEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("Maria Santos");
        entity.setCpf("98765432100");
        entity.setEmail(new Email("maria@email.com"));
        entity.setContacts(Set.of(new Contact(ContactType.MOBILE, "11999998888", true)));
        entity.setAddresses(Set.of());
        entity.setActive(true);

        Guardian guardian = mapper.toDomain(entity);

        assertThat(guardian.getName()).isEqualTo("Maria Santos");
        assertThat(guardian.getCpf().value()).isEqualTo("98765432100");
        assertThat(guardian.getEmail().getValue()).isEqualTo("maria@email.com");
        assertThat(guardian.isActive()).isTrue();
    }

    @Test
    void devePreservarVersionNoMapping() {
        Guardian guardian = Guardian.reconstitute(
                UUID.randomUUID(),
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of(),
                true,
                null,
                null,
                5L
        );

        GuardianEntity entity = mapper.toEntity(guardian);

        assertThat(entity.getVersion()).isEqualTo(5L);
    }

    @Test
    void devePreservarIdNoMapping() {
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
                null
        );

        GuardianEntity entity = mapper.toEntity(guardian);

        assertThat(entity.getId()).isEqualTo(id);
    }

    @Test
    void deveConverterGuardianInativo() {
        Guardian guardian = Guardian.create(
                "João Silva",
                new Cpf("12345678909"),
                new Email("joao@email.com"),
                Set.of(new Contact(ContactType.WHATSAPP, "11987654321", true)),
                Set.of()
        );
        guardian.deactivate();

        GuardianEntity entity = mapper.toEntity(guardian);

        assertThat(entity.isActive()).isFalse();
    }
}