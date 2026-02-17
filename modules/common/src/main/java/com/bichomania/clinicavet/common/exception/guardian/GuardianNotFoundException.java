package com.bichomania.clinicavet.common.exception.guardian;

import java.util.UUID;

public class GuardianNotFoundException extends RuntimeException {
    private final UUID guardianId;

    public GuardianNotFoundException(UUID id) {
        super(String.format("Tutor com ID %s não encontrado.", id));
        this.guardianId = id;
    }

    public UUID getGuardianId() {
        return guardianId;
    }
}
