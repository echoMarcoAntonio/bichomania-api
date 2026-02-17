package com.bichomania.clinicavet.domain.dewormer;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.dewormer.InvalidDewormerException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade Dewormer (Vermífugo do domínio)
 *
 * Contém regras de negócio e validações próprias.
 * Não conhece JPA, DTO, Controller ou Spring.
 */
public final class Dewormer {

    // Mensagens de exceção centralizadas
    private static final String FIELD_REQUIRED = ExceptionMessages.DEWORMER_FIELD_REQUIRED;
    private static final String NAME_TOO_LONG = ExceptionMessages.DEWORMER_NAME_TOO_LONG;
    private static final String MANUFACTURER_TOO_LONG = ExceptionMessages.DEWORMER_MANUFACTURER_TOO_LONG;
    private static final String DESCRIPTION_TOO_LONG = ExceptionMessages.DEWORMER_DESCRIPTION_TOO_LONG;
    private static final String VALIDITY_OUT_OF_RANGE = ExceptionMessages.DEWORMER_VALIDITY_OUT_OF_RANGE;

    // Regras do domínio
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_MANUFACTURER_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    private static final int MIN_VALIDITY_MONTHS = 1;
    private static final int MAX_VALIDITY_MONTHS = 24;

    // Identidade do domínio
    private final UUID id;

    // Campos essenciais
    private final String name;
    private final String manufacturer;
    private final String description;
    private final Integer validityMonths;

    // Auditoria do domínio
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Construtor privado.
     * Usado apenas por factory methods e reconstituição.
     */
    private Dewormer(
            final UUID id,
            final String name,
            final String manufacturer,
            final String description,
            final Integer validityMonths,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {

        // Valida campos obrigatórios
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDewormerException(FIELD_REQUIRED);
        }

        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            throw new InvalidDewormerException(FIELD_REQUIRED);
        }

        // Valida regras de negócio (com trim e constantes)
        if (name.trim().length() > MAX_NAME_LENGTH) {
            throw new InvalidDewormerException(NAME_TOO_LONG);
        }

        if (manufacturer.trim().length() > MAX_MANUFACTURER_LENGTH) {
            throw new InvalidDewormerException(MANUFACTURER_TOO_LONG);
        }

        if (description != null && description.trim().length() > MAX_DESCRIPTION_LENGTH) {
            throw new InvalidDewormerException(DESCRIPTION_TOO_LONG);
        }

        if (validityMonths != null && (validityMonths < MIN_VALIDITY_MONTHS || validityMonths > MAX_VALIDITY_MONTHS)) {
            throw new InvalidDewormerException(VALIDITY_OUT_OF_RANGE);
        }

        // Inicializa estado
        this.id = (id != null) ? id : UUID.randomUUID();
        this.name = name.trim();
        this.manufacturer = manufacturer.trim();
        this.description = description != null ? description.trim() : null;
        this.validityMonths = validityMonths;

        // Garante integridade das datas de auditoria
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
        this.updatedAt = (updatedAt != null) ? updatedAt : this.createdAt;
    }

    /**
     * Factory method para criação de novo vermífugo.
     * Garante que a entidade já nasça válida.
     */
    public static Dewormer create(final String name, final String manufacturer, final String description, final Integer validityMonths) {
        return new Dewormer(null, name, manufacturer, description, validityMonths, null, null);
    }

    /**
     * Reconstitui um vermífugo existente do banco.
     * Não altera estado nem regras.
     */
    public static Dewormer reconstitute(
            final UUID id,
            final String name,
            final String manufacturer,
            final String description,
            final Integer validityMonths,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        return new Dewormer(id, name, manufacturer, description, validityMonths, createdAt, updatedAt);
    }

    /**
     * Atualiza os dados do vermífugo.
     * Retorna uma nova instância com os dados atualizados, mantendo a identidade e data de criação.
     */
    public Dewormer update(final String name, final String manufacturer, final String description, final Integer validityMonths) {
        return new Dewormer(
                this.id,
                name,
                manufacturer,
                description,
                validityMonths,
                this.createdAt,
                LocalDateTime.now()
        );
    }

    // Comportamento de domínio

    public boolean hasValidityPeriod() {
        return validityMonths != null;
    }

    public String getDisplayName() {
        return String.format("%s - %s", name, manufacturer);
    }

    // Getters

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public Integer getValidityMonths() {
        return validityMonths;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Equals e HashCode baseados na identidade

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dewormer other)) return false;

        // Entidades não persistidas (id null) não são consideradas iguais
        if (id == null || other.id == null) return false;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Dewormer[id=" + id + ", name='" + name + "', manufacturer='" + manufacturer + "']";
    }
}