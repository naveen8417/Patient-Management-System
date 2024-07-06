package com.patient.service;

import java.util.List;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.patient.entity.PatientEntity;
import com.patient.exception.PatientNotFoundException;
import com.patient.repo.PatientRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

	private PatientRepo patientRepo;

	public Long insert(PatientEntity patient) {
		PatientEntity record = patientRepo.save(patient);
		return record.getId();
	}

	public List<PatientEntity> getAll() {
		List<PatientEntity> list = patientRepo.findAll();
		return list;

	}

	public Page<PatientEntity> getAllPatients(Pageable pageable) {
		return patientRepo.findAll(pageable);
	}

	@Override
	public void deletePatient(Long id) {
		patientRepo.deleteById(id);

	}

	@Override
	public PatientEntity getOnePatient(Long id) {

		Optional<PatientEntity> opt = patientRepo.findById(id);
		if (opt.isPresent()) {
			PatientEntity e = opt.get();
			return e;
		} else
			throw new PatientNotFoundException("PATIENT '" + id + "' NOT EXIST");

	}

	@Override
	public void updatePatient(PatientEntity patientEntity) {
		if (patientRepo.existsById(patientEntity.getId()))
			patientRepo.save(patientEntity);
		else
			throw new PatientNotFoundException("PATIENT '" + patientEntity.getId() + "' NOT EXIST");
	}

	@Override
	public List<PatientEntity> getAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] generatePdfData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return patientRepo.existsByEmail(email);
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return patientRepo.existsById(id);
	}

	@Override
	public PatientEntity save(PatientEntity patientEntity) {
		// TODO Auto-generated method stub

		return patientRepo.save(patientEntity);

	}

	@Override
	public List<PatientEntity> getAllPatients() {
		// TODO Auto-generated method stub

		return patientRepo.findAll(); // Assuming findAll() method returns a list of patients

	}

	@Override
	public boolean existsByPhoneno(String phoneno) {
		// TODO Auto-generated method stub
		return patientRepo.existsByPhoneno(phoneno);
	}

}
