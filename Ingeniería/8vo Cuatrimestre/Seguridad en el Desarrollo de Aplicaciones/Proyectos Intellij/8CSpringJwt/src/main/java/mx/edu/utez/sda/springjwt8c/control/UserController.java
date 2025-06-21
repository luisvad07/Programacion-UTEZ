package mx.edu.utez.sda.springjwt8c.control;

import mx.edu.utez.sda.springjwt8c.entity.AuthRequestDto;
import mx.edu.utez.sda.springjwt8c.entity.UserInfo;
import mx.edu.utez.sda.springjwt8c.service.JwtService;
import mx.edu.utez.sda.springjwt8c.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserInfoService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/index")
    public String index(){
        logger.trace("Este es un mensaje de trace");
        logger.debug("Este es un mensaje de bug");
        logger.info("Este es un mensaje de info");
        logger.warn("Este es un mensaje de warning");
        logger.error("Este es un mensaje de error");
        return "Servicio Index";
    }

    @PostMapping("/registrame")
    public String registrame(@RequestBody UserInfo userInfo){
        return service.guardarUser(userInfo);
    }

    @GetMapping("/admin/test")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String soloAdmin(){
        return "Este endpoint es solo para admin :)";
    }

    @GetMapping("/user/test")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public String paraUser(){
        return "Este endpoint puede ser para admin y user XD";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDto authRequestDto){
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                      new UsernamePasswordAuthenticationToken(
                              authRequestDto.getUsername(),
                              authRequestDto.getPassword()
                      )
                    );
            if (authentication.isAuthenticated()){
                return jwtService.generateToken(authRequestDto.getUsername());
            } else {
                throw new UsernameNotFoundException("Usuario Invalido");
            }
        }catch (Exception e){
            throw new UsernameNotFoundException("Usuario Invalido");
        }
    }

}
