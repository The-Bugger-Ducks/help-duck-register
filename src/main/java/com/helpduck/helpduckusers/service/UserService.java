package com.helpduck.helpduckusers.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.model.hateoas.UserHateoas;
import com.helpduck.helpduckusers.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  @Transactional(readOnly = true)
  public Page<UserHateoas> findAll(Pageable pageable) {
    Page<User> users = repository.findAll(pageable);
    Page<UserHateoas> page = users.map(x -> new UserHateoas(x));
    return page;
  }

  @Transactional(readOnly = true)
  public UserHateoas findById(String id) {
    Optional<User> user = repository.findById(id);
    if (user.isEmpty()) {
      return null;
    }

    UserHateoas userHateoas = new UserHateoas(user.get());
    return userHateoas;
  }

  @Transactional(readOnly = true)
  public User createUser(User user) {
    BCryptPasswordEncoder toCriptografy = new BCryptPasswordEncoder();
    String passwordEncrypted = toCriptografy.encode(user.getPassword());
    user.setPassword(passwordEncrypted);

    user.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
    user.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

    User userInserted = repository.insert(user);
    return userInserted;
  }
}
