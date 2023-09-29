package com.ad.medicare.service.impl;

import com.ad.medicare.dto.UserResponse;
import com.ad.medicare.entity.DoctorPersonalInformation;
import com.ad.medicare.entity.User;
import com.ad.medicare.exception.UserNotFoundException;
import com.ad.medicare.repository.DoctorPersonalInformationRepository;
import com.ad.medicare.repository.UserRepository;
import com.ad.medicare.service.UserService;
import com.ad.medicare.util.MedicareGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorPersonalInformationRepository doctorPersonalInformationRepository;

    @Override
    public User createUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setTypeId(MedicareGenerator.generateId(user.getRole()));
        user.setPassword(password);
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public UserResponse updateUserById(User user, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User updateUser = optionalUser.get();
            updateUser.setPassword(user.getPassword());
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setEmail(user.getEmail());
            User updatedUser = userRepository.save(updateUser);
            UserResponse userResponse = new UserResponse();
            userResponse.setFirstName(updatedUser.getFirstName());
            userResponse.setLastName(updatedUser.getLastName());
            userResponse.setEmail(updatedUser.getEmail());
            userResponse.setPassword(updatedUser.getPassword());
            userResponse.setActive(updatedUser.isActive());
            userResponse.setRole(updatedUser.getRole());
            userResponse.setTypeId(updatedUser.getTypeId());
            userResponse.setStatus(updateUser.getStatus());
            userResponse.setUsername(updatedUser.getUsername());
            userResponse.setUpdatedDate(updatedUser.getUpdatedDate());
            userResponse.setCreatedDate(updatedUser.getCreatedDate());
            return userResponse;
        } else {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> users=userRepository.findAll();
        return  users.stream().map(user -> {
            UserResponse userResponse=new UserResponse();
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setEmail(user.getEmail());
            userResponse.setPassword(user.getPassword());
            userResponse.setActive(user.isActive());
            userResponse.setRole(user.getRole());
            userResponse.setTypeId(user.getTypeId());
            userResponse.setStatus(user.getStatus());
            userResponse.setUsername(user.getUsername());
            userResponse.setUpdatedDate(user.getUpdatedDate());
            userResponse.setCreatedDate(user.getCreatedDate());
            return userResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long userId) {

    }

    @Override
    public DoctorPersonalInformation addDoctorPersonalInformationByUserId(Long userId, DoctorPersonalInformation doctorPersonalInformation) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            doctorPersonalInformation.setUser(user); // Set the user for the address
            return doctorPersonalInformationRepository.save(doctorPersonalInformation);
        } else {
            // Handle the case where the user does not exist
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
    }
}
