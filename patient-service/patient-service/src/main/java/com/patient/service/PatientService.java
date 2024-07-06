package com.patient.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.patient.entity.PatientEntity;

public interface PatientService {

	Long insert(PatientEntity patient);

	List<PatientEntity> getAll(Pageable pageable);

	void deletePatient(Long id);

	PatientEntity getOnePatient(Long id);

	List<PatientEntity> getAllPatients();

	public Page<PatientEntity> getAllPatients(Pageable pageable);

	void updatePatient(PatientEntity patientEntity);

	public byte[] generatePdfData();

	boolean existsByEmail(String email);

	boolean existsByPhoneno(String phoneno);

	boolean existsById(Long id);

	public PatientEntity save(PatientEntity patientEntity);
}
