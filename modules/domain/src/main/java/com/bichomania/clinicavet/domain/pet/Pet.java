package com.bichomania.clinicavet.domain.pet;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.pet.InvalidPetException;
import com.bichomania.clinicavet.domain.dewormerapplication.DewormerApplication;
import com.bichomania.clinicavet.domain.vaccineapplication.VaccineApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidade Pet do domínio.
 * Contém regras de negócio e imutabilidade parcial.
 * Não conhece JPA, DTOs ou Spring.
 */
public class Pet {

    // Mensagens de exceção definidas de forma centralizada em ExceptionMessages
    private static final String PET_FIELD_REQUIRED = ExceptionMessages.PET_FIELD_REQUIRED;
    private static final String BIRTH_DATE_IN_FUTURE = ExceptionMessages.PET_BIRTH_DATE_IN_FUTURE;

    // Campos essenciais do domínio
    private final java.util.UUID id;
    private final java.util.UUID guardianId;
    private final String name;
    private final LocalDate birthDate;
    private String breed;
    private final Sex sex;
    private final Boolean isCastrated;
    private String microchipNumber;
    private String history;

    // Listas de agregados
    private final List<VaccineApplication> vaccineApplications;
    private final List<DewormerApplication> dewormerApplications;
    private final List<Reminder> reminders;

    // Auditoria do domínio
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Construtor privado.
     * Usado internamente por factory methods e reconstituição do banco.
     */
    private Pet(java.util.UUID id, java.util.UUID guardianId, String name, LocalDate birthDate, String breed,
                Sex sex, Boolean isCastrated, String microchipNumber, String history,
                List<VaccineApplication> vaccineApplications,
                List<DewormerApplication> dewormerApplications,
                List<Reminder> reminders,
                LocalDateTime createdAt, LocalDateTime updatedAt) {

        // Valida campos obrigatórios
        if (guardianId == null || name == null || birthDate == null || sex == null || isCastrated == null || breed == null || breed.isBlank()) {
            throw new InvalidPetException(PET_FIELD_REQUIRED);
        }

        // Inicializa campos
        this.id = (id != null) ? id : java.util.UUID.randomUUID();
        this.guardianId = guardianId;
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.sex = sex;
        this.isCastrated = isCastrated;
        this.microchipNumber = microchipNumber;
        this.history = history;
        this.vaccineApplications = vaccineApplications != null ? new ArrayList<>(vaccineApplications) : new ArrayList<>();
        this.dewormerApplications = dewormerApplications != null ? new ArrayList<>(dewormerApplications) : new ArrayList<>();
        this.reminders = reminders != null ? new ArrayList<>(reminders) : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method para criação de novo pet.
     * Garante validação de campos e regras de negócio.
     */
    public static Pet create(java.util.UUID guardianId, String name, LocalDate birthDate, String breed,
                             Sex sex, Boolean isCastrated, String microchipNumber, String history) {

        // Valida campos obrigatórios
        if (guardianId == null || name == null || birthDate == null || sex == null || isCastrated == null || breed == null || breed.isBlank()) {
            throw new InvalidPetException(PET_FIELD_REQUIRED);
        }

        // Valida data de nascimento
        if (birthDate.isAfter(LocalDate.now())) {
            throw new InvalidPetException(BIRTH_DATE_IN_FUTURE);
        }

        return new Pet(
                null,
                guardianId,
                name,
                birthDate,
                breed,
                sex,
                isCastrated,
                microchipNumber,
                history,
                null,
                null,
                null
                );
    }

    /**
     * Reconstitui pet existente do banco.
     * Mantém imutabilidade parcial e listas defensivas.
     */
    public static Pet reconstitute(java.util.UUID id, java.util.UUID guardianId, String name, LocalDate birthDate,
                                   String breed, Sex sex, Boolean isCastrated, String microchipNumber,
                                   String history, List<VaccineApplication> vaccineApplications,
                                   List<DewormerApplication> dewormerApplications,
                                   List<Reminder> reminders,
                                   LocalDateTime createdAt, LocalDateTime updatedAt) {

        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new InvalidPetException(BIRTH_DATE_IN_FUTURE);
        }

        return new Pet(id, guardianId, name, birthDate, breed, sex, isCastrated,
                microchipNumber, history, vaccineApplications, dewormerApplications,
                reminders, createdAt, updatedAt);
    }

    // Métodos de domínio

    public void updateBreed(String newBreed) {
        if (newBreed != null && !newBreed.isBlank()) {
            this.breed = newBreed;
        }
    }

    public void updateMicrochipNumber(String newMicrochipNumber) {
        this.microchipNumber = newMicrochipNumber;
    }

    public void updateHistory(String newHistory) {
        this.history = newHistory;
    }

    public void addVaccineApplication(VaccineApplication vaccineApplication) {
        if (vaccineApplication != null) {
            this.vaccineApplications.add(vaccineApplication);
        }
    }

    public void addDewormerApplication(DewormerApplication dewormerApplication) {
        if (dewormerApplication != null) {
            this.dewormerApplications.add(dewormerApplication);
        }
    }

    public void addReminder(Reminder reminder) {
        if (reminder != null) {
            this.reminders.add(reminder);
        }
    }

    /**
     * Calcula idade do pet em anos inteiros.
     */
    public int calculateAgeInYears() {
        return Period.between(this.birthDate, LocalDate.now()).getYears();    }

    // Getters (somente leitura)

    public java.util.UUID getId() {
        return id;
    }

    public java.util.UUID getGuardianId() {
        return guardianId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getBreed() {
        return breed;
    }

    public Sex getSex() {
        return sex;
    }

    public Boolean getIsCastrated() {
        return isCastrated;
    }

    public String getMicrochipNumber() {
        return microchipNumber;
    }

    public String getHistory() {
        return history;
    }

    public List<VaccineApplication> getVaccineApplications() {
        return Collections.unmodifiableList(vaccineApplications);
    }

    public List<DewormerApplication> getDewormerApplications() {
        return Collections.unmodifiableList(dewormerApplications);
    }

    public List<Reminder> getReminders() {
        return Collections.unmodifiableList(reminders);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Equals e HashCode baseados no ID

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
}
