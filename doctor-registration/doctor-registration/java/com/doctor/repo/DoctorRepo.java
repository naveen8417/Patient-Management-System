package com.doctor.repo;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import com.doctor.entity.DoctorEntity;

public interface DoctorRepo extends JpaRepository<DoctorEntity, Long> {

	
	 List<DoctorEntity> findByDoctoridIn(List<Long> doctorIdList);
	    boolean existsByDoctoremail(String email);
	    boolean existsByDoctorid(Long doctorId);
}
