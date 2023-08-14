package com.example.identatask.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenGenerator {

    private String secret = "458e5a9602bde2b4782d8b4395b7db06cf9c794794c238743fec19e0f93d716b748a54117b1b7485118ab0c463c8256b27fd0368b3a4dfc7efd067f045e47c77" +
            "85fca8d59770e04c029d182458f34fe3ae9f653c8e13d2bd41e72fac0cc8af18a0b3e4cf86f3f1f474bdfe4f99f0fb02db064560a26ecb947e2194fb10495620" +
            "70c8cb6c969129881272ef513760ded030b21c564f714e0fb30199ead915a0581afe91c590245da68745fcd1925a2231c01a67243222bf0015f54265df6963bb" +
            "1e40f22286993fdaf0eb4c1563c59dcda4480ca587b2a05e9227544b906b1a4f11759655f7be3119d3154b8c5c1eb4899c3d580df5de3acabab439ff3e4503ac" +
            "f3b7222aa1146e9bbab22083f6c7e438c33b5240cd490a2d25d36ea23028664dcf6c0f6dc3aaf75f2d0d6093021e1190e1778434f971feb0985213e057e16f17";

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new AuthenticationCredentialsNotFoundException("Incorrect");
        }
    }
}
