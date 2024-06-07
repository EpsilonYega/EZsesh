package vibe.EZsesh.configurations;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import vibe.EZsesh.entities.UserDetailsImpl;
import java.util.Date;

@Component
public class JwtCore {
    @Value("${EZsesh.app.secret}")
    private String secret;
    @Value("${EZsesh.app.lifetime}")
    private int lifetime;
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("id", ((UserDetailsImpl) userDetails).getId())
                .claim("username", userDetails.getUsername())
                .claim("email", ((UserDetailsImpl) userDetails).getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime() + lifetime)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    public String getNameFromJwt(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody().getSubject();
    }
}
