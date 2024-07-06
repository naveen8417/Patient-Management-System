package com.doctor.Configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.doctor.repo.DoctorRepo;
import com.doctor.service.DoctorServiceImpl;

@Configuration
public class DoctorServiceTestConfig {

    @Bean
    public DoctorServiceImpl doctorService(DoctorRepo doctorRepo) {
        return new DoctorServiceImpl(doctorRepo);
    }

    // Define any other beans required for testing here
}