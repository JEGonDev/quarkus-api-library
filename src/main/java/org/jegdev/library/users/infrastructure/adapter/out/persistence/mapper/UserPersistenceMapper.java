package org.jegdev.library.users.infrastructure.adapter.out.persistence.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.jegdev.library.users.domain.model.User;
import org.jegdev.library.users.infrastructure.adapter.out.persistence.entity.UserEntity;

@ApplicationScoped // Gestión del ciclo de vida como singleton
public class UserPersistenceMapper {

    // Convierte de entidad a dominio
    public User toDomain(UserEntity entity) {
        // Validación defensiva contra nulos
        if (entity == null) {
            return null;
        }

        // Construcción del objeto de dominio
        return User.builder()
                .id(entity.id != null ? entity.id.toHexString() : null) // Convierte ObjectId a String
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roles(entity.getRoles())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    // Convierte de dominio a entidad
    public UserEntity toEntity(User user) {
        // Paso 1: Crear entidad con campos básicos
        UserEntity entity = UserEntity.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .build();

        // Paso 2: Asignar ID si existe (conversión de String a ObjectId)
        if (user.getId() != null) {
            entity.id = new org.bson.types.ObjectId(user.getId());
        }

        return entity;
    }
}