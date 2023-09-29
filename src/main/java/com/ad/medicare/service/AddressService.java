package com.ad.medicare.service;

import com.ad.medicare.entity.Address;
import com.ad.medicare.entity.User;

public interface AddressService {

     Address addAddressByUserId(Long userId,Address address);
}
