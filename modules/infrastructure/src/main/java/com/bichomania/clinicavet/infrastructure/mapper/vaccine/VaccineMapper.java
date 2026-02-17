//package com.bichomania.clinicavet.infrastructure.mapper.vaccine;
//
//import com.bichomania.clinicavet.domain.vaccine.Vaccine;
//import com.bichomania.clinicavet.infrastructure.persistence.vaccine.VaccineEntity;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * Mapper para conversão entre Vaccine (Domain) e VaccineEntity (JPA).
// *
// * Responsável por traduzir entre a camada de domínio (regras de negócio)
// * e a camada de persistência (banco de dados).
// */
//@Component
//public class VaccineMapper {
//
//    /**
//     * Converte entidade de domínio (Vaccine) para entidade JPA (VaccineEntity).
//     *
//     * Usado antes de persistir no banco de dados.
//     *
//     * @param vaccine Entidade de domínio
//     * @return Entidade JPA pronta para persistência
//     */
//    public VaccineEntity toEntity(Vaccine vaccine) {
//        if (vaccine == null) {
//            return null;
//        }
//
//        return new VaccineEntity(
//                vaccine.getId(),
//                vaccine.getName(),
//                vaccine.getManufacturer(),
//                vaccine.getDescription(),
//                vaccine.getValidityMonths(),
//                vaccine.getCreatedAt(),
//                vaccine.getUpdatedAt()
//        );
//    }
//
//    /**
//     * Converte entidade JPA (VaccineEntity) para entidade de domínio (Vaccine).
//     *
//     * Usado após buscar dados do banco de dados.
//     * Reconstitui a entidade de domínio com todas as suas regras.
//     *
//     * @param entity Entidade JPA do banco
//     * @return Entidade de domínio reconstituída
//     */
//    public Vaccine toDomain(VaccineEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//
//        return Vaccine.reconstitute(
//                entity.getId(),
//                entity.getName(),
//                entity.getManufacturer(),
//                entity.getDescription(),
//                entity.getValidityMonths(),
//                entity.getCreatedAt(),
//                entity.getUpdatedAt()
//        );
//    }
//
//    /**
//     * Converte lista de entidades JPA para lista de entidades de domínio.
//     *
//     * @param entities Lista de entidades JPA
//     * @return Lista de entidades de domínio
//     */
//    public List<Vaccine> toDomainList(List<VaccineEntity> entities) {
//        if (entities == null) {
//            return List.of();
//        }
//
//        return entities.stream()
//                .map(this::toDomain)
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Converte lista de entidades de domínio para lista de entidades JPA.
//     *
//     * @param vaccines Lista de entidades de domínio
//     * @return Lista de entidades JPA
//     */
//    public List<VaccineEntity> toEntityList(List<Vaccine> vaccines) {
//        if (vaccines == null) {
//            return List.of();
//        }
//
//        return vaccines.stream()
//                .map(this::toEntity)
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Atualiza uma entidade JPA existente com dados de uma entidade de domínio.
//     *
//     * Útil para operações de UPDATE, preservando metadados JPA como version.
//     *
//     * @param entity Entidade JPA existente a ser atualizada
//     * @param vaccine Entidade de domínio com novos dados
//     */
//    public void updateEntityFromDomain(VaccineEntity entity, Vaccine vaccine) {
//        if (entity == null || vaccine == null) {
//            return;
//        }
//
//        entity.setName(vaccine.getName());
//        entity.setManufacturer(vaccine.getManufacturer());
//        entity.setDescription(vaccine.getDescription());
//        entity.setValidityMonths(vaccine.getValidityMonths());
//        // createdAt nunca é atualizado
//        // updatedAt é gerenciado automaticamente pelo @LastModifiedDate
//    }
//}