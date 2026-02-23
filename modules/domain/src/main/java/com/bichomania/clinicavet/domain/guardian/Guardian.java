package com.bichomania.clinicavet.domain.guardian;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.guardian.InvalidGuardianException;
import com.bichomania.clinicavet.domain.shared.valueobjects.Address;
import com.bichomania.clinicavet.domain.shared.valueobjects.Contact;
import com.bichomania.clinicavet.domain.shared.valueobjects.Cpf;
import com.bichomania.clinicavet.domain.shared.valueobjects.Email;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Guardian (Tutor) - Aggregate Root
 *
 * Representa o responsável legal pelos animais atendidos pela clínica.
 * Garante invariantes: nome válido, CPF único, exatamente 1 contato principal.
 */
public final class Guardian {

    private static final int MAX_NAME_LENGTH = 100;

    private final UUID id;
    private final Cpf cpf;
    private final Set<Contact> contacts;
    private final Set<Address> addresses;
    private final LocalDateTime createdAt;
    private final Long version; // Para optimistic locking
    private String name;
    private Email email;
    private boolean active;
    private LocalDateTime updatedAt;

    // Construtor privado - garante invariantes
    private Guardian(UUID id, String name, Cpf cpf, Email email,
                     Set<Contact> contacts, Set<Address> addresses,
                     boolean active, LocalDateTime createdAt,
                     LocalDateTime updatedAt, Long version) {
        validateName(name);
        validateContacts(contacts);

        this.id = id != null ? id : UUID.randomUUID();
        this.name = name.trim();
        this.cpf = Objects.requireNonNull(cpf);
        this.email = email;
        this.contacts = new HashSet<>(contacts);
        this.addresses = addresses != null ? new HashSet<>(addresses) : new HashSet<>();
        this.active = active;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : this.createdAt;
        this.version = version;
    }

    // Validações
    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new InvalidGuardianException(ExceptionMessages.GUARDIAN_NAME_REQUIRED);
        if (name.trim().length() > MAX_NAME_LENGTH)
            throw new InvalidGuardianException(ExceptionMessages.GUARDIAN_NAME_TOO_LONG);
    }

    private static void validateContacts(Set<Contact> contacts) {
        if (contacts == null || contacts.isEmpty())
            throw new InvalidGuardianException(ExceptionMessages.GUARDIAN_CONTACT_REQUIRED);

        long principals = contacts.stream().filter(Contact::isPrincipal).count();
        if (principals != 1)
            throw new InvalidGuardianException(ExceptionMessages.GUARDIAN_CONTACT_ONE_PRINCIPAL);
    }

    // Factory Methods
    public static Guardian create(String name, Cpf cpf, Email email,
                                  Set<Contact> contacts, Set<Address> addresses) {
        return new Guardian(null, name, cpf, email, contacts, addresses,
                true, null, null, null);
    }

    public static Guardian reconstitute(UUID id, String name, Cpf cpf, Email email,
                                        Set<Contact> contacts, Set<Address> addresses,
                                        boolean active, LocalDateTime createdAt,
                                        LocalDateTime updatedAt, Long version) {
        return new Guardian(id, name, cpf, email, contacts, addresses,
                active, createdAt, updatedAt, version);
    }

    // Comportamentos de Domínio
    public void updateName(String newName) {
        validateName(newName);
        this.name = newName.trim();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEmail(Email newEmail) {
        this.email = Objects.requireNonNull(newEmail);
        this.updatedAt = LocalDateTime.now();
    }

    public void updateContacts(Set<Contact> newContacts) {
        validateContacts(newContacts);
        this.contacts.clear();
        this.contacts.addAll(newContacts);
        this.updatedAt = LocalDateTime.now();
    }

    public void updateAddresses(Set<Address> newAddresses) {
        if (newAddresses != null) {
            this.addresses.clear();
            this.addresses.addAll(newAddresses);
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void deactivate() {
        if (!this.active) {
            throw new InvalidGuardianException(ExceptionMessages.GUARDIAN_ALREADY_INACTIVE);
        }
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void reactivate() {
        if (this.active) {
            throw new InvalidGuardianException(ExceptionMessages.GUARDIAN_ALREADY_ACTIVE);
        }
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public Contact getPrincipalContact() {
        return contacts.stream()
                .filter(Contact::isPrincipal)
                .findFirst()
                .orElseThrow(() -> new InvalidGuardianException(
                        ExceptionMessages.GUARDIAN_CONTACT_ONE_PRINCIPAL));
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public Cpf getCpf() { return cpf; }
    public Email getEmail() { return email; }
    public Set<Contact> getContacts() { return Collections.unmodifiableSet(contacts); }
    public Set<Address> getAddresses() { return Collections.unmodifiableSet(addresses); }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Long getVersion() { return version; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guardian g)) return false;
        return Objects.equals(id, g.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Guardian[id=%s, name='%s', cpf='%s', active=%s]",
                id, name, cpf.getFormatted(), active);
    }
}