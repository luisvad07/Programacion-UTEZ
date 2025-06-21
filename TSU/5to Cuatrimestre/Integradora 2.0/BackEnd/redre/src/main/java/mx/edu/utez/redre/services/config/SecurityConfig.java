/*package mx.edu.utez.redre.services.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import mx.edu.utez.redre.security.services.UserServices;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServices userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/redre/auth/login").permitAll()
                .antMatchers("/redre/departamento/**").permitAll()
                .antMatchers("/redre/responsable/**").permitAll()
                .antMatchers("/redre/asesor/**").permitAll()
                .antMatchers("/redre/estudiante/**").permitAll()
                .antMatchers("/redre/mensajesAsesor/**", "/redre/mensajesDepartamento/**", "/redre/mensajesResponsables/**").permitAll()
                .antMatchers("/redre/consultas/**", "/redre/files/**", "/redremovil/archivos/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .and()
                .httpBasic();
    }
}
*/