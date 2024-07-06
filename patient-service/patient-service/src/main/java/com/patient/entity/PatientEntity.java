package com.patient.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "patient_details")
@AllArgsConstructor
@NoArgsConstructor
public class PatientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated value using database identity column
	private long id;

	private String patientName;
	private String patientGen;
	private String disease;
	private String bloodGroup;
	private String email;
	private String phoneno;
	private String dob;
	private int age;
	private String guardianName;
	private String guardianPhone;
	private String currentStreet;
	private String currentVillage;
	private String currentPostOffice;
	private String currentDistrict;
	private String currentCountry;
	private String currentPincode;
	private String permanentStreet;
	private String permanentVillage;
	private String permanentPostOffice;
	private String permanentDistrict;
	private String permanentCountry;
	private String permanentPincode;
	private String bookingDate;
	private String appointmentTime;

}
