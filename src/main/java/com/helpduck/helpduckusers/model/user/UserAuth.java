package com.helpduck.helpduckusers.model.user;

import com.helpduck.helpduckusers.enums.RoleEnum;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuth {

  @Id
  private String id;

  private String firstName;
  private String lastName;

  private String email;

  private RoleEnum role;

  private String token;

  public UserAuth(UserDetails userData, String tokenString) {
    email = userData.getUsername();

    // role = userData.getRole();

    token = tokenString;
  }

}
