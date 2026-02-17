package com.bichomania.clinicavet.application.vaccine.services;

// TODO: Implementar depois que criar VaccineEntity, VaccineRepository, VaccineMapper no módulo infrastructure
// Este serviço está desativado temporariamente - foco atual é Guardian

/*
import com.bichomania.clinicavet.application.vaccine.dto.VaccineRequest;
import com.bichomania.clinicavet.application.vaccine.dto.VaccineResponse;
import com.bichomania.clinicavet.application.vaccine.dto.VaccineUpdateRequest;
import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.vaccine.VaccineNotFoundException;
import com.bichomania.clinicavet.domain.vaccine.Vaccine;
import com.bichomania.clinicavet.infrastructure.mapper.vaccine.VaccineMapper;
import com.bichomania.clinicavet.infrastructure.persistence.vaccine.VaccineEntity;
import com.bichomania.clinicavet.infrastructure.persistence.vaccine.VaccineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class VaccineService {

    private static final Logger log = LoggerFactory.getLogger(VaccineService.class);

    private final VaccineRepository repository;
    private final VaccineMapper mapper;

    public VaccineService(VaccineRepository repository, VaccineMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public VaccineResponse create(VaccineRequest request) {
        // implementação...
    }

    // ... resto dos métodos ...
}
*/

public class VaccineService {
    // TODO: implementar quando criar camada de persistência
}