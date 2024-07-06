package com.doctor.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.doctor.entity.DoctorEntity;
import com.doctor.exception.DoctorNotFoundException;
import com.doctor.repo.DoctorRepo;

@Service
public class DoctorServiceImpl implements DoctorService{
    
	@Autowired
	private DoctorRepo doctorRepo;

	
	public Long insert(DoctorEntity patient) {
		DoctorEntity record = doctorRepo.save(patient);
		return record.getId();
	}

	public List<DoctorEntity> getAll() {
		List<DoctorEntity> list = doctorRepo.findAll();
		return list;

	}

	public Page<DoctorEntity> getAllPatients(Pageable pageable) {
		return doctorRepo.findAll(pageable);
	}

	@Override
	public void deletePatient(Long id) {
		doctorRepo.deleteById(id);

	}

	@Override
	public DoctorEntity getOnePatient(Long id) {

		Optional<DoctorEntity> opt = doctorRepo.findById(id);
		if (opt.isPresent()) {
			DoctorEntity e = opt.get();
			return e;
		} else
			throw new DoctorNotFoundException("Doctor '" + id + "' NOT EXIST");

	}

	@Override
	public void updatePatient(DoctorEntity doctorEntity) {
		if (doctorRepo.existsById(doctorEntity.getId()))
			doctorRepo.save(doctorEntity);
		else
			throw new DoctorNotFoundException("PATIENT '" + doctorEntity.getId() + "' NOT EXIST");
	}

	@Override
	public List<DoctorEntity> getAll(Pageable pageable) {
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
		return doctorRepo.existsByEmail(email);
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return doctorRepo.existsById(id);
	}

	@Override
	public DoctorEntity save(DoctorEntity doctorEntity) {
		// TODO Auto-generated method stub
		
		        return doctorRepo.save(doctorEntity);
		    
	
	}

	@Override
	public List<DoctorEntity> getAllPatients() {
		// TODO Auto-generated method stub
		 
		    return doctorRepo.findAll(); // Assuming findAll() method returns a list of patients
		   
	}

}
