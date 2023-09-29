package com.ad.medicare.service;

import com.ad.medicare.config.CustomUserDetails;
import com.ad.medicare.entity.User;
import com.ad.medicare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByUsername(username);
        return user.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("USERNAME NOT FOUND EXCEPTION"));
    }
}
