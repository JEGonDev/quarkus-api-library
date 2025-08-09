package org.jegdev.library.users.infrastructure.config;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jegdev.library.users.domain.model.User;
import org.jegdev.library.users.domain.port.out.PasswordHasher;
import org.jegdev.library.users.domain.port.out.UserRepository;

import java.time.Instant;
import java.util.List;

/**
 * Esta clase se ejecuta automáticamente al iniciar la aplicación.
 * Su propósito es garantizar que exista al menos un usuario con rol ADMIN.
 * Si no existe, lo crea usando datos definidos en application.properties.
 */
@Startup // Indica que esta clase debe inicializarse al arrancar la aplicación
@ApplicationScoped
public class AdminUserInitializer {

    @Inject
    UserRepository userRepository; // Puerto de salida para persistencia de usuarios

    @Inject
    PasswordHasher passwordHasher; // Puerto de salida para hashear contraseñas

    // Inyecta el email del admin desde application.properties
    @ConfigProperty(name = "admin.email")
    String adminEmail;

    // Inyecta el password del admin desde application.properties
    @ConfigProperty(name = "admin.password")
    String adminPassword;

    @PostConstruct // Método que se ejecuta al iniciar la aplicación
    void init() {
        userRepository.findByEmail(adminEmail)
                .onItem().ifNull().switchTo(() -> {
                    // Si no existe el usuario admin lo crea aqui
                    User adminUser = new User(
                            null, // ID se genera automáticamente
                            "Admin", // Nombre del usuario admin
                            adminEmail, // Email del usuario admin
                            passwordHasher.hash(adminPassword), // Password hasheado
                            List.of("ADMIN"), // Rol del usuario admin
                            Instant.now()
                    );
                    return userRepository.save(adminUser);
                })
                .subscribe().with(
                        user -> System.out.println("Usuario admin inicializado: " + user.getEmail()),
                        failure -> System.err.println("Error al inicializar el usuario admin: " + failure.getMessage()
                ));
    }
}
