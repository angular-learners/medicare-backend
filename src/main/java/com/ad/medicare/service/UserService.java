package com.ad.medicare.service;

import com.ad.medicare.dto.UserResponse;
import com.ad.medicare.entity.DoctorPersonalInformation;
import com.ad.medicare.entity.User;

import java.util.List;

public interface UserService {
     User createUser(User user);

    UserResponse updateUserById(User user, Long userId);

     List<UserResponse>  findAllUsers();

     void deleteUserById(Long userId);

    DoctorPersonalInformation addDoctorPersonalInformationByUserId(Long userId,  DoctorPersonalInformation doctorPersonalInformation);
}
