package com.bichomania.clinicavet.application.guardian;

import com.bichomania.clinicavet.domain.guardian.Guardian;

import java.util.Optional;
import java.util.UUID;

public interface GuardianRepository {
    Guardian save(Guardian guardian);
    Optional<Guardian> findById(UUID id);
    Optional<Guardian> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    void deactivate(UUID id); // Soft delete
    void reactivate(UUID id);
}