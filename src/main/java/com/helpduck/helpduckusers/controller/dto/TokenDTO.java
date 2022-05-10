package com.helpduck.helpduckusers.controller.dto;

import com.helpduck.helpduckusers.entity.User;

import lombok.Data;

@Data
public class TokenDTO {

  private String token;
  private String type;
  private UserLoginDTO user;

  public TokenDTO(String token, String type, User user) {
    this.token = token;
    this.type = type;

    this.user = UserLoginDTO.create(user);
  }

}
