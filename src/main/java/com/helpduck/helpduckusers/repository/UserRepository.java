package com.helpduck.helpduckusers.repository;

import java.util.Optional;

import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.enums.RoleEnum;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);

  // { }
  @Query("{ $or: [ {'firstName': { $regex:?0, '$options' : 'i' }}, {'lastName': { $regex:?0, '$options' : 'i' }} ]}")
  Page<User> searchUsername(Pageable page, String username);

  @Query("{ $or: [ {'firstName': { $regex:?0, '$options' : 'i' }}, {'lastName': { $regex:?0, '$options' : 'i' }} ],  $and: [{'role': ?1 }] }")
  Page<User> searchUsernameAndFilterPerRole(Pageable page, String username, RoleEnum role);

  Page<User> findByRole(Pageable page, RoleEnum role);
}