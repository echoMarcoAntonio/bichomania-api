package com.bichomania.clinicavet.infrastructure.persistence.guardian;

import com.bichomania.clinicavet.domain.guardian.Guardian;
import com.bichomania.clinicavet.domain.shared.valueobjects.Cpf;
import org.springframework.stereotype.Component;

@Component
public class GuardianMapper {

    public GuardianEntity toEntity(Guardian guardian) {
        GuardianEntity entity = new GuardianEntity();
        entity.setId(guardian.getId());
        entity.setName(guardian.getName());
        entity.setCpf(guardian.getCpf().value());
        entity.setEmail(guardian.getEmail());
        entity.setContacts(guardian.getContacts());
        entity.setAddresses(guardian.getAddresses());
        entity.setActive(guardian.isActive());
        entity.setCreatedAt(guardian.getCreatedAt());
        entity.setUpdatedAt(guardian.getUpdatedAt());
        entity.setVersion(guardian.getVersion());
        return entity;
    }

    public Guardian toDomain(GuardianEntity entity) {
        return Guardian.reconstitute(
                entity.getId(),
                entity.getName(),
                new Cpf(entity.getCpf()),
                entity.getEmail(),
                entity.getContacts(),
                entity.getAddresses(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getVersion()
        );
    }
}