package ru.korshun.eda.tokenData;

import io.jsonwebtoken.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import ru.korshun.eda.CustomUserDetails;
import ru.korshun.eda.entity.Role;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMsForUsers}")
    private int jwtExpirationInMsForUsers;

    @Value("${app.jwtExpirationInMsForOperators}")
    private int jwtExpirationInMsForOperators;


//    private int jwtUserId = 0;

    public String generateToken(Authentication authentication, Role role) {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        int epirationTime = getExpirationTimeFromRole(role);

        Date expiryDate = new Date(new Date().getTime() + epirationTime);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }

    public int getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    private int getExpirationTimeFromRole(Role role) {

        switch (role.getAuthority()) {

            case Role.OPERATOR:
                return jwtExpirationInMsForOperators;

            default:
                return jwtExpirationInMsForUsers;

        }

    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

//    public int getJwtUserId() {
//        return jwtUserId;
//    }
//
//    public void setJwtUserId(int jwtUserId) {
//        this.jwtUserId = jwtUserId;
//    }
}