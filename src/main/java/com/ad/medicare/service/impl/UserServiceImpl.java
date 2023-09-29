package com.ad.medicare.service.impl;

import com.ad.medicare.entity.User;
import com.ad.medicare.repository.UserRepository;
import com.ad.medicare.service.UserService;
import com.ad.medicare.util.MedicareGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        String password=passwordEncoder.encode(user.getPassword());
        user.setTypeId(MedicareGenerator.generateId(user.getRole()));
        user.setPassword(password);
        user.setActive(true);
        return userRepository.save(user);
    }
}
