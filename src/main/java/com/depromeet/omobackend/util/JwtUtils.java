package com.depromeet.omobackend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private static final Logger logger = LogManager.getLogger(JwtUtils.class);

    @Autowired
    Environment env;

    public Boolean isValidateToken(String token) {
        try {

            final String subject = (String) getBobyFromToken(token).get("sub");
            return !subject.isEmpty();

        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            long exp = (Long) getBobyFromToken(token).get("exp");
            final Date expiration = new Date(exp);

            return expiration.before(new Date());

        }catch (Exception e) {
            return false;
        }
    }

    public <T> String generateToken(T userDetails) {
        if (logger.isDebugEnabled()) {
            logger.debug(userDetails);
        }

        Map<String,Object> claim = new HashMap<>();

        if (userDetails instanceof DefaultOAuth2User) {
            claim.put("iss", env.getProperty("jwt.toekn-issuer"));  // 발급자
            claim.put("sub",  ((DefaultOAuth2User) userDetails).getName()); // subject 인증 대상(고유 ID)
            claim.put("email", ((DefaultOAuth2User) userDetails).getAttributes().get("userEmail"));
        }
        String secret = env.getProperty("jwt.secret");
        int exp = Integer.valueOf(env.getProperty("jwt.expire-time"));
        claim.put("iat", new Date(System.currentTimeMillis()));
        claim.put("exp", new Date(System.currentTimeMillis() + (1000 * exp)));

        return Jwts.builder()
                .setClaims(claim)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Map<String,Object> getBobyFromToken(String token){
        String secret = env.getProperty("jwt.secret");
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}