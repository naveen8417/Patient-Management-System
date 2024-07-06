package com.doctor.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctor.entity.DoctorEntity;

@Repository
public interface DoctorRepo extends JpaRepository<DoctorEntity, Long> {

	List<DoctorEntity> findByIdIn(List<Long> userIdList);
     boolean existsByEmail(String email);
	
    boolean existsById(Long id);
}
