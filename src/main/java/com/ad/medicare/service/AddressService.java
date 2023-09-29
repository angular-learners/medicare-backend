package com.ad.medicare.service;

import com.ad.medicare.dto.AddressResponse;
import com.ad.medicare.entity.Address;
import com.ad.medicare.entity.User;

import java.util.List;

public interface AddressService {

     Address addAddressByUserId(Long userId,Address address);
      List<AddressResponse> findAllAddressesByUserId(Long userId);
}
