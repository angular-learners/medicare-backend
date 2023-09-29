package com.ad.medicare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "doctor_personal_information")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DoctorPersonalInformation {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String experience;
    private String lastWorkedHospital;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updatedDate;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
