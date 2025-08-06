package org.jegdev.library.users.infrastructure.adapter.in.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.jegdev.library.users.domain.model.User;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserRequest;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserResponse;

import java.util.ArrayList;

@ApplicationScoped
public class UserDtoMapper {

    public User toDomain(UserRequest request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .roles(new ArrayList<>()) // Iniciamos con lista vacía de roles
                .build();
    }

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
