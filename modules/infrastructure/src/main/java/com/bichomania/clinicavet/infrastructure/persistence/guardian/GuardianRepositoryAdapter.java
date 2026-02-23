package com.bichomania.clinicavet.infrastructure.persistence.guardian;

import com.bichomania.clinicavet.application.guardian.GuardianRepository;
import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.guardian.GuardianNotFoundException;
import com.bichomania.clinicavet.domain.guardian.Guardian;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class GuardianRepositoryAdapter implements GuardianRepository {

    private final GuardianJpaRepository jpaRepository;
    private final GuardianMapper mapper;

    public GuardianRepositoryAdapter(GuardianJpaRepository jpaRepository,
                                     GuardianMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Guardian save(Guardian guardian) {
        GuardianEntity entity = mapper.toEntity(guardian);
        GuardianEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Guardian> findById(UUID id) {
        return jpaRepository.findByIdAndActiveTrue(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Guardian> findByCpf(String cpf) {
        return jpaRepository.findByCpfAndActiveTrue(cpf)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return jpaRepository.existsByCpfAndActiveTrue(cpf);
    }

    @Override
    public void deactivate(UUID id) {
        GuardianEntity entity = jpaRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new GuardianNotFoundException(
                        ExceptionMessages.GUARDIAN_NOT_FOUND));

        entity.setActive(false);
        entity.setUpdatedAt(LocalDateTime.now());
        jpaRepository.save(entity);
    }

    @Override
    public void reactivate(UUID id) {
        GuardianEntity entity = jpaRepository.findById(id)
                .orElseThrow(() -> new GuardianNotFoundException(
                        ExceptionMessages.GUARDIAN_NOT_FOUND));

        entity.setActive(true);
        entity.setUpdatedAt(LocalDateTime.now());
        jpaRepository.save(entity);
    }
}