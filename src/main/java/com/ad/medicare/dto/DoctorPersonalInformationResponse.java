package com.ad.medicare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorPersonalInformationResponse {
    private Long Id;
    private String experience;
    private String lastWorkedHospital;
    private Date createdDate;
    private Date updatedDate;
}
