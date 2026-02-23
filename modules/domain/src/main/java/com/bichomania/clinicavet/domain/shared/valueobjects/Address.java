package com.bichomania.clinicavet.domain.shared.valueobjects;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.validator.CepValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {

    // Mensagens de exceção personalizadas
    public static final String TO_STRING = ExceptionMessages.GUARDIAN_ADDRESS_TO_STRING;
    private static final String CITY_REQUIRED = ExceptionMessages.GUARDIAN_CITY_REQUIRED;
    private static final String CITY_TOO_LONG = ExceptionMessages.GUARDIAN_CITY_TOO_LONG;
    private static final String CEP_INVALID = ExceptionMessages.GUARDIAN_CEP_INVALID;
    // Regras de domínio
    private static final int MAX_CITY_LENGTH = 100;

    // Value Objects
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "state", length = 2)
    private String state;

    @Column(name = "cep", length = 8)
    private String cep;

    @Column(name = "address_details", length = 500)
    private String details;

    @Column(name = "address_principal", nullable = false)
    private boolean principal;

    /**
     * Método construtor protegido.
     * Exigido pelo JPA. Nunca usar diretamente.
     */
    protected Address() {
    }

    /** Construtor completo (com CEP). */
    public Address(String city, String state, String cep, String details, boolean principal) {
        this.city      = validateCity(city);
        this.state     = state != null ? state.trim().toUpperCase() : null;
        this.cep       = validateCep(cep);
        this.details   = details != null ? details.trim() : null;
        this.principal = principal;
    }

    /** Construtor sem CEP (endereço manual). */
    public Address(String city, String state, String details, boolean principal) {
        this(city, state, null, details, principal);
    }

    private static String validateCity(String city) {
        if (city == null || city.trim().isEmpty())
            throw new IllegalArgumentException(CITY_REQUIRED);
        if (city.trim().length() > MAX_CITY_LENGTH)
            throw new IllegalArgumentException(CITY_TOO_LONG);
        return city.trim();
    }

    private static String validateCep(String cep) {
        if (cep == null) return null;
        String cleaned = CepValidator.clean(cep);
        if (!CepValidator.isValid(cleaned))
            throw new IllegalArgumentException(CEP_INVALID);
        return cleaned;
    }

    // Getters
    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCep() {
        return cep;
    }

    public String getFormattedCep() {
        return CepValidator.format(cep);
    }

    public String getDetails() {
        return details;
    }

    public boolean isPrincipal() {
        return principal;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address a)) return false;
        return Objects.equals(city, a.city) && Objects.equals(details, a.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, details);
    }

    @Override
    public String toString() {
        return String.format(TO_STRING, city, state, cep, details, principal);
    }
}
