package com.bichomania.clinicavet.domain.dewormerapplication;

import com.bichomania.clinicavet.common.exception.ExceptionMessages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class DewormerApplication {

    /**
     * Entidade Vaccine (Vacina do domínio)
     * Contém regras de negócio e imutabilidade parcial.
     * Não conhece JPA, DTOs ou Spring.
     */

    public class Vaccine {

        // Mensagens de exceção definidas de forma centralizada em ExceptionMessages
        private static final String VACCINE_FIELD_REQUIRED = ExceptionMessages.VACCINE_FIELD_REQUIRED;
        private static final String E = ExceptionMessages.VACCINE_DATE_IN_PAST;

        // Campos essenciais do domínio
        private final java.util.UUID id;
        private final String name;

        // Listas de agregados

        // Auditoria do domínio
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        /**
         * Construtor privado.
         * Usado internamente por factory methods e reconstituição do banco.
         */
        private Vaccine(java.util.UUID id, String name,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {

            // Valida campos obrigatórios
            if (name == null) {
                throw new InvalidVaccineException(VACCINE_FIELD_REQUIRED);
            }

            // Inicializa campos
            this.id = (id != null) ? id : java.util.UUID.randomUUID();
            this.name = name;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        /**
         * Factory method para criação de novo vaccine.
         * Garante validação de campos e regras de negócio.
         */
        public static com.bichomania.clinicavet.domain.vaccine.Vaccine create(java.util.UUID id, String name {

            // Valida campos obrigatórios
            if (name == null) {
                throw new InvalidVaccineException(VACCINE_FIELD_REQUIRED);
            }

            // Valida data de nascimento
            if (.isAfter(LocalDate.now())) {
                throw new InvalidVaccineException(VACCINE_DATE_IN_FUTURE);
            }

            return new com.bichomania.clinicavet.domain.vaccine.Vaccine(
                    name,
                    );
        }

        /**
         * Reconstitui vaccine existente do banco.
         * Mantém imutabilidade parcial e listas defensivas.
         */
        public static com.bichomania.clinicavet.domain.vaccine.Vaccine reconstitute(java.util.UUID id, String name,
                                                                                    LocalDateTime createdAt, LocalDateTime updatedAt) {

            return new com.bichomania.clinicavet.domain.vaccine.Vaccine(id, name, createdAt, updatedAt);
        }

        // Métodos de domínio

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
            if (!(o instanceof com.bichomania.clinicavet.domain.pet.Pet pet)) return false;
            return Objects.equals(id, vaccine.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
