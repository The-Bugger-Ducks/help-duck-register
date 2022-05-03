package com.helpduck.helpduckusers.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticateFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  private JWTUtil jwtUtil;

  public JWTAuthenticateFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {

    try {
      String username = "thiago@gmail.com";
      String password = "1234";

      BCryptPasswordEncoder toCriptografy = new BCryptPasswordEncoder();
      String passwordEncrypted = toCriptografy.encode(password);

      System.out.println("batata");
      System.out.println(username);
      System.out.println(password);

      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
          passwordEncrypted, new ArrayList<>());

      Authentication authentication = authenticationManager.authenticate(authenticationToken);

      return authentication;
    } catch (Exception e) {
      throw new RuntimeException("Error to authenticate the user " + e.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
    UserDetails userData = (UserDetails) authResult.getPrincipal();
    String token = jwtUtil.gerarToken(userData.getUsername());

    response.addHeader("Authorization", "Bearer " + token);

  }
}
