package com.ad.medicare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressResponse {
    private Long id;
    private String city;
    private String country;
    private String landmark;
    private int pinCode;
    private Long mobileNumber;
}
