package com.ninjaone.backendinterviewproject.security;

import com.ninjaone.backendinterviewproject.model.Role;
import com.ninjaone.backendinterviewproject.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long MILLISECONDS_IN_ONE_SECOND = 1000L;
    private static final int SECONDS_IN_ONE_MINUTE = 60;
    private static final int MINUTES_IN_ONE_HOUR = 60;
    private static final int HOURS_IN_ONE_DAY = 24;
    private static final long ONE_DAY_EXPIRE_DURATION = HOURS_IN_ONE_DAY * MINUTES_IN_ONE_HOUR * SECONDS_IN_ONE_MINUTE * MILLISECONDS_IN_ONE_SECOND;

    @Value("${security.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(User user) {
        final String authorities = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));
        final String subject = String.join(",", user.getId(), user.getEmail());
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("NinjaSecurity")
                .claim("roles", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ONE_DAY_EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex);
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }
        return false;
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getIdFromClaims(final Claims claims){

        String subject = (String) claims.get(Claims.SUBJECT);
        String[] jwtSubject = subject.split(",");
        return jwtSubject[0];
    }

    public String getEmailFromClaims(final Claims claims){

        String subject = (String) claims.get(Claims.SUBJECT);
        String[] jwtSubject = subject.split(",");
        return jwtSubject[1];
    }

    public Set<Role> getRolesFromClaims(final Claims claims){

        String roles = (String) claims.get("roles");
        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");
        Set<Role> r = new HashSet<>();
        for (String aRoleName : roleNames) {
            r.add(Role.builder().name(aRoleName).build());
        }
        return r;
    }
}
