package com.imagen_social.mac_morelos_api.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.imagen_social.mac_morelos_api.security.service.UserDetailsServiceImpl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider provider;
    private final UserDetailsServiceImpl service;

    public JwtFilter(JwtProvider provider, UserDetailsServiceImpl service) {
        this.provider = provider;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            // Resuelve el token JWT desde la solicitud
            String token = provider.resolveToken(request);
            // Si no hay token, pasa a la siguiente fase de la cadena de filtros
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            
            // Resuelve las reclamaciones del token (información adicional en el token)
            Claims claims = provider.resolveClaims(token);

            // Si las reclamaciones son válidas, procesa la autenticación
            if (claims != null && provider.validateClaims(claims)) {
                String username = provider.getEmailOrUsernameFromToken(claims);
            
                // Extraer los roles de manera segura
                Object rolesObj = claims.get("roles");
                List<GrantedAuthority> authorities = new ArrayList<>();
            
                if (rolesObj instanceof List<?>) {
                    authorities = ((List<?>) rolesObj).stream()
                        .filter(String.class::isInstance) // Asegurar que sean strings
                        .map(role -> new SimpleGrantedAuthority(role.toString()))
                        .collect(Collectors.toList());
                }
            
                UserDetails user = service.loadUserByUsername(username);
                Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
            
                SecurityContextHolder.getContext().setAuthentication(auth);
            }            
            
            // Continúa con el procesamiento de la solicitud
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Si ocurre un error, responde con un código HTTP 403 (prohibido)
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }
}
