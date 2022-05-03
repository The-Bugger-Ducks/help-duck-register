package com.helpduck.helpduckusers.service;

import java.util.Optional;

import com.helpduck.helpduckusers.entity.User;
import com.helpduck.helpduckusers.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<User> user = userRepository.findByEmail(username);

    if (user == null) {
      throw new UsernameNotFoundException("User does not exists! " + username);
    }

    return UserPrincipal.create(user.get());
  }

}
