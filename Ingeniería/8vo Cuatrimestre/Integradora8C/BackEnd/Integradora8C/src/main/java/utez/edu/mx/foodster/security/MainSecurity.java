package utez.edu.mx.foodster.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import utez.edu.mx.foodster.security.jwt.JwtAuthenticationFilter;
import utez.edu.mx.foodster.security.jwt.JwtProvider;
import utez.edu.mx.foodster.security.service.UserDetailsServiceImpl;
import utez.edu.mx.foodster.utils.RolesActuales;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MainSecurity {


    @Value("${apiPrefix}")
    private String apiPrefix;
    private final String[] whiteList = {
            apiPrefix + "/auth/**"
    };
    private final JwtProvider provider;


    private final UserDetailsServiceImpl service;

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
    public JwtAuthenticationFilter filter() {
        return new JwtAuthenticationFilter(provider, service);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(whiteList).permitAll()
                                // rutas publicas de la api
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/servicios/**").permitAll()
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/paquetes/**").permitAll()
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/servicios-paquete/**").permitAll()
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/categorias-servicios/**").permitAll()
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/usuarios/public/").permitAll()
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/captcha/**").permitAll()
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/calificaciones/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()

                                // rutas de eventos
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/eventos/**").authenticated()
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/eventos/usuario/").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.CLIENTE)
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/eventos/personal/").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.PERSONAL)
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/eventos/usuario/**").hasAnyAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/eventos/personal/**").hasAnyAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/eventos/finalizar/**").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.PERSONAL)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/eventos/cancelar/**").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.CLIENTE)
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/eventos/").hasAuthority(RolesActuales.ADMIN)

                                // rutas de usuarios
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/usuarios/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/usuarios/**").authenticated()
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/usuarios/usuario/").authenticated()
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/usuarios/**").hasAuthority(RolesActuales.ADMIN)

                                // rutas de personal
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/personal/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/personal/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/personal/**").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.PERSONAL)
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/personal/**").hasAuthority(RolesActuales.ADMIN)

                                // rutas de categorias personal
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/categorias-personal/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/categorias-personal/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/categorias-personal/**").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.PERSONAL)
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/categorias-personal/**").hasAuthority(RolesActuales.ADMIN)

                                // rutas de categorias servicios
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/categorias-servicios/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/categorias-servicios/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/categorias-servicios/**").hasAuthority(RolesActuales.ADMIN)

                                // rutas de servicios
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/servicios/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/servicios/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/servicios/**").hasAuthority(RolesActuales.ADMIN)

                                // rutas de paquetes
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/paquetes/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/paquetes/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/paquetes/**").hasAuthority(RolesActuales.ADMIN)

                                // rutas de roles
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/roles/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/roles/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/roles/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/roles/**").hasAuthority(RolesActuales.ADMIN)

                                // rutas de direcciones

                                .requestMatchers(HttpMethod.POST, apiPrefix + "/direcciones/").authenticated()
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/direcciones/").authenticated()
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/direcciones/usuario/").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.CLIENTE)
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/direcciones/**").authenticated()

                                // rutas de servicios evento
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/servicios-evento/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/servicios-evento/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/servicios-evento/evento/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/servicios-evento/**").hasAuthority(RolesActuales.ADMIN)

                                // rutas de servicios paquete
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/servicios-paquete/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/servicios-paquete/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/servicios-paquete/**").hasAuthority(RolesActuales.ADMIN)

                                // rutas de personal evento
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/personal-evento/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/personal-evento/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/personal-evento/evento/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/personal-evento/**").hasAuthority(RolesActuales.ADMIN)

                                // calificaciones
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/calificaciones/").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.CLIENTE)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/calificaciones/").hasAuthority(RolesActuales.ADMIN)
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/calificaciones/**").hasAuthority(RolesActuales.ADMIN)

                                // rutas de tarjetas
                                .requestMatchers(HttpMethod.POST, apiPrefix + "/tarjetas/").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.CLIENTE)
                                .requestMatchers(HttpMethod.PUT, apiPrefix + "/tarjetas/").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.CLIENTE)
                                .requestMatchers(HttpMethod.GET, apiPrefix + "/tarjetas/usuario/").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.CLIENTE)
                                .requestMatchers(HttpMethod.DELETE, apiPrefix + "/tarjetas/**").hasAnyAuthority(RolesActuales.ADMIN, RolesActuales.CLIENTE)


                                .anyRequest().hasAuthority(RolesActuales.ADMIN)
                )
                .httpBasic(Customizer.withDefaults())
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class)
                .logout(out -> out.logoutUrl("/api/auth/logout").clearAuthentication(true));
        return http.build();
    }
}
