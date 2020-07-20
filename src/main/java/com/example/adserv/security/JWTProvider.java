package com.example.adserv.security;

import com.example.adserv.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTProvider {


    @Value("${security.jwt.token.secret:really_secret}")
    private String secret;

    @Value("${security.jwt.token.validity-ms:3600000}")
    private long validityInMs = 3600000;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        List<String> authorities = claims.get("authorities", List.class);
        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        System.out.println("grantedAuthorities:" + grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", grantedAuthorities);

    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            throw new ServiceException("Invalid token", HttpStatus.BAD_REQUEST);
        }
    }

}
