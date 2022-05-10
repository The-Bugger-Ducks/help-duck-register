package com.helpduck.helpduckusers.controller;

import java.util.Optional;

import com.helpduck.helpduckusers.controller.dto.TokenDTO;
import com.helpduck.helpduckusers.controller.form.LoginForm;
import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.repository.UserRepository;
import com.helpduck.helpduckusers.security.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/authentication")
  public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginForm form) {
    UsernamePasswordAuthenticationToken payloadLogin = form.converter();

    try {
      Authentication authentication = authManager.authenticate(payloadLogin);

      Optional<User> user = userRepository.findByEmail(authentication.getName());

      String token = tokenService.generateToken(authentication.getName());

      return ResponseEntity.ok(new TokenDTO(token, "Bearer", user.get()));
    } catch (AuthenticationException e) {
      return new ResponseEntity<TokenDTO>(HttpStatus.UNAUTHORIZED);
    }
  }
}
