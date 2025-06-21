package mx.edu.utez.sda.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)

public class SecurityConfig {
    public SecurityConfig() {
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("user1").password(this.passwordEncoder().encode("uno123")).roles(new String[]{"USER"}).build();
        UserDetails admin = User.withUsername("admin").password(this.passwordEncoder().encode("admin123")).roles(new String[]{"ADMIN"}).build();
        UserDetails rece = User.withUsername("recepcion").password(this.passwordEncoder().encode("recepcion123")).roles(new String[]{"RECE"}).build();
        UserDetails nino = User.withUsername("infantil").password(this.passwordEncoder().encode("infantil123")).roles(new String[]{"INF"}).build();
        UserDetails adulto = User.withUsername("adulto").password(this.passwordEncoder().encode("adulto123")).roles(new String[]{"ADULTO"}).build();

        return new InMemoryUserDetailsManager(new UserDetails[]{user1, admin, rece, nino, adulto});
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception {
        httpSecurity.authorizeHttpRequests((request) -> {
            request.requestMatchers("/", "/index").permitAll();
            request.anyRequest().authenticated();
        });

        httpSecurity.formLogin((login) -> {
            login.loginPage("/login").permitAll();
        });

        httpSecurity.logout((logout) -> {
            logout.permitAll();
        });

        return httpSecurity.build();
    }

}
