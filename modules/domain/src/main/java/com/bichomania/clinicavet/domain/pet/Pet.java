package com.bichomania.clinicavet.domain.pet;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.pet.InvalidPetException;
import com.bichomania.clinicavet.domain.dewormerapplication.DewormerApplication;
import com.bichomania.clinicavet.domain.vaccineapplication.VaccineApplication;
import com.bichomania.clinicavet.domain.reminder.Reminder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade Pet (Aggregate Root)
 * Segue o padrão imutável para evolução de estado e listas defensivas.
 */
public final class Pet {

    private static final String PET_FIELD_REQUIRED = ExceptionMessages.PET_FIELD_REQUIRED;
    private static final String BIRTH_DATE_IN_FUTURE = ExceptionMessages.PET_BIRTH_DATE_IN_FUTURE;

    private final UUID id;
    private final UUID guardianId;
    private final String name;
    private final LocalDate birthDate;
    private final String breed;
    private final Sex sex;
    private final Boolean isCastrated;
    private final String microchipNumber;
    private final String history;

    // Listas internas mutáveis apenas dentro da instância
    private final List<VaccineApplication> vaccineApplications;
    private final List<DewormerApplication> dewormerApplications;
    private final List<Reminder> reminders;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Pet(UUID id, UUID guardianId, String name, LocalDate birthDate, String breed,
                Sex sex, Boolean isCastrated, String microchipNumber, String history,
                List<VaccineApplication> vaccineApplications,
                List<DewormerApplication> dewormerApplications,
                List<Reminder> reminders,
                LocalDateTime createdAt, LocalDateTime updatedAt) {

        if (guardianId == null || name == null || birthDate == null || sex == null || isCastrated == null || breed == null || breed.isBlank()) {
            throw new InvalidPetException(PET_FIELD_REQUIRED);
        }

        this.id = (id != null) ? id : UUID.randomUUID();
        this.guardianId = guardianId;
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed.trim();
        this.sex = sex;
        this.isCastrated = isCastrated;
        this.microchipNumber = microchipNumber;
        this.history = history;

        this.vaccineApplications = vaccineApplications != null ? new ArrayList<>(vaccineApplications) : new ArrayList<>();
        this.dewormerApplications = dewormerApplications != null ? new ArrayList<>(dewormerApplications) : new ArrayList<>();
        this.reminders = reminders != null ? new ArrayList<>(reminders) : new ArrayList<>();

        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
        this.updatedAt = (updatedAt != null) ? updatedAt : this.createdAt;
    }

    public static Pet create(UUID guardianId, String name, LocalDate birthDate, String breed,
                             Sex sex, Boolean isCastrated, String microchipNumber, String history) {

        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new InvalidPetException(BIRTH_DATE_IN_FUTURE);
        }

        return new Pet(null, guardianId, name, birthDate, breed, sex, isCastrated,
                microchipNumber, history, null, null, null, null, null);
    }

    public static Pet reconstitute(UUID id, UUID guardianId, String name, LocalDate birthDate,
                                   String breed, Sex sex, Boolean isCastrated, String microchipNumber,
                                   String history, List<VaccineApplication> vaccineApplications,
                                   List<DewormerApplication> dewormerApplications,
                                   List<Reminder> reminders,
                                   LocalDateTime createdAt, LocalDateTime updatedAt) {

        return new Pet(id, guardianId, name, birthDate, breed, sex, isCastrated,
                microchipNumber, history, vaccineApplications, dewormerApplications,
                reminders, createdAt, updatedAt);
    }

    // --- Métodos de Evolução de Estado ---

    /**
     * Atualiza dados mutáveis do pet, retornando uma nova instância e atualizando o carimbo de tempo.
     */
    public Pet updateDetails(String newBreed, String newMicrochipNumber, String newHistory) {
        return new Pet(
                this.id, this.guardianId, this.name, this.birthDate,
                (newBreed != null && !newBreed.isBlank()) ? newBreed.trim() : this.breed,
                this.sex, this.isCastrated, newMicrochipNumber, newHistory,
                this.vaccineApplications, this.dewormerApplications, this.reminders,
                this.createdAt, LocalDateTime.now()
        );
    }

    // --- Métodos de Agregação ---

    public void addVaccineApplication(VaccineApplication application) {
        if (application != null) this.vaccineApplications.add(application);
    }

    public void addDewormerApplication(DewormerApplication application) {
        if (application != null) this.dewormerApplications.add(application);
    }

    public void addReminder(Reminder reminder) {
        if (reminder != null) this.reminders.add(reminder);
    }

    public int calculateAgeInYears() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    // --- Getters (Listas Defensivas) ---

    public UUID getId() { return id; }
    public UUID getGuardianId() { return guardianId; }
    public String getName() { return name; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getBreed() { return breed; }
    public Sex getSex() { return sex; }
    public Boolean getIsCastrated() { return isCastrated; }
    public String getMicrochipNumber() { return microchipNumber; }
    public String getHistory() { return history; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public List<VaccineApplication> getVaccineApplications() {
        return Collections.unmodifiableList(vaccineApplications);
    }

    public List<DewormerApplication> getDewormerApplications() {
        return Collections.unmodifiableList(dewormerApplications);
    }

    public List<Reminder> getReminders() {
        return Collections.unmodifiableList(reminders);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet pet)) return false;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Pet[id=%s, name='%s', breed='%s']", id, name, breed);
    }
}