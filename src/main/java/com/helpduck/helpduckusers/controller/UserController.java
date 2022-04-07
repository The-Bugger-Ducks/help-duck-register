package com.helpduck.helpduckusers.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import com.helpduck.helpduckusers.entity.User;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@GetMapping("/")
	public ResponseEntity<Page<UserHateoas>> getUsers(Pageable pageable) {
		ResponseEntity<Page<UserHateoas>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Page<UserHateoas> pageUserHateoas = service.findAll(pageable);

		if (!pageUserHateoas.isEmpty()) {
			linkAdder.addLink(pageUserHateoas);
			response = new ResponseEntity<Page<UserHateoas>>(pageUserHateoas, HttpStatus.FOUND);
		}
		return response;
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserHateoas> getUser(@PathVariable String id) {
		ResponseEntity<UserHateoas> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		UserHateoas userHateoas = service.findById(id);
		if (userHateoas != null) {
			linkAdder.addLink(userHateoas);
			response = new ResponseEntity<UserHateoas>(userHateoas, HttpStatus.FOUND);
		}
		return response;
	}

	@PostMapping("/create")
	public ResponseEntity<User> ClientRegister(@RequestBody User user) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (user.getId() == null) {
			BCryptPasswordEncoder toCriptografy = new BCryptPasswordEncoder();
      String passwordEncrypted = toCriptografy.encode(user.getPassword());
      user.setPassword(passwordEncrypted);

			user.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
			user.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
			repository.save(user);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<User>(status);

	}

	@PutMapping("/update")
	public ResponseEntity<?> ClientUpdate(@RequestBody User updatedUser) {
		HttpStatus status = HttpStatus.CONFLICT;
		Optional<User> userOptional = repository.findById(updatedUser.getId());
		if (!userOptional.isEmpty()) {
			User user = userOptional.get();
			UserUpdater updater = new UserUpdater();
			updater.Update(user, updatedUser);
			repository.save(user);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> DeleteClient(@PathVariable String id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Optional<User> userOptional = repository.findById(id);
		if (!userOptional.isEmpty()) {
			repository.delete(userOptional.get());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
