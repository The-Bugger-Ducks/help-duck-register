package com.helpduck.helpduckusers.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Setter;

@Setter
public class LoginForm {
  private String email;
  private String password;

  public UsernamePasswordAuthenticationToken converter() {
    return new UsernamePasswordAuthenticationToken(email, password);
  }
}
