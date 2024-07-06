package com.doctor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name ="doctor_details")
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {
	
	@Id
	   private long id;
	   
    private long doctorid;
    private String doctorname;
    private String email;
    private String phoneno;
    private String address;
    private String about;
    private String patientname;
    private String patientage;
    private String bloodgroup;
    private String BP;
    private String Sugar;
    private String disease;
    private String healthcondition;
    private String precaution;
	
	
	
}
