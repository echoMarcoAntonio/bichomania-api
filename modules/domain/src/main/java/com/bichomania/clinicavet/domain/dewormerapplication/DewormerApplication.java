package com.bichomania.clinicavet.domain.dewormerapplication;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.dewormer.InvalidDewormerException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade DewormerApplication (Registro de aplicação de vermífugo)
 * Vincucla um Pet ao Vermífugo aplicado com sua respectiva validade.
 */
public final class DewormerApplication {

    // Mensagens de exceção centralizadas
    private static final String FIELD_REQUIRED = ExceptionMessages.DEWORMER_APPLICATION_FIELD_REQUIRED;
    private static final String DATE_IN_FUTURE = ExceptionMessages.DEWORMER_APPLICATION_DATE_IN_FUTURE;

    private final UUID id;
    private final UUID petId;
    private final UUID dewormerId;
    private final LocalDate applicationDate;
    private final LocalDate nextApplicationDate; // Substitui o campo 'validity' do legado por algo mais semântico

    // Auditoria
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Construtor privado com as regras de negócio de aplicação.
     */
    private DewormerApplication(
            final UUID id,
            final UUID petId,
            final UUID dewormerId,
            final LocalDate applicationDate,
            final LocalDate nextApplicationDate,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        // Validação de integridade referencial básica (Campos obrigatórios)
        if (petId == null || dewormerId == null || applicationDate == null) {
            throw new InvalidDewormerException(FIELD_REQUIRED);
        }

        // Regra de Negócio: Não se registra aplicação no futuro
        if (applicationDate.isAfter(LocalDate.now())) {
            throw new InvalidDewormerException(DATE_IN_FUTURE);
        }

        this.id = (id != null) ? id : UUID.randomUUID();
        this.petId = petId;
        this.dewormerId = dewormerId;
        this.applicationDate = applicationDate;
        this.nextApplicationDate = nextApplicationDate;
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
        this.updatedAt = (updatedAt != null) ? updatedAt : this.createdAt;
    }

    /**
     * Factory method para registrar uma nova aplicação.
     * @param validityMonths Meses vindos da regra de negócio do Dewormer aplicado.
     */
    public static DewormerApplication create(
            final UUID petId,
            final UUID dewormerId,
            final LocalDate applicationDate,
            final Integer validityMonths
    ) {
        // Cálculo automático da próxima dose com base na validade do produto
        LocalDate nextDose = (validityMonths != null)
                ? applicationDate.plusMonths(validityMonths)
                : null;

        return new DewormerApplication(null, petId, dewormerId, applicationDate, nextDose, null, null);
    }

    /**
     * Reconstituição do banco (Data Mapper/JPA).
     */
    public static DewormerApplication reconstitute(
            final UUID id,
            final UUID petId,
            final UUID dewormerId,
            final LocalDate applicationDate,
            final LocalDate nextApplicationDate,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        return new DewormerApplication(id, petId, dewormerId, applicationDate, nextApplicationDate, createdAt, updatedAt);
    }

    // Comportamento de Domínio

    /**
     * Verifica se a aplicação ainda está dentro do prazo de validade.
     */
    public boolean isExpired() {
        if (nextApplicationDate == null) return false;
        return LocalDate.now().isAfter(nextApplicationDate);
    }

    // Getters

    public UUID getId() { return id; }
    public UUID getPetId() { return petId; }
    public UUID getDewormerId() { return dewormerId; }
    public LocalDate getApplicationDate() { return applicationDate; }
    public LocalDate getNextApplicationDate() { return nextApplicationDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DewormerApplication other)) return false;
        if (id == null || other.id == null) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DewormerApplication[id=" + id + ", petId=" + petId + ", date=" + applicationDate + "]";
    }
}