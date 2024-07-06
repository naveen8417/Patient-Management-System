package com.doctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.doctor.entity.DoctorEntity;
import com.doctor.exception.DoctorNotFoundException;
import com.doctor.repo.DoctorRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor   
public class DoctorServiceImpl implements DoctorService {

	private DoctorRepo doctorRepo;

	
	public Long insert(DoctorEntity patient) {
		DoctorEntity record = doctorRepo.save(patient);
		return record.getDoctorid();
	}

	public List<DoctorEntity> getAll() {
		List<DoctorEntity> list = doctorRepo.findAll();
		return list;

	}

	public Page<DoctorEntity> getAllPatients(Pageable pageable) {
		return doctorRepo.findAll(pageable);
	}

	@Override
	public void deletePatient(Long doctorid) {
		doctorRepo.deleteById(doctorid);

	}

	@Override
	public DoctorEntity getOnePatient(Long doctorid) {

		Optional<DoctorEntity> opt = doctorRepo.findById(doctorid);
		if (opt.isPresent()) {
			DoctorEntity e = opt.get();
			return e;
		} else
			throw new DoctorNotFoundException("PATIENT '" + doctorid + "' NOT EXIST");

	}

	@Override
	public void updatePatient(DoctorEntity doctorEntity) {
		if (doctorRepo.existsById(doctorEntity.getDoctorid()))
			doctorRepo.save(doctorEntity);
		else
			throw new DoctorNotFoundException("PATIENT '" + doctorEntity.getDoctorid() + "' NOT EXIST");
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
	public boolean existsByEmail(String doctoremail) {
		// TODO Auto-generated method stub
		return doctorRepo.existsByDoctoremail(doctoremail);
	}

	@Override
	public boolean existsById(Long doctorid) {
		// TODO Auto-generated method stub
		return doctorRepo.existsByDoctorid(doctorid);
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
