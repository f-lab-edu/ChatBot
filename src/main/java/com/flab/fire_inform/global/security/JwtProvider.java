package com.flab.fire_inform.global.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

        @Value("${secretKey}")
        private String secretKey;

        @Value("${accessTokenTime}")
        private String accessTokenTime; // 1분

        @Value("${refreshTokenTime}")
        private String refreshTokenTime; // 7일

        // AccessToken
        public String createAccessToken(String id, String password){

                Claims claims = Jwts.claims();
                claims.put("id", id);
                claims.put("password",password);
                Date now = new Date();

                String ssecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
                byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(ssecretKey);
                Key signKey = new SecretKeySpec(secretKeyBytes,signatureAlgorithm.getJcaName());


                return Jwts.builder()
                        .setHeaderParam(Header.JWT_TYPE, Header.TYPE)
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + Long.parseLong(accessTokenTime)))
                        .signWith(signKey)
                        .compact();
        }

        // Refresh Token
        public String createRefreshToken(String id, String password){
                Claims claims = Jwts.claims();
                claims.put("id", id);
                claims.put("password",password);
                Date now = new Date();

                String ssecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
                byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(ssecretKey);
                Key signKey = new SecretKeySpec(secretKeyBytes,signatureAlgorithm.getJcaName());


                return Jwts.builder()
                        .setHeaderParam(Header.JWT_TYPE, Header.TYPE)
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + Long.parseLong(refreshTokenTime)))
                        .signWith(signKey)
                        .compact();
        }

        // validate Any Token
        public boolean validationToken(String token){
                try{
                        String ssecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
                        Jws<Claims> claims = Jwts.parserBuilder()
                                .setSigningKey(DatatypeConverter.parseBase64Binary(ssecretKey))
                                .build().parseClaimsJws(token);
                        return !claims.getBody().getExpiration().before(new Date());
                }catch (Exception e){
                        return false;
                }
        }

        // getting information of Any Token
        // claims로 받으면 다른 객체에서 사용할 때 Claims라는 객체를 사용해야한다. 그러지 말고 그냥 여기서 String으로 뽑아서 반환하는게 더 좋지 않을까?
        public Claims getInformation(String token){
                String ssecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(ssecretKey))
                        .build().parseClaimsJws(token).getBody();
                return claims;
        }
}
