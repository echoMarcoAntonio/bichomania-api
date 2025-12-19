package com.bichomania.clinicavet.infrastructure.persistence.pet;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade JPA mapeada para o banco de dados (Postgres UUID).
 * A camada de persistência conhece apenas tipos primitivos/DB.
 * A conversão para Domain é feita via PetMapper.
 */
@Entity
@Table(
        name = "pets",
        indexes = {
                @Index(
                        name = "idx_pet_dates",
                        columnList = "created_at, updated_at"
                ),
                @Index(
                        name = "idx_guardian_id",
                        columnList = "guardian_id"
                )
        }
)
@EntityListeners(AuditingEntityListener.class)
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pet_id", nullable = false)
    private UUID id;

    @Column(name = "guardian_id", nullable = false)
    private UUID guardianId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "breed", nullable = false, length = 50)
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false, length = 10)
    private Sex sex;

    @Column(name = "is_castrated", nullable = false)
    private Boolean isCastrated = false;

    @Column(name = "microchip_number", unique = true, length = 30)
    private String microchipNumber;

    @Lob
    @Column(name = "history", columnDefinition = "LONGTEXT")
    private String history;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("pet-vaccine")
    private List<VaccineApplicationEntity> vaccineApplications = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("pet-dewormer")
    private List<DewormerApplicationEntity> dewormerApplications = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("pet-reminder")
    private List<ReminderEntity> reminders = new ArrayList<>();

    // Construtor padrão exigido pelo JPA
    public PetEntity() {
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGuardianId() {
        return guardianId;
    }
    public void setGuardianId(UUID guardianId) {
        this.guardianId = guardianId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBreed() {
        return breed;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Boolean getIsCastrated() {
        return isCastrated;
    }
    public void setIsCastrated(Boolean isCastrated) {
        this.isCastrated = isCastrated;
    }

    public String getMicrochipNumber() {
        return microchipNumber;
    }
    public void setMicrochipNumber(String microchipNumber) {
        this.microchipNumber = microchipNumber;
    }

    public String getHistory() {
        return history;
    }
    public void setHistory(String history) {
        this.history = history;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getVersion() {
        return version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }

    public List<VaccineApplicationEntity> getVaccineApplications() {
        return vaccineApplications;
    }

    public void setVaccineApplications(List<VaccineApplicationEntity> vaccineApplications) {
        this.vaccineApplications = vaccineApplications;
    }

    public List<DewormerApplicationEntity> getDewormerApplications() {
        return dewormerApplications;
    }

    public void setDewormerApplications(List<DewormerApplicationEntity> dewormerApplications) {
        this.dewormerApplications = dewormerApplications;
    }

    public List<ReminderEntity> getReminders() {
        return reminders;
    }

    public void setReminders(List<ReminderEntity> reminders) {
        this.reminders = reminders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetEntity that)) return false;
        if (this.id == null || that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : Objects.hash(id);
    }
}
