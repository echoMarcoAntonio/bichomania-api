package com.bichomania.clinicavet.application.guardian.services;

import com.bichomania.clinicavet.domain.guardian.Guardian;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class GuardianService {

    // TODO: injetar GuardianRepository port quando criar

    public Guardian createGuardian(/* TODO: GuardianRequest request */) {
        // TODO: implementar
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Guardian findById(UUID id) {
        // TODO: implementar
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void deleteGuardian(UUID id) {
        // TODO: implementar
        throw new UnsupportedOperationException("Not implemented yet");
    }
}