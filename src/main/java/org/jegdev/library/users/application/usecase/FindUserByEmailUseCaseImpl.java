package org.jegdev.library.users.application.usecase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.jegdev.library.users.domain.port.in.FindUserByEmailUseCase;
import org.jegdev.library.users.domain.port.out.UserRepository;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserResponse;
import org.jegdev.library.users.infrastructure.adapter.in.rest.mapper.UserDtoMapper;

@ApplicationScoped
public class FindUserByEmailUseCaseImpl implements FindUserByEmailUseCase {

    private final UserDtoMapper mapper;  // Mapper para convertir entre DTO y dominio
    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias.
     * IMPORTANTE: El parámetro userRepository debe ser la interfaz UserRepository, no UserRepositoryImpl
     * para mantener el principio de inversión de dependencias.
     */
    public FindUserByEmailUseCaseImpl(UserDtoMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public Uni<UserResponse> findUserByEmail(String email) {
        return userRepository.findByEmail(email)


    }
}
