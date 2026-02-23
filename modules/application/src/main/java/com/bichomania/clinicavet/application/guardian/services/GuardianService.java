package com.bichomania.clinicavet.application.guardian.services;

import com.bichomania.clinicavet.application.guardian.GuardianRepository;
import com.bichomania.clinicavet.application.guardian.dto.GuardianRequest;
import com.bichomania.clinicavet.application.guardian.dto.GuardianResponse;
import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.guardian.DuplicateCpfException;
import com.bichomania.clinicavet.common.exception.guardian.GuardianNotFoundException;
import com.bichomania.clinicavet.common.validator.CpfValidator;
import com.bichomania.clinicavet.domain.guardian.Guardian;
import com.bichomania.clinicavet.domain.shared.valueobjects.Address;
import com.bichomania.clinicavet.domain.shared.valueobjects.Contact;
import com.bichomania.clinicavet.domain.shared.valueobjects.Cpf;
import com.bichomania.clinicavet.domain.shared.valueobjects.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class GuardianService {

    private final GuardianRepository repository;

    public GuardianService(GuardianRepository repository) {
        this.repository = repository;
    }

    public GuardianResponse create(GuardianRequest request) {
        String cleanCpf = CpfValidator.clean(request.cpf());

        if (repository.existsByCpf(cleanCpf)) {
            throw new DuplicateCpfException(cleanCpf);
        }

        Guardian guardian = Guardian.create(
                request.name(),
                new Cpf(cleanCpf),
                new Email(request.email()),
                mapContacts(request.contacts()),
                mapAddresses(request.addresses())
        );

        Guardian saved = repository.save(guardian);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public GuardianResponse findById(UUID id) {
        Guardian guardian = repository.findById(id)
                .orElseThrow(() -> new GuardianNotFoundException(
                        ExceptionMessages.GUARDIAN_NOT_FOUND));
        return toResponse(guardian);
    }

    @Transactional(readOnly = true)
    public GuardianResponse findByCpf(String cpf) {
        String cleanCpf = CpfValidator.clean(cpf);
        Guardian guardian = repository.findByCpf(cleanCpf)
                .orElseThrow(() -> new GuardianNotFoundException(
                        ExceptionMessages.GUARDIAN_NOT_FOUND));
        return toResponse(guardian);
    }

    public GuardianResponse update(UUID id, GuardianRequest request) {
        Guardian existing = repository.findById(id)
                .orElseThrow(() -> new GuardianNotFoundException(
                        ExceptionMessages.GUARDIAN_NOT_FOUND));

        existing.updateName(request.name());
        existing.updateEmail(new Email(request.email()));
        existing.updateContacts(mapContacts(request.contacts()));
        existing.updateAddresses(mapAddresses(request.addresses()));

        Guardian saved = repository.save(existing);
        return toResponse(saved);
    }

    public void deactivate(UUID id) {
        repository.deactivate(id);
    }

    public void reactivate(UUID id) {
        repository.reactivate(id);
    }

    // Mappers
    private Set<Contact> mapContacts(Set<GuardianRequest.ContactDTO> dtos) {
        if (dtos == null) return Collections.emptySet();
        return dtos.stream()
                .map(dto -> new Contact(dto.type(), dto.value(), dto.principal()))
                .collect(Collectors.toSet());
    }

    private Set<Address> mapAddresses(Set<GuardianRequest.AddressDTO> dtos) {
        if (dtos == null) return Collections.emptySet();
        return dtos.stream()
                .map(dto -> new Address(dto.city(), dto.state(), dto.cep(),
                        dto.details(), dto.principal()))
                .collect(Collectors.toSet());
    }

    private GuardianResponse toResponse(Guardian guardian) {
        return new GuardianResponse(
                guardian.getId(),
                guardian.getName(),
                guardian.getCpf().getFormatted(),
                guardian.getEmail().getValue(),
                mapContactsToDTO(guardian.getContacts()),
                mapAddressesToDTO(guardian.getAddresses()),
                guardian.isActive(),
                guardian.getCreatedAt(),
                guardian.getUpdatedAt()
        );
    }

    private Set<GuardianResponse.ContactDTO> mapContactsToDTO(Set<Contact> contacts) {
        return contacts.stream()
                .map(c -> new GuardianResponse.ContactDTO(
                        c.getType(),
                        c.getValue(),
                        c.getFormattedValue(),
                        c.isPrincipal()
                ))
                .collect(Collectors.toSet());
    }

    private Set<GuardianResponse.AddressDTO> mapAddressesToDTO(Set<Address> addresses) {
        return addresses.stream()
                .map(a -> new GuardianResponse.AddressDTO(
                        a.getCity(),
                        a.getState(),
                        a.getCep(),
                        a.getFormattedCep(),
                        a.getDetails(),
                        a.isPrincipal()
                ))
                .collect(Collectors.toSet());
    }
}