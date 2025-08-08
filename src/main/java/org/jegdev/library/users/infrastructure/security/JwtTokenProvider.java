package org.jegdev.library.users.infrastructure.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jegdev.library.users.domain.model.User;

import java.time.Instant;
import java.util.HashSet;

/**
 * Servicio responsable de generar tokens JWT firmados para usuarios autenticados.
 * Esta clase encapsula la lógica relacionada con claims, expiración y firma del token.
 */
@ApplicationScoped
public class JwtTokenProvider {

    @ConfigProperty(name = "jwt.expiration") // Tiempo de expiración del token en segundos
    long expirationSeconds;

    @ConfigProperty(name = "mp.jwt.verify.issuer") // El emisor del token, utilizado para verificar la firma
    String issuer;

    /**
     * Genera un token JWT firmado para el usuario dado.
     *
     * @param user Usuario autenticado
     * @return Token JWT como cadena firmada
     */
    public String generateToken(User user) {
        try {
            Instant now = Instant.now();
            Instant expiration = now.plusSeconds(expirationSeconds);

            return Jwt.claims()
                    .issuer(issuer)                          // iss
                    .subject(user.getEmail())                // sub
                    .groups(new HashSet<>(user.getRoles()))  // groups
                    .issuedAt(now)                          // iat
                    .expiresAt(expiration)                  // exp
                    .sign();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el JWT: " + e.getMessage(), e);
        }
    }
}
