package com.helpduck.helpduckusers.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

  @Value("${jwt.secretkey}")
  private String secretkey;

  @Value("${jwt.expiration}")
  private String expiration;

  public String generateToken(String email) {
    Date expirationDate = new Date(System.currentTimeMillis() + Long.parseLong(expiration));

    return Jwts.builder()
        .setSubject(email)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS256, secretkey).compact();
  }

  public boolean validateToken(String jwtToken) {
    Claims claims = getClaims(jwtToken);
    if (claims != null) {

      String username = claims.getSubject();

      Date expirationDate = claims.getExpiration();

      Date dateNow = new Date(System.currentTimeMillis());

      if (username != null && expirationDate != null && dateNow.before(expirationDate)) {
        return true;
      }
    }
    return false;
  }

  private Claims getClaims(String jwtToken) {
    try {
      return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(jwtToken).getBody();
    } catch (Exception e) {
      return null;
    }
  }

  public String getUsername(String jwtToken) {
    Claims claims = getClaims(jwtToken);
    if (claims != null) {
      String username = claims.getSubject();
      return username;
    }
    return null;
  }
}
