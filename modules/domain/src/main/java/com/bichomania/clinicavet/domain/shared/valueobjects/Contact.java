package com.bichomania.clinicavet.domain.shared.valueobjects;

import com.bichomania.clinicavet.common.types.ContactType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Objects;

@Embeddable
public class Contact {

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type", nullable = false, length = 20)
    private ContactType type;

    @Column(name = "contact_value", nullable = false, length = 50)
    private String value;

    @Column(name = "is_principal", nullable = false)
    private boolean principal;

    protected Contact() {
    }

    public Contact(ContactType type, String value, boolean principal) {
        this.type = Objects.requireNonNull(type, "O tipo do contato não pode ser nulo");
        this.value = validateAndClean(value);
        this.principal = principal;
    }

    private String validateAndClean(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("O valor do contato não pode estar vazio");
        }

        String cleaned = value.replaceAll("\\D", "");

        if (cleaned.length() < 10 || cleaned.length() > 11) {
            throw new IllegalArgumentException("Valor de contato inválido");
        }

        return cleaned;
    }

    public ContactType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public String getFormattedValue() {
        if (value.length() == 11) {
            return String.format("(%s) %s-%s",
                    value.substring(0, 2),
                    value.substring(2, 7),
                    value.substring(7, 11)
            );
        } else {
            return String.format("(%s) %s-%s",
                    value.substring(0, 2),
                    value.substring(2, 6),
                    value.substring(6, 11)
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact contact)) return false;
        return Objects.equals(type, contact.type) &&
                Objects.equals(value, contact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return String.format("Contato [tipo=%s, valor=%s, principal=%s]", type, value, principal);
    }
}
