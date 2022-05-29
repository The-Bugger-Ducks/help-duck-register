package com.helpduck.helpduckusers.model.hateoas;

import java.time.LocalDateTime;

import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.enums.DepartmentEnum;
import com.helpduck.helpduckusers.enums.RoleEnum;

import org.springframework.data.annotation.Id;
import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserHateoas extends RepresentationModel<UserHateoas> {

  @Id
  private String id;

  private String firstName;
  private String lastName;

  private String email;
  private String password;

  private RoleEnum role;
  private DepartmentEnum department;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public UserHateoas(User user) {
    id = user.getId();

    firstName = user.getFirstName();
    lastName = user.getLastName();

    email = user.getEmail();
    password = user.getPassword();

    role = user.getRole();
    department = user.getDepartment();

    createdAt = user.getCreatedAt();
    updatedAt = user.getUpdatedAt();
  }
}
