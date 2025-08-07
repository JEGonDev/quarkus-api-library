package org.jegdev.library.users.application.usecase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jegdev.library.users.domain.model.User;
import org.jegdev.library.users.domain.port.in.LoginUserUseCase;
import org.jegdev.library.users.domain.port.out.PasswordHasher;
import org.jegdev.library.users.domain.port.out.UserRepository;
import org.jegdev.library.users.errors.exceptions.personalized.UserEmailNotFoundException;
import org.jegdev.library.users.errors.exceptions.personalized.IncorrectPasswordException;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.LoginRequest;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.LoginResponse;
import org.jegdev.library.users.infrastructure.security.JwtTokenProvider;

/**
 * Implementación del caso de uso para iniciar sesión de un usuario.
 * Esta clase orquesta el flujo de inicio de sesión, validando las credenciales
 * y generando la respuesta correspondiente.
 */
@ApplicationScoped
public class LoginUserUseCaseImpl implements LoginUserUseCase {

    private final UserRepository userRepository; // Puerto de salida para persistencia de usuarios
    private final PasswordHasher passwordHasher; // Puerto de salida para hashear contraseñas
    private final JwtTokenProvider jwtTokenProvider; // Proveedor de tokens JWT

    /**
     * Constructor con inyección de dependencias.
     * IMPORTANTE: El parámetro userRepository debe ser la interfaz UserRepository, no UserRepositoryImpl
     * para mantener el principio de inversión de dependencias.
     */
    @Inject
    public LoginUserUseCaseImpl(UserRepository userRepository, PasswordHasher passwordHasher, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Método principal que orquesta el flujo de inicio de sesión de un usuario.
     * El flujo sigue estos pasos:
     * 1. Busca al usuario por su email
     * 2. Valida la contraseña proporcionada
     * 3. Genera y retorna la respuesta de inicio de sesión
     *
     * @param loginRequest DTO con los datos del inicio de sesión (email y contraseña)
     * @return Uni<LoginResponse> Respuesta reactiva con el estado del inicio de sesión
     * @throws UserEmailNotFoundException si no se encuentra un usuario con el email proporcionado
     * @throws IncorrectPasswordException si la contraseña es incorrecta
     */
    @Override
    public Uni<LoginResponse> login(LoginRequest loginRequest) {
        return findUserByEmail(loginRequest.getEmail()) // paso 1: Buscar usuario por email
                .map(user -> validatePassword(loginRequest.getPassword(), user)) // paso 2: Validar contraseña
                .map(user -> generateLoginResponse(user)); // paso 3: Generar respuesta de inicio de sesión
    }

    /**
     * Busca un usuario por su email.
     * Si no se encuentra, lanza una excepción personalizada.
     *
     * @param email Email del usuario a buscar
     * @return Uni<User> Usuario encontrado
     * @throws UserEmailNotFoundException si no se encuentra un usuario con el email proporcionado
     */
    private Uni<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .onItem().ifNull().failWith(() -> new UserEmailNotFoundException("Usuario no encontrado con email: " + email));
    }

    /**
     * Valida la contraseña proporcionada contra la almacenada en el usuario.
     * Si no coinciden, lanza una excepción personalizada.
     *
     * @param rawPassword Contraseña proporcionada por el usuario
     * @param user Usuario encontrado en la base de datos
     * @return User Usuario validado
     * @throws IncorrectPasswordException si la contraseña es incorrecta
     */
    private User validatePassword(String rawPassword, User user) {
        if (!passwordHasher.verify(rawPassword, user.getPassword())) {
            throw new IncorrectPasswordException("Contraseña incorrecta para el usuario: " + user.getEmail());
        }
        return user;
    }

    /**
     * Genera la respuesta de inicio de sesión.
     * En este caso, simplemente retorna un estado "OK".
     *
     * @param user Usuario que ha iniciado sesión correctamente
     * @return LoginResponse Respuesta de inicio de sesión
     */
    private LoginResponse generateLoginResponse(User user) {
        String token = jwtTokenProvider.generateToken(user); // Genera el token JWT para el usuario
        return new LoginResponse(token); // Retorna la respuesta con estado y token
    }
}
