package com.bichomania.clinicavet.domain.user;

/**
 * Define os papéis de acesso no sistema.
 * O prefixo ROLE_ é mantido para integração nativa com Spring Security.
 */
public enum Role {
    ROLE_USER,
    ROLE_VETERINARIO,
    ROLE_RECEPCIONISTA,
    ROLE_ADMIN
}