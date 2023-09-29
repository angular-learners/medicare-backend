package com.ad.medicare.repository;

import com.ad.medicare.entity.DoctorPersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorPersonalInformationRepository extends JpaRepository<DoctorPersonalInformation,Long> {
}
