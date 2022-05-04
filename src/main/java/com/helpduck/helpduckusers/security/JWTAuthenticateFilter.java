package com.helpduck.helpduckusers.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpduck.helpduckusers.entity.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
      User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
          user.getEmail(),
          user.getPassword(),
          new ArrayList<>()

      ));

    } catch (Exception e) {
      throw new RuntimeException("Error to authenticate the user " + e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {
    UserDetails userData = (UserDetails) authResult.getPrincipal();

    String token = jwtUtil.gerarToken(userData.getUsername());

    response.getWriter().write(token);
    response.getWriter().flush();
  }
}
