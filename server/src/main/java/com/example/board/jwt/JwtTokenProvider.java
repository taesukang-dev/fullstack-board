package com.example.board.jwt;

import com.example.board.dto.response.TokenResponse;
import com.example.board.repository.TokenCacheRepository;
import com.example.board.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {

    private final String secret;
    private final long tokenValidityMilliseconds;
    private final long refreshTokenValidityMilliseconds;
    private final UserDetailsService userDetailsService;
    private final TokenCacheRepository tokenCacheRepository;

    private Key key;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityMilliseconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityMilliseconds,
            TokenCacheRepository tokenCacheRepository,
            CustomUserDetailsService customUserDetailsService) {
        this.secret = secret;
        this.tokenValidityMilliseconds = tokenValidityMilliseconds;
        this.refreshTokenValidityMilliseconds = refreshTokenValidityMilliseconds;
        this.userDetailsService = customUserDetailsService;
        this.tokenCacheRepository = tokenCacheRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String userPk, List<String> roles, String rtk) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        return tokenBuilder(claims, new Date(), tokenValidityMilliseconds);
    }

    public TokenResponse createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        String rtk = tokenBuilder(claims, now, refreshTokenValidityMilliseconds);
        tokenCacheRepository.setToken(userPk, rtk, Duration.ofMillis(refreshTokenValidityMilliseconds));
        return TokenResponse.builder()
                .atk(tokenBuilder(claims, now, tokenValidityMilliseconds))
                .rtk(rtk)
                .build();
    }

    public String tokenBuilder(Claims claims, Date now, Long validityMilliseconds) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityMilliseconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String getUserPk(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
