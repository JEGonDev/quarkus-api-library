package org.jegdev.library.users.application.usecase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jegdev.library.users.domain.model.User;
import org.jegdev.library.users.domain.port.in.RegisterUserUseCase;
import org.jegdev.library.users.domain.port.out.PasswordHasher;
import org.jegdev.library.users.domain.port.out.UserRepository;
import org.jegdev.library.users.errors.exceptions.personalized.UserDuplicateException;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserRequest;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserResponse;
import org.jegdev.library.users.infrastructure.adapter.in.rest.mapper.UserDtoMapper;

import java.time.Instant;
import java.util.List;

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
    private final PasswordHasher passwordHasher; // Puerto de salida para hashear contraseñas

    /**
     * Constructor con inyección de dependencias.
     * IMPORTANTE: El parámetro userRepository debe ser la interfaz UserRepository, no UserRepositoryImpl
     * para mantener el principio de inversión de dependencias.
     */
    @Inject
    public RegisterUserUseCaseImpl(UserRepository userRepository, UserDtoMapper mapper, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordHasher = passwordHasher;
    }

    /**
     * Método principal que orquesta el flujo de registro de un usuario.
     * El flujo sigue estos pasos:
     * 1. Valida que no exista un usuario con el mismo email
     * 2. Crea una entidad de dominio User a partir del DTO
     * 3. Persiste el usuario en la base de datos
     * 4. Convierte y retorna la respuesta
     *
     * @param userRequest DTO con los datos del usuario a registrar (ya validado por Bean Validation)
     * @return Uni<UserResponse> Respuesta reactiva con el usuario registrado
     * @throws UserDuplicateException si ya existe un usuario con el mismo email
     */
    @Override
    public Uni<UserResponse> register(UserRequest userRequest) {
        return validateEmailNotExists(userRequest.getEmail()) // paso 1: Validar el email
                .map(ignored -> createUserFromRequest(userRequest)) // paso 2: Crear entidad de dominio
                .chain(this::saveUser) // paso 3: Persistir el usuario
                .map(mapper::toResponse); // paso 4: Convertir a respuesta DTO
    }

    // Validar que no exista un usuario con el mismo email
    private Uni<Void> validateEmailNotExists(String email) {
        return userRepository.findByEmail(email) // Verificar si el email ya existe
                .onItem().ifNotNull()  // Si se encuentra un usuario con ese email
                .failWith(() -> new UserDuplicateException(email)) // lanzar excepción personalizada
                .replaceWithVoid(); // Si no existe, continuar sin valor
    }

    // Crear una entidad de dominio User a partir del DTO UserRequest
    private User createUserFromRequest(UserRequest userRequest) {
        User user = mapper.toDomain(userRequest); // Convertir DTO a entidad de dominio
        user.setCreatedAt(Instant.now()); // Establecer la fecha de creación
        user.setRoles(List.of("USER")); // Asignar el rol por defecto "USER"

        String hashedPassword = passwordHasher.hash(userRequest.getPassword()); // Hashear la contraseña antes de guardar
        user.setPassword(hashedPassword); // Establecer la contraseña hasheada

        return user;
    }

    // Persistir el usuario en la base de datos
    private Uni<User> saveUser(User user) {
        return userRepository.save(user); // Persistir el usuario y retornar la entidad guardada
    }
}
