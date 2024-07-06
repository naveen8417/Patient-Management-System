package com.doctor.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name ="doctors_details")
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long doctorid;
	   
   
    private String doctorname;
    private String doctoremail;
    private String doctorphoneno;
    private String address;
    private String specialisation;
    private String availability;
   
	
	
	
}
