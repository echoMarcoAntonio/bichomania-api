package com.bichomania.clinicavet.application.guardian.dto;

import com.bichomania.clinicavet.common.types.ContactType;

import java.util.Set;

public record GuardianRequest(
        String name,
        String cpf,
        String email,
        Set<ContactDTO> contacts,
        Set<AddressDTO> addresses
) {
    public record ContactDTO(
            ContactType type,
            String value,
            boolean principal
    ) {}

    public record AddressDTO(
            String city,
            String state,
            String cep,
            String details,
            boolean principal
    ) {}
}