package br.com.rfreforged.ReforgedGCP.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;

    public String generateToken(Authentication authentication) {

        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(now.getTimeInMillis() + Long.parseLong(expiration));

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("rol", userPrincipal.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(now.getTime())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256).compact();

    }

    String getUsernameFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody().getSubject();

    }

    boolean validateToken(String authToken, HttpServletRequest rq, HttpServletResponse response) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("JWT Token Inválido");
        } catch (ExpiredJwtException ex) {
            invalidateToken(response);
            rq.setAttribute("expired", ex.getMessage());
            logger.error("JWT Token Expirado");
        } catch (UnsupportedJwtException ex) {
            logger.error("JWT Token não suportado");
        } catch (IllegalArgumentException ex) {
            logger.error("As declarações do Token estão vazias.");
        }
        return false;
    }

    public void invalidateToken(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("SID", "")
                .maxAge(0)
                .sameSite("Lax")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}