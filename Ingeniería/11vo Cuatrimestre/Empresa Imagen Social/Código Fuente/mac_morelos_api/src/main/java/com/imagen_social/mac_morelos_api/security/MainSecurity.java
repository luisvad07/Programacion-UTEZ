package com.imagen_social.mac_morelos_api.security;

import com.imagen_social.mac_morelos_api.enums.roles.RoleEnum;
import com.imagen_social.mac_morelos_api.security.jwt.JwtFilter;
import com.imagen_social.mac_morelos_api.security.jwt.JwtProvider;
import com.imagen_social.mac_morelos_api.security.service.UserDetailsServiceImpl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class MainSecurity {

    @Value("${apiPrefix}")
    private String apiPrefix;

    private final String[] whiteList = {
        apiPrefix + "/auth/**"
    };

    private final JwtProvider provider;
    private final UserDetailsServiceImpl service;

    // Constructor para inyección de dependencias
    public MainSecurity(JwtProvider provider, UserDetailsServiceImpl service) {
        this.provider = provider;
        this.service = service;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(service);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtFilter filter() {
        return new JwtFilter(provider, service);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Permite todos los orígenes
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(false); // Cambia a true si necesitas credenciales
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configura CORS usando el bean
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(req ->
                req.requestMatchers(whiteList).permitAll() // Rutas públicas de autenticación
                   .requestMatchers(HttpMethod.POST, apiPrefix + "/auth/**").permitAll()
                   .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll() // Swagger
                   .requestMatchers("/swagger-ui/index.html").permitAll()
                   .requestMatchers(apiPrefix + "/events/status/**", 
                                 apiPrefix + "/events/start-after", 
                                 apiPrefix + "/events/end-before",
                                 apiPrefix + "/events/ongoing",
                                 apiPrefix + "/events/search",
                                 apiPrefix + "/events/address/**").permitAll()
                   .requestMatchers(apiPrefix + "/users/**").permitAll()  // Rutas públicas para usuarios
                   .requestMatchers("/img/**").permitAll() // Rutas para imagenes
                   .requestMatchers("/news_images/**").permitAll()
                   .requestMatchers(apiPrefix + "/incidents/**").permitAll()
                   .requestMatchers(apiPrefix + "/event-registrations/**").permitAll()
                   .requestMatchers(apiPrefix + "/news/getAll").permitAll()
                   .requestMatchers("/profile_pictures/**").permitAll()
                   .anyRequest().hasAnyAuthority(RoleEnum.ADMINISTRADOR.name(), RoleEnum.PROMOTOR.name(), RoleEnum.SUPERVISOR.name(), RoleEnum.PERIODISTA.name()) // Proteger otras rutas
            )
            .httpBasic(Customizer.withDefaults())
            .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Usar autenticación sin estado
            .authenticationProvider(authenticationProvider()) // Configuración del proveedor de autenticación
            .addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class) // Agregar el filtro JWT antes del filtro de autenticación
            .logout(out -> out.logoutUrl("/api/auth/logout").clearAuthentication(true)); // Configuración de logout

        return http.build();
    }
}
