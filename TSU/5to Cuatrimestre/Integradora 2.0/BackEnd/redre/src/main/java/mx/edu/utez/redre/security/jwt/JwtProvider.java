/*package mx.edu.utez.redre.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import mx.edu.utez.redre.security.model.UsuarioAuth;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        UsuarioAuth userDetail = (UsuarioAuth) authentication.getPrincipal();
        return Jwts.builder().setSubject(userDetail.getCorreo())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration*100L))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String getCorreoFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Token Malformado");
            //e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            logger.error("Token No Soportado");
            //e.printStackTrace();
        } catch (IllegalArgumentException e) {
            logger.error("Token No Provisto");
            //e.printStackTrace();
        } catch (MalformedJwtException e) {
            logger.error("Token Malformado");
            //e.printStackTrace();
        } catch (Exception e) {
            logger.error("Token No Valido");
            //e.printStackTrace();
        }
        return false;
    }
    
}*/
