package com.omniscient.omniscientback.login.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.Date;



@Service
public class JwtService {

    private final String jwtAccessSecretKey;
    private final String jwtRefreshSecretKey;

    private Date accessTokenExpiry;
    private Date refreshTokenExpiry;


    public JwtService(
            @Value("${jwt.access.secret}") String jwtAccessSecretKey,
            @Value("${jwt.refresh.secret}") String jwtRefreshSecretKey) {
        this.jwtAccessSecretKey = jwtAccessSecretKey;
        this.jwtRefreshSecretKey = jwtRefreshSecretKey;

    }

    // Access Token 생성
    public String createAccessToken(String userId) {
        Date now = new Date();
        this.accessTokenExpiry = new Date(now.getTime() + Duration.ofHours(10).toMillis());
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiry)  // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, jwtAccessSecretKey)
                .compact();
    }

    // Refresh Token 생성
    public String createRefreshJwt(String userId) {
        Date now = new Date();
        this.refreshTokenExpiry = new Date(now.getTime() + Duration.ofDays(30).toMillis());
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(refreshTokenExpiry)  // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, jwtRefreshSecretKey)
                .compact();
    }

    // JWT 검증 메서드
    public Claims validateToken(String token, boolean isRefreshToken) {
        String secretKey = isRefreshToken ? jwtRefreshSecretKey : jwtAccessSecretKey;

        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("잘못된 JWT 서명입니다.", e);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT 토큰이 만료되었습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 JWT 토큰입니다.", e);
        }
    }

    // Access Token 만료 시간 반환 메서드
    public long getAccessTokenExpiry() {
        if (accessTokenExpiry == null) {
            throw new IllegalStateException("Access Token이 아직 생성되지 않았습니다.");
        }
        return accessTokenExpiry.getTime();
    }

    // Refresh Token 만료 시간 반환 메서드
    public long getRefreshTokenExpiry() {
        if (refreshTokenExpiry == null) {
            throw new IllegalStateException("Refresh Token이 아직 생성되지 않았습니다.");
        }
        return refreshTokenExpiry.getTime();
    }
}




