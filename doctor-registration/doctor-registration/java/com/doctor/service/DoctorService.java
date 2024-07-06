package com.doctor.service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.doctor.entity.DoctorEntity;

public interface DoctorService {
	
	

	
	

	Long insert(DoctorEntity patient);
	
	List<DoctorEntity> getAll(Pageable pageable);
	void deletePatient(Long doctorid);
	DoctorEntity getOnePatient(Long doctorid);
	List<DoctorEntity> getAllPatients();
	public Page<DoctorEntity> getAllPatients(Pageable pageable);
	void updatePatient(DoctorEntity doctorEntity);
	public byte[] generatePdfData();
	
	boolean existsByEmail(String doctoremail);
	
	boolean existsById(Long doctorid);
	 public DoctorEntity save(DoctorEntity doctorEntity);
}
