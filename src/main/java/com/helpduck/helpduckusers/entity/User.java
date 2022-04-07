package com.helpduck.helpduckusers.entity;

import java.time.LocalDateTime;

import com.helpduck.helpduckusers.enums.RoleEnum;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document()
public class User {

	@Id
	private String id;

	private String firstName;
	private String lastName;

	@Indexed(unique = true)
	private String email;
	private String password;

	private RoleEnum role;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}