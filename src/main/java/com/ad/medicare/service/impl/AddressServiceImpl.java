package com.ad.medicare.service.impl;

import com.ad.medicare.dto.AddressResponse;
import com.ad.medicare.entity.Address;
import com.ad.medicare.entity.User;
import com.ad.medicare.exception.UserNotFoundException;
import com.ad.medicare.repository.AddressRepository;
import com.ad.medicare.repository.UserRepository;
import com.ad.medicare.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address addAddressByUserId(Long userId, Address address) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            address.setUser(user); // Set the user for the address
            return addressRepository.save(address);
        } else {
            // Handle the case where the user does not exist
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
    }

    @Override
    public List<AddressResponse> findAllAddressesByUserId(Long userId) {
        List<Address> addressList = addressRepository.findAllByUserId(userId);
        return addressList.stream().map(address ->
                new AddressResponse(address.getId(),
                        address.getCity(), address.getCountry(), address.getLandmark(), address.getPinCode(), address.getMobileNumber())).collect(Collectors.toList());

    }

}
