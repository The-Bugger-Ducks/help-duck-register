package com.helpduck.helpduckusers.service;

import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.model.hateoas.UserHateoas;
import com.helpduck.helpduckusers.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    User user = repository.findById(id).get();
    UserHateoas userHateoas= new UserHateoas(user);
    return userHateoas;
  }
}
