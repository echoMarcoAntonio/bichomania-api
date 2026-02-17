package com.bichomania.clinicavet.domain.user;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;
import com.bichomania.clinicavet.common.exception.user.InvalidUserException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade User (Usuário do domínio)
 * Representa quem acessa o sistema.
 * A lógica de integração com Spring Security (UserDetails) deve ficar na camada de Infra.
 */
public final class User {

    private static final String FIELD_REQUIRED = ExceptionMessages.USER_FIELD_REQUIRED;
    private static final String INVALID_EMAIL = ExceptionMessages.USER_INVALID_EMAIL;

    private final UUID id;
    private final String name;
    private final String email;
    private final String password; // Hash da senha
    private final Role role;

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private User(
            final UUID id,
            final String name,
            final String email,
            final String password,
            final Role role,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        // Validação de presença
        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || role == null) {
            throw new InvalidUserException(FIELD_REQUIRED);
        }

        // Validação básica de formato de e-mail (Regra de Negócio)
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidUserException(INVALID_EMAIL);
        }

        this.id = (id != null) ? id : UUID.randomUUID();
        this.name = name.trim();
        this.email = email.trim().toLowerCase();
        this.password = password;
        this.role = role;
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
        this.updatedAt = (updatedAt != null) ? updatedAt : this.createdAt;
    }

    /**
     * Factory method para novos usuários.
     * @param password Deve chegar aqui já criptografado pelo Service.
     */
    public static User create(String name, String email, String password, Role role) {
        return new User(null, name, email, password, role, null, null);
    }

    /**
     * Reconstitui o usuário do banco.
     */
    public static User reconstitute(
            UUID id, String name, String email, String password, Role role,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        return new User(id, name, email, password, role, createdAt, updatedAt);
    }

    // Comportamentos de Domínio

    public boolean isAdmin() {
        return Role.ROLE_ADMIN.equals(this.role);
    }

    /**
     * Retorna uma nova instância com a senha atualizada.
     */
    public User changePassword(String newHashedPassword) {
        return new User(this.id, this.name, this.email, newHashedPassword, this.role, this.createdAt, LocalDateTime.now());
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        if (id == null || other.id == null) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("User[id=%s, email='%s', role=%s]", id, email, role);
    }
}