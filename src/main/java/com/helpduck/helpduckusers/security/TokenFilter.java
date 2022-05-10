package com.helpduck.helpduckusers.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.helpduck.helpduckusers.repository.UserRepository;
import com.helpduck.helpduckusers.service.UserPrincipal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenFilter extends OncePerRequestFilter {

  private TokenService tokenService;
  private UserRepository userRepository;

  public TokenFilter(TokenService tokenService, UserRepository userRepository) {
    this.tokenService = tokenService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    String token = getToken(request);

    boolean isTokenValid = tokenService.validateToken(token);

    if (isTokenValid) {
      authenticateUser(token);
    }

    chain.doFilter(request, response);
  }

  private void authenticateUser(String token) {
    String email = tokenService.getUsername(token);

    UserPrincipal user = UserPrincipal.create(
        userRepository.findByEmail(email).get());

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
        user.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String getToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }

    return token = token.replace("Bearer ", "");
  }

}
