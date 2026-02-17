package com.bichomania.clinicavet.domain.reminder;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.reminder.InvalidReminderException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade Reminder (Lembrete do domínio)
 * Representa uma notificação agendada ou enviada para um tutor sobre um pet.
 * Utilizada para controle no painel da clínica e histórico de notificações do cliente.
 */
public final class Reminder {

    // Mensagens de exceção centralizadas
    private static final String FIELD_REQUIRED = ExceptionMessages.REMINDER_FIELD_REQUIRED;
    private static final String MESSAGE_TOO_LONG = ExceptionMessages.REMINDER_MESSAGE_TOO_LONG;

    // Regras de domínio
    private static final int MAX_MESSAGE_LENGTH = 1000;

    private final UUID id;
    private final UUID guardianId;
    private final UUID petId;
    private final String message;
    private final LocalDate scheduledDate;
    private final ReminderType type;

    // Status mutável para refletir o ciclo de vida (Pendente -> Enviado/Falha)
    private SendingStatus status;

    // Auditoria do domínio
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Construtor privado para garantir integridade.
     */
    private Reminder(
            final UUID id,
            final UUID guardianId,
            final UUID petId,
            final String message,
            final LocalDate scheduledDate,
            final ReminderType type,
            final SendingStatus status,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        // Validação de campos essenciais
        if (guardianId == null || petId == null || type == null || scheduledDate == null) {
            throw new InvalidReminderException(FIELD_REQUIRED);
        }

        if (message == null || message.trim().isEmpty()) {
            throw new InvalidReminderException(FIELD_REQUIRED);
        }

        if (message.trim().length() > MAX_MESSAGE_LENGTH) {
            throw new InvalidReminderException(MESSAGE_TOO_LONG);
        }

        this.id = (id != null) ? id : UUID.randomUUID();
        this.guardianId = guardianId;
        this.petId = petId;
        this.message = message.trim();
        this.scheduledDate = scheduledDate;
        this.type = type;
        this.status = (status != null) ? status : SendingStatus.PENDENTE;

        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
        this.updatedAt = (updatedAt != null) ? updatedAt : this.createdAt;
    }

    /**
     * Factory method para criar um novo lembrete agendado.
     */
    public static Reminder create(UUID guardianId, UUID petId, String message, LocalDate scheduledDate, ReminderType type) {
        return new Reminder(null, guardianId, petId, message, scheduledDate, type, SendingStatus.PENDENTE, null, null);
    }

    /**
     * Reconstitui o lembrete a partir do estado persistido.
     */
    public static Reminder reconstitute(
            UUID id, UUID guardianId, UUID petId, String message,
            LocalDate scheduledDate, ReminderType type, SendingStatus status,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new Reminder(id, guardianId, petId, message, scheduledDate, type, status, createdAt, updatedAt);
    }

    // --- Comportamentos de Domínio (Transições de Estado para o Painel) ---

    public void markAsSent() {
        this.status = SendingStatus.ENVIADO;
    }

    public void markAsFailed() {
        this.status = SendingStatus.FALHA;
    }

    public boolean isPending() {
        return this.status == SendingStatus.PENDENTE;
    }

    public boolean isOverdue() {
        return isPending() && LocalDate.now().isAfter(scheduledDate);
    }

    // --- Getters ---

    public UUID getId() { return id; }
    public UUID getGuardianId() { return guardianId; }
    public UUID getPetId() { return petId; }
    public String getMessage() { return message; }
    public LocalDate getScheduledDate() { return scheduledDate; }
    public ReminderType getType() { return type; }
    public SendingStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reminder other)) return false;
        if (id == null || other.id == null) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Reminder[id=%s, type=%s, status=%s]", id, type, status);
    }
}