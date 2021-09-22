package com.depromeet.omobackend.security.jwt;

import com.depromeet.omobackend.exception.InvalidTokenException;
import com.depromeet.omobackend.exception.TokenTypeNotRefreshException;
import com.depromeet.omobackend.security.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.exp.access}")
    private Long accessExp;

    @Value("${jwt.exp.refresh}")
    private Long refreshExp;

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer";

    private final AuthDetailsService authDetailsService;

    public String generateAccessToken(String email) {
        return generateToken(email, "access", accessExp);
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, "refresh", refreshExp);
    }

    private String generateToken(String email, String type, Long exp) {
        return Jwts.builder()
                .setHeaderParam("typ", type)
                .setIssuedAt(new Date())
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean isRefreshToken(String token) {
        try {
            return getClaims(token).getHeader().get("typ").equals("refresh");
        } catch (Exception e) {
            throw new TokenTypeNotRefreshException();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader(HEADER);
            if (bearerToken != null && bearerToken.startsWith(PREFIX) && validateToken(bearerToken)) {
                return bearerToken.substring(7);
            }
            return null;
        } catch(Exception e) {
            throw new InvalidTokenException();
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(getId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private boolean validateToken(String token) {
        return getClaims(token).getBody().getExpiration().after(new Date());
    }

    private String getId(String token) {
        return getClaims(token).getBody().getSubject();
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

}
