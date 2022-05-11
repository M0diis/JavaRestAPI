package me.m0dii.security.jwt;

import io.jsonwebtoken.*;
import me.m0dii.security.services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${m0dii.app.jwtSecret}")
    private String jwtSecret;

    @Value("${m0dii.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${m0dii.app.jwtCookieName}")
    private String jwtCookie;

    /**
     * Returns the JWT token from the request.
     *
     * @param request The request.
     * @return The JWT token.
     *
     * @see HttpServletRequest
     * @see Cookie
     */
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);

        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * Generates a JWT token from the user's information.
     *
     * @param userPrincipal The user's information.
     * @return The JWT token.
     *
     * @see UserDetailsImpl
     * @see ResponseCookie
     */
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());

        return ResponseCookie.from(jwtCookie, jwt)
                .path("/api").maxAge(24 * 60 * 60L)
                .httpOnly(true).build();
    }

    /**
     * Generates a clean JWT token.
     *
     * @return The JWT token.
     *
     * @see ResponseCookie
     */
    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookie, null).path("/api").build();
    }

    /**
     * Gets the username from the JWT token.
     *
     * @param token The JWT token.
     * @return The username.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Checks if the JWT token is valid.
     *
     * @param authToken The JWT token.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Generates a JWT token from the username.
     *
     * @param username The username.
     *
     * @return The JWT token.
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}