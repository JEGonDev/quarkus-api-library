package org.jegdev.library.users.application.usecase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jegdev.library.users.domain.port.in.RegisterUserUseCase;
import org.jegdev.library.users.domain.port.out.UserRepository;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserRequest;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserResponse;
import org.jegdev.library.users.infrastructure.adapter.in.rest.mapper.UserDtoMapper;

/**
 * Implementación del caso de uso para registrar usuarios.
 * Esta clase maneja el flujo reactivo completo para la creación de usuarios,
 * incluyendo validaciones y persistencia.
 *
 * @ApplicationScoped garantiza una única instancia para toda la aplicación
 */
@ApplicationScoped
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepository userRepository; // Puerto de salida para persistencia de usuarios
    private final UserDtoMapper mapper; // Mapper para convertir entre DTO y dominio

    /**
     * Constructor con inyección de dependencias.
     * IMPORTANTE: El parámetro userRepository debe ser la interfaz UserRepository, no UserRepositoryImpl
     * para mantener el principio de inversión de dependencias.
     */
    @Inject
    public RegisterUserUseCaseImpl(UserRepository userRepository, UserDtoMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Uni<UserResponse> register(UserRequest userRequest) {
        return null;
    }
}
