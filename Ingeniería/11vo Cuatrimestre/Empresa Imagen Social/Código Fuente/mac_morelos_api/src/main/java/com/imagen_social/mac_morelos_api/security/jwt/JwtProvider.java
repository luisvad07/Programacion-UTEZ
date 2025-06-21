package com.imagen_social.mac_morelos_api.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.imagen_social.mac_morelos_api.security.entity.UserDetailsImpl;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class JwtProvider {

    Logger logger = Logger.getLogger(JwtProvider.class.getName());

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.expiration.restore}")
    private long expirationRestore;

    // Genera un token JWT basado en la autenticación
    public String generateToken(Authentication authentication) {
        // Obtener la instancia de UserDetailsImpl (ya no necesitamos hacer un cast a User)
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); 
    
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getEmail());  // Accedemos al email directamente
        claims.put("allowedPlatforms", userDetails.getAllowedPlatforms());  // Accedemos a allowedPlatforms si es necesario

        // Obtener roles del usuario
        claims.put("roles", userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .map(role -> role.replace("ROLE_", "")) // <-- Quita el prefijo "ROLE_" si es necesario
            .collect(Collectors.toList()));


        // Si el usuario es CIUDADANO, usa el username como subject, sino usa el email
        String subject;
        if (userDetails.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equalsIgnoreCase("ROLE_CIUDADANO"))) {
            // Si es un ciudadano, se usa el username
            subject = userDetails.getUsername();
        } else {
            // Si no es ciudadano, se usa el email
            subject = userDetails.getEmail();
        }
    
        return Jwts.builder()
                .setClaims(claims)  // Agregamos los claims
                .setSubject(subject)  // Usamos el username para el subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true; // Token válido
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Token inválido o expirado
        }
    }

    // Obtiene la clave de firma a partir del secreto configurado
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Analiza y obtiene los claims de un token JWT
    private Claims parseJwtClaims(String token) {
        try {
            // Eliminar posibles espacios o caracteres extra del token
            token = token.trim(); 

            if (!isValidJwtToken(token)) {
                throw new RuntimeException("Token inválido o mal formado");
            }
    
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.severe("Error al intentar decodificar el token JWT: " + e.getMessage());
            throw new RuntimeException("Token inválido o mal formado", e); 
        }
    }

    // Resuelve los claims del token a partir de una solicitud HTTP
    public Claims resolveClaims(String token) {
        try {
            return parseJwtClaims(token);  // Usamos el token directamente para obtener los claims
        } catch (ExpiredJwtException e) {
            logger.severe("El token JWT ha expirado. Información adicional: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.severe("Ocurrió un error al intentar resolver el token JWT. Información adicional: " + e.getMessage());
            throw e;
        }
    }    
    // Método para extraer el token JWT desde los encabezados de la solicitud HTTP.
    public String resolveToken(HttpServletRequest req) {
        String tokenHeader = req.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7).trim(); // Elimina los espacios

            // Log para depuración
            logger.info("Token recibido: '" + token + "'");
            return token;
        }
        return null;
    }
    
    // Método que verifica que el token tiene un formato válido
    private boolean isValidJwtToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false; // JWT debe tener tres partes: Header, Payload, Signature
        }

        for (String part : parts) {
            if (!isBase64Url(part)) {
                return false; // Verifica que cada parte sea Base64 URL válido
            }
        }

        return true;
    }

    // Verifica si una cadena es un Base64 URL válido
    private boolean isBase64Url(String str) {
        try {
            // Intenta decodificar la cadena utilizando Base64 URL (sin padding)
            byte[] decoded = Base64.getUrlDecoder().decode(str);
            return decoded != null && decoded.length > 0;
        } catch (IllegalArgumentException e) {
            return false; // Si ocurre una excepción, no es un Base64 URL válido
        }
    }

    // Valida los claims de un token
    public boolean validateClaims(Claims claims) {
        return claims.getExpiration().after(new Date());
    }

    // Obtiene el email o username desde un token JWT
    public String getEmailOrUsernameFromToken(Claims claims) {
        if (claims == null) {
            logger.severe("Los claims del token son nulos.");
            return null;
        }
    
        String subject = claims.getSubject();
        if (subject == null || subject.isEmpty()) {
            logger.severe("El subject en los claims del token es nulo o vacío.");
            return null;
        }
    
        return subject;
    }

    // Genera un token para restablecer contraseña
    public String generatePasswordResetToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationRestore * 1000L))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Valida un token de restablecimiento de contraseña
    public boolean validatePasswordResetToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.severe("Token mal formado");
        } catch (UnsupportedJwtException e) {
            logger.severe("Token no soportado");
        } catch (ExpiredJwtException e) {
            logger.severe("Token caducado");
        } catch (IllegalArgumentException e) {
            logger.severe("Token no provisto");
        }
        return false;
    }

    // Obtiene el email a partir de un token de restablecimiento de contraseña
    public String getEmailFromPasswordResetToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

