package com.bichomania.clinicavet.domain.vaccineapplication;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.vaccine.InvalidVaccineException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade VaccineApplication (Registro de aplicação de vacina)
 * Representa o evento real de vacinação de um pet.
 */
public final class VaccineApplication {

    // Mensagens de exceção centralizadas
    private static final String FIELD_REQUIRED = ExceptionMessages.VACCINE_APPLICATION_FIELD_REQUIRED;
    private static final String DATE_IN_FUTURE = ExceptionMessages.VACCINE_APPLICATION_DATE_IN_FUTURE;

    // Identidade e Vínculos (IDs)
    private final UUID id;
    private final UUID petId;
    private final UUID vaccineId;

    // Dados da Aplicação
    private final LocalDate applicationDate;
    private final LocalDate nextApplicationDate;

    // Auditoria
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Construtor privado para garantir que a aplicação sempre nasça válida.
     */
    private VaccineApplication(
            final UUID id,
            final UUID petId,
            final UUID vaccineId,
            final LocalDate applicationDate,
            final LocalDate nextApplicationDate,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        // Validação de integridade: campos obrigatórios
        if (petId == null || vaccineId == null || applicationDate == null) {
            throw new InvalidVaccineException(FIELD_REQUIRED);
        }

        // Regra de Negócio: Não se registra aplicação futura
        if (applicationDate.isAfter(LocalDate.now())) {
            throw new InvalidVaccineException(DATE_IN_FUTURE);
        }

        this.id = (id != null) ? id : UUID.randomUUID();
        this.petId = petId;
        this.vaccineId = vaccineId;
        this.applicationDate = applicationDate;
        this.nextApplicationDate = nextApplicationDate;

        // Auditoria consistente
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
        this.updatedAt = (updatedAt != null) ? updatedAt : this.createdAt;
    }

    /**
     * Factory method para registrar uma nova aplicação de vacina.
     * @param validityMonths Período de validade da vacina aplicada (em meses).
     */
    public static VaccineApplication create(
            final UUID petId,
            final UUID vaccineId,
            final LocalDate applicationDate,
            final Integer validityMonths
    ) {
        // Lógica de domínio: calcula automaticamente a próxima dose se houver validade
        LocalDate nextDose = (validityMonths != null)
                ? applicationDate.plusMonths(validityMonths)
                : null;

        return new VaccineApplication(null, petId, vaccineId, applicationDate, nextDose, null, null);
    }

    /**
     * Reconstitui a aplicação a partir dos dados do banco.
     */
    public static VaccineApplication reconstitute(
            final UUID id,
            final UUID petId,
            final UUID vaccineId,
            final LocalDate applicationDate,
            final LocalDate nextApplicationDate,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        return new VaccineApplication(id, petId, vaccineId, applicationDate, nextApplicationDate, createdAt, updatedAt);
    }

    // Métodos de consulta (Domínio)

    public boolean isBoostRequired() {
        return nextApplicationDate != null && LocalDate.now().isAfter(nextApplicationDate.minusDays(7));
    }

    // Getters

    public UUID getId() { return id; }
    public UUID getPetId() { return petId; }
    public UUID getVaccineId() { return vaccineId; }
    public LocalDate getApplicationDate() { return applicationDate; }
    public LocalDate getNextApplicationDate() { return nextApplicationDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VaccineApplication other)) return false;
        if (id == null || other.id == null) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("VaccineApplication[id=%s, petId=%s, date=%s]", id, petId, applicationDate);
    }
}