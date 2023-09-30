package com.ad.medicare.controller;

import com.ad.medicare.dto.*;
import com.ad.medicare.entity.Address;
import com.ad.medicare.entity.DoctorPersonalInformation;
import com.ad.medicare.entity.User;
import com.ad.medicare.repository.UserRepository;
import com.ad.medicare.service.AddressService;
import com.ad.medicare.service.JwtService;
import com.ad.medicare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicare")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @PostMapping("/users/createAccount")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/users/authenticate")
    public UserLoginResponse authenticateAndGetToken(@RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            User user = optionalUser.get();
            UserLoginResponse loginResponse = new UserLoginResponse();
            loginResponse.setToken(jwtService.generateToken(loginRequest.getUsername()));
            loginResponse.setUsername(user.getUsername());
            loginResponse.setEmail(user.getEmail());
            loginResponse.setRole(user.getRole());
            loginResponse.setUserId(user.getId());
            return loginResponse;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @PostMapping("/users/address/{userId}")
    public ResponseEntity<AddressResponse> createAddressForUser(
            @PathVariable Long userId,
            @RequestBody Address address) {
        Address createdAddress = addressService.addAddressByUserId(userId, address);
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setCity(createdAddress.getCity());
        addressResponse.setId(createdAddress.getId());
        addressResponse.setLandmark(createdAddress.getLandmark());
        addressResponse.setCountry(createdAddress.getCountry());
        addressResponse.setMobileNumber(createdAddress.getMobileNumber());
        addressResponse.setPinCode(createdAddress.getPinCode());
        if (createdAddress == null) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(addressResponse);
    }


    @GetMapping("/users/welcome")
    public String getMessage() {
        return "Welcome to Medicare Application";
    }

    @GetMapping("/users/addresses/{userId}")
    public ResponseEntity<List<AddressResponse>> getAddressesByUserId(@PathVariable Long userId) {
        List<AddressResponse> addresses = addressService.findAllAddressesByUserId(userId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping("/users/doctorPersonalInfo/{userId}")
    public ResponseEntity<DoctorPersonalInformationResponse> createDoctorPersonalInformationByUserId(@PathVariable Long userId, @RequestBody DoctorPersonalInformation doctorPersonalInformation) {
        DoctorPersonalInformation createdDoctorPersonalInfo = userService.addDoctorPersonalInformationByUserId(userId, doctorPersonalInformation);
        DoctorPersonalInformationResponse doctorPersonalInformationResponse = new DoctorPersonalInformationResponse();
        doctorPersonalInformationResponse.setId(createdDoctorPersonalInfo.getId());
        doctorPersonalInformationResponse.setLastWorkedHospital(createdDoctorPersonalInfo.getLastWorkedHospital());
        doctorPersonalInformationResponse.setCreatedDate(createdDoctorPersonalInfo.getCreatedDate());
        doctorPersonalInformationResponse.setExperience(createdDoctorPersonalInfo.getExperience());
        doctorPersonalInformationResponse.setUpdatedDate(createdDoctorPersonalInfo.getUpdatedDate());
        return new ResponseEntity<>(doctorPersonalInformationResponse, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserResponse> updateUserById(@RequestBody User user, @PathVariable Long userId) {
        UserResponse userResponse = userService.updateUserById(user, userId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponseList=userService.findAllUsers();
        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
    }
}

