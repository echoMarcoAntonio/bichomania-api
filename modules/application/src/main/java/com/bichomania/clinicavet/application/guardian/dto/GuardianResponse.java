package com.bichomania.clinicavet.application.guardian.dto;

import com.bichomania.clinicavet.common.types.ContactType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record GuardianResponse(
        UUID id,
        String name,
        String cpf,
        String email,
        Set<ContactDTO> contacts,
        Set<AddressDTO> addresses,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record ContactDTO(
            ContactType type,
            String value,
            String formattedValue,
            boolean principal
    ) {}

    public record AddressDTO(
            String city,
            String state,
            String cep,
            String formattedCep,
            String details,
            boolean principal
    ) {}
}