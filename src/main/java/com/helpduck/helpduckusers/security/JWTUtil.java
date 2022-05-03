package com.helpduck.helpduckusers.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {

  @Value("${jwt.secretkey}")
  private String secretkey;

  @Value("${jwt.expiration}")
  private String expiration;

  public String gerarToken(String email) {
    Date dateNow = new Date();
    Date dataExpiracao = new Date(dateNow.getTime() + Long.parseLong(expiration));

    return Jwts.builder().setIssuer("BATATINHA QUENTE - HEHE")
        .setSubject(email)
        .setIssuedAt(dateNow)
        .setExpiration(dataExpiracao)
        .signWith(SignatureAlgorithm.HS256, secretkey).compact();
  }

  public boolean validarToken(String jwtToken) {
    Claims reivindicacoes = obterReivindicacoes(jwtToken);
    if (reivindicacoes != null) {
      String nomeUsuario = reivindicacoes.getSubject();
      Date dataExpiracao = reivindicacoes.getExpiration();
      Date agora = new Date(System.currentTimeMillis());
      if (nomeUsuario != null && dataExpiracao != null && agora.before(dataExpiracao)) {
        return true;
      }
    }
    return false;
  }

  private Claims obterReivindicacoes(String jwtToken) {
    try {
      return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(jwtToken).getBody();
    } catch (Exception e) {
      return null;
    }
  }

  public String obterNomeUsuairo(String jwtToken) {
    Claims reivindicacoes = obterReivindicacoes(jwtToken);
    if (reivindicacoes != null) {
      String nomeUsuario = reivindicacoes.getSubject();
      return nomeUsuario;
    }
    return null;
  }
}
