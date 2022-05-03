package com.helpduck.helpduckusers.controller;

import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.security.JWTUtil;
import com.helpduck.helpduckusers.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  CustomUserDetailsService customUserDetailsService;

  @Autowired
  JWTUtil jwtUtil;

  @PostMapping("/authentication")
  public ResponseEntity<?> signIn(@RequestBody User user) {

    // UserDetails userAux =
    // customUserDetailsService.loadUserByUsername(user.getEmail());

    // System.out.println(userAux);

    String token = jwtUtil.gerarToken(user.getEmail());

    return new ResponseEntity<String>(token, HttpStatus.OK);
  }
}
