package com.bichomania.clinicavet.infrastructure.persistence.guardian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuardianJpaRepository extends JpaRepository<GuardianEntity, UUID> {

    Optional<GuardianEntity> findByIdAndActiveTrue(UUID id);
    Optional<GuardianEntity> findByCpfAndActiveTrue(String cpf);
    boolean existsByCpfAndActiveTrue(String cpf);
}