package com.helpduck.helpduckusers.repository;

import java.util.Optional;

import com.helpduck.helpduckusers.entity.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;


public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);

  @Query("{'user.name': {$regex: ?0 }})")
  Page<User> findAllByName(Pageable pageable, String name);
}