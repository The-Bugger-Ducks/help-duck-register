package com.helpduck.helpduckusers.controller.dto;

import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.enums.RoleEnum;
import com.helpduck.helpduckusers.enums.DepartmentEnum;

import lombok.Data;

@Data
public class UserLoginDTO {
  private String id;
  private String email;
  private String firstName;
  private String lastName;
  private DepartmentEnum department;
  private RoleEnum role;

  public UserLoginDTO(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.department = user.getDepartment();
    this.role = user.getRole();
  }

  public static UserLoginDTO create(User user) {
    return new UserLoginDTO(user);
  }
}
