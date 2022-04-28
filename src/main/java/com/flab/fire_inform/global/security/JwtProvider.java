package com.flab.fire_inform.global.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

        private static final String secretKey = Base64.getEncoder().encodeToString("20220427flabHaiHeeDooKyoungOhteamprojectfireinformwedothisprojectforourlearning".getBytes());
        private static final long accessTokenTime = 1000L*60; // 1분
        private static final long refreshTokenTime = 1000L*60*60*24*7; // 7일

        // AccessToken
        public String createAccessToken(String id, String password){
                Claims claims = Jwts.claims();
                claims.put("id", id);
                claims.put("password",password);
                Date now = new Date();

                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
                byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
                Key signKey = new SecretKeySpec(secretKeyBytes,signatureAlgorithm.getJcaName());


                return Jwts.builder()
                        .setHeaderParam(Header.JWT_TYPE, Header.TYPE)
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + accessTokenTime))
                        .signWith(signKey)
                        .compact();
        }

        // Refresh Token
        public String createRefreshToken(String id, String password){
                Claims claims = Jwts.claims();
                claims.put("id", id);
                claims.put("password",password);
                Date now = new Date();

                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
                byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
                Key signKey = new SecretKeySpec(secretKeyBytes,signatureAlgorithm.getJcaName());


                return Jwts.builder()
                        .setHeaderParam(Header.JWT_TYPE, Header.TYPE)
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshTokenTime))
                        .signWith(signKey)
                        .compact();
        }

        // validate Any Token
        public boolean validationToken(String token){
                try{
                        log.info("여기서 터진다.");
                        Jws<Claims> claims = Jwts.parserBuilder()
                                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                                .build().parseClaimsJws(token);
                        log.info("여기서 터진다.");
                        return !claims.getBody().getExpiration().before(new Date());
                }catch (Exception e){
                        return false;
                }
        }

        // getting information of Any Token
        // claims로 받으면 다른 객체에서 사용할 때 Claims라는 객체를 사용해야한다. 그러지 말고 그냥 여기서 String으로 뽑아서 반환하는게 더 좋지 않을까?
        public Claims getInformation(String token){
                Claims claims = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                        .build().parseClaimsJws(token).getBody();
                return claims;
        }
}
