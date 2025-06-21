/*package mx.edu.utez.redre.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.redre.security.controller.dtos.LoginDto;
import mx.edu.utez.redre.security.jwt.JwtProvider;
import mx.edu.utez.redre.utils.CustomResponse;

@RestController
@RequestMapping("/redre/auth/")
@CrossOrigin(origins = {"*"})
public class LoginController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtProvider provider;

    @PostMapping("/login")
    public ResponseEntity<CustomResponse<Map<String, Object>>> login(@Valid @RequestBody LoginDto login) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(login.getCorreo(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = provider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, Object> data = new HashMap <>();
        data.put("token", token);
        data.put("user", userDetails);
        return new ResponseEntity<>(
          new CustomResponse<>(
            data,
            false,
            200,
            "Ok"
          ),
          HttpStatus.OK 
        );
    }
}*/
