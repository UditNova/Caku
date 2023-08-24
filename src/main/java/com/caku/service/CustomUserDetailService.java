package com.caku.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.caku.model.CustomUserDetail;
import com.caku.model.User;
import com.caku.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    
    public boolean doesUserExistByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Optional<User> user=userRepository.findUserByEmail(email);

        user.orElseThrow(()->new UsernameNotFoundException("User name not found!"));
        System.out.println("customUserDetails service called");
        return user.map(CustomUserDetail::new).get();
    }
    
}
