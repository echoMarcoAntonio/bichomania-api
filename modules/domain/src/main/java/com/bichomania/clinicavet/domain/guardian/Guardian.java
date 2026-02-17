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
 * Entidade Guardian (Tutor) - Domain Entity
 */
public final class Guardian {

    // Regras de negócio
    private static final int MAX_NAME_LENGTH = 100;

    // Mensagens de exceção
    private static final String NAME_REQUIRED = ExceptionMessages.GUARDIAN_NAME_REQUIRED;
    private static final String NAME_TOO_LONG = ExceptionMessages.GUARDIAN_NAME_TOO_LONG;
    private static final String CONTACT_REQUIRED = ExceptionMessages.GUARDIAN_CONTACT_REQUIRED;
    private static final String CONTACT_ONE_PRINCIPAL = ExceptionMessages.GUARDIAN_CONTACT_ONE_PRINCIPAL;

    // Atributos de estado
    private final UUID id;
    private final Cpf cpf;
    private final Set<Contact> contacts;
    private final Set<Address> addresses;
    private final LocalDateTime createdAt;
    private String name;
    private Email email;
    private boolean active;
    private LocalDateTime updatedAt;

    // Construtor privado (Garante invariantes)
    private Guardian(
            UUID id,
            String name,
            Cpf cpf,
            Email email,
            Set<Contact> contacts,
            Set<Address> addresses,
            boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
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
    }

    // Validações
    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new InvalidGuardianException(NAME_REQUIRED);
        if (name.trim().length() > MAX_NAME_LENGTH)
            throw new InvalidGuardianException(NAME_TOO_LONG);
    }

    private static void validateContacts(Set<Contact> contacts) {
        if (contacts == null || contacts.isEmpty())
            throw new InvalidGuardianException(CONTACT_REQUIRED);
        long principals = contacts.stream().filter(Contact::isPrincipal).count();
        if (principals != 1)
            throw new InvalidGuardianException(CONTACT_ONE_PRINCIPAL);
    }

    // Factories
    public static Guardian create(String name, Cpf cpf, Email email,
                                  Set<Contact> contacts, Set<Address> addresses) {
        return new Guardian(null, name, cpf, email, contacts, addresses, true, null, null);
    }

    public static Guardian reconstitute(UUID id, String name, Cpf cpf, Email email,
                                        Set<Contact> contacts, Set<Address> addresses,
                                        boolean active, LocalDateTime createdAt,
                                        LocalDateTime updatedAt) {
        return new Guardian(id, name, cpf, email, contacts, addresses, active, createdAt, updatedAt);
    }

    // Métodos de atualização (Comportamento)
    public void updateName(String newName) {
        validateName(newName);
        this.name = newName.trim();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEmail(Email newEmail) {
        this.email = Objects.requireNonNull(newEmail);
        this.updatedAt = LocalDateTime.now();
    }

    public void addContact(Contact contact) {
        contacts.add(Objects.requireNonNull(contact));
        validateContacts(contacts);
        this.updatedAt = LocalDateTime.now();
    }

    public void addAddress(Address address) {
        addresses.add(Objects.requireNonNull(address));
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public Contact getPrincipalContact() {
        return contacts.stream()
                .filter(Contact::isPrincipal)
                .findFirst()
                .orElseThrow(() -> new InvalidGuardianException(CONTACT_ONE_PRINCIPAL));
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Email getEmail() {
        return email;
    }

    public Set<Contact> getContacts() {
        return Collections.unmodifiableSet(contacts);
    }

    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

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