package com.helpduck.helpduckusers.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTValitadeFilter extends BasicAuthenticationFilter {

  public static final String HEADER_ATTRIBUITES = "Authorization";
  public static final String ATTRIBUITES_PREFIX = "Bearer ";

  @Autowired
  private JWTUtil jwtUtil;

  public JWTValitadeFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    String attribute = request.getHeader(HEADER_ATTRIBUITES);

    if (attribute == null) {
      chain.doFilter(request, response);
      return;
    }

    if (!attribute.startsWith(ATTRIBUITES_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    String token = attribute.replace(ATTRIBUITES_PREFIX, "");

    UsernamePasswordAuthenticationToken authenticaitonToken = getAuthenticationToken(token);

    SecurityContextHolder.getContext().setAuthentication(authenticaitonToken);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
    String user = jwtUtil.getUsername(token);

    if (user == null) {
      return null;
    }

    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
  }

}
