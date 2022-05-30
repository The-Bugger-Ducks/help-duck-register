package com.helpduck.helpduckusers.controller;

import java.util.Optional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.enums.RoleEnum;
import com.helpduck.helpduckusers.model.hateoas.UserHateoas;
import com.helpduck.helpduckusers.model.user.UserLinkAdder;
import com.helpduck.helpduckusers.model.user.UserUpdater;
import com.helpduck.helpduckusers.repository.UserRepository;
import com.helpduck.helpduckusers.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository repository;
	@Autowired
	private UserLinkAdder linkAdder;
	@Autowired
	private UserService service;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PreAuthorize("hasRole('admin')")
	@GetMapping("/")
	public ResponseEntity<Page<UserHateoas>> getUsers(Pageable pageable) {

		Page<UserHateoas> pageUserHateoas = service.findAll(pageable);
		if (pageUserHateoas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		linkAdder.addLink(pageUserHateoas);
		return new ResponseEntity<Page<UserHateoas>>(pageUserHateoas, HttpStatus.FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserHateoas> getUser(@PathVariable String id) {

		UserHateoas userHateoas = service.findById(id);
		if (userHateoas == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		linkAdder.addLink(userHateoas);
		return new ResponseEntity<UserHateoas>(userHateoas, HttpStatus.FOUND);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<UserHateoas>> getAllUserByUsername(
			@RequestParam Optional<RoleEnum> role,
			@RequestParam Optional<String> username,
			Pageable page) {

		Page<UserHateoas> pageUserHateoas;

		if (username.isPresent() && role.isPresent()) {
			pageUserHateoas = service.searchUsernameAndFilterPerRole(page, username.get(), role.get());
		} else if (!username.isPresent() && !role.isPresent()) {
			pageUserHateoas = service.findAll(page);
		} else if (username.isPresent() && !role.isPresent()) {
			pageUserHateoas = service.searchUsername(page, username.get());
		} else {
			pageUserHateoas = service.findByRole(page, role.get());
		}

		if (pageUserHateoas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		linkAdder.addLink(pageUserHateoas);
		return new ResponseEntity<Page<UserHateoas>>(pageUserHateoas, HttpStatus.FOUND);
	}

	@PreAuthorize("hasRole('admin')")
	@PostMapping("/create")
	public ResponseEntity<User> UserCreate(@RequestBody User user) {

		if (user.getId() != null) {
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}

		service.createUser(user);
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<User> UserUpdate(@RequestBody User updatedUser) {

		HttpStatus status = HttpStatus.CONFLICT;
		Optional<User> userOptional = repository.findById(updatedUser.getId());
		if (!userOptional.isEmpty()) {
			User user = userOptional.get();
			UserUpdater updater = new UserUpdater();
			updater.updateData(user, updatedUser);
			repository.save(user);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<User>(status);
	}

	@PutMapping("/update-password")
	public ResponseEntity<User> updatePassword(@RequestBody ObjectNode objectNode) {

		HttpStatus status = HttpStatus.CONFLICT;
		String userId = objectNode.get("userId").asText();
		String newPassword = objectNode.get("newPassword").asText();
		String oldPassword = objectNode.get("oldPassword").asText();

		Optional<User> userOptional = repository.findById(userId);
		if (!userOptional.isEmpty()) {
			User user = userOptional.get();
			String dbPassword = user.getPassword();

			if (passwordEncoder.matches(oldPassword, dbPassword)) {
				user.setPassword(passwordEncoder.encode(newPassword));
				repository.save(user);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.UNAUTHORIZED;
			}

		} else {
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<User>(status);
	}

	@PreAuthorize("hasRole('admin')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<User> DeleteUser(@PathVariable String id) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		Optional<User> userOptional = repository.findById(id);
		if (!userOptional.isEmpty()) {
			repository.delete(userOptional.get());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<User>(status);
	}

}
