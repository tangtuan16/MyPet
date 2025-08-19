package org.example.mypet.Utils.Jwts;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mypet.Configs.JwtProperties;
import org.example.mypet.DTO.Auth.JwtResponse;
import org.example.mypet.DTO.Auth.RefreshTokenRequest;
import org.example.mypet.DTO.User.UserDTO;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserDTO dto) {
        Date now = new Date();
        Date expDate = new Date(now.getTime() + jwtProperties.getAccessExpirationMs());
        log.info("now: {}", now.toString());
        log.info("expiration: {}", expDate);
        return Jwts.builder()
                .setSubject(dto.getUsername())
                .claim("userID", dto.getId())
                .claim("roles", dto.getRoles().stream()
                        .map(role -> role.getName()
                        ).collect(Collectors.toSet()))
                .setIssuedAt(now)
                .setExpiration(expDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDTO dto) {
        Date now = new Date();
        Date expDate = new Date(now.getTime() + jwtProperties.getRefreshExpirationMs());
        return Jwts.builder()
                .setSubject(dto.getUsername())
                .claim("UserID", dto.getId())
                .setIssuedAt(now)
                .setExpiration(expDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return
                Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
    }

    public JwtResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            String username = claims.getSubject();

            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + jwtProperties.getAccessExpirationMs());

            String newAccessToken = Jwts.builder()
                    .setSubject(username)
                    .claim("userID", claims.get("userID"))
                    .claim("roles", claims.get("roles"))
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();

            return new JwtResponse(newAccessToken, refreshToken);

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Refresh token expired");
        } catch (JwtException e) {
            throw new RuntimeException("Invalid refresh token");
        }
    }

}
