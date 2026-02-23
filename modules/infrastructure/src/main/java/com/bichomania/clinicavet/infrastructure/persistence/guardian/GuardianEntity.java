package com.bichomania.clinicavet.infrastructure.persistence.guardian;

import com.bichomania.clinicavet.domain.shared.valueobjects.Address;
import com.bichomania.clinicavet.domain.shared.valueobjects.Contact;
import com.bichomania.clinicavet.domain.shared.valueobjects.Email;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "guardians", indexes = {
        @Index(name = "idx_guardian_cpf", columnList = "cpf", unique = true),
        @Index(name = "idx_guardian_email", columnList = "email"),
        @Index(name = "idx_guardian_active", columnList = "active")
})
@EntityListeners(AuditingEntityListener.class)
public class GuardianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guardian_id")
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Embedded
    private Email email;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "guardian_contacts",
            joinColumns = @JoinColumn(name = "guardian_id"),
            indexes = @Index(name = "idx_contact_guardian", columnList = "guardian_id")
    )
    private Set<Contact> contacts = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "guardian_addresses",
            joinColumns = @JoinColumn(name = "guardian_id"),
            indexes = @Index(name = "idx_address_guardian", columnList = "guardian_id")
    )
    private Set<Address> addresses = new HashSet<>();

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false, length = 100)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Version
    private Long version;

    protected GuardianEntity() {}

    // Getters/Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public Email getEmail() { return email; }
    public void setEmail(Email email) { this.email = email; }

    public Set<Contact> getContacts() { return contacts; }
    public void setContacts(Set<Contact> contacts) { this.contacts = contacts; }

    public Set<Address> getAddresses() { return addresses; }
    public void setAddresses(Set<Address> addresses) { this.addresses = addresses; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuardianEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? System.identityHashCode(this) : Objects.hash(id);
    }
}