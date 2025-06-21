package mx.edu.utez.sda.springjwt8c.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extracAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(
                        getSignKey()
                )
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extracClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extracAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String createToken(Map<String, Object> claims, String username){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        claims.put("clave","valor");
        return createToken(claims, username);
    }

    public String extractUsername(String token){
        return extracClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extracClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (
                username.equals(userDetails.getUsername())
                &&
                        !isTokenExpired(token)
        );
    }

}
