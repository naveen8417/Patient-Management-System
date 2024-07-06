package com.patient.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patient.entity.PatientEntity;

public interface PatientRepo extends JpaRepository<PatientEntity, Long> {

	List<PatientEntity> findByIdIn(List<Long> userIdList);

	boolean existsByEmail(String email);

	boolean existsByPhoneno(String phoneno);

	boolean existsById(Long id);
	Optional<PatientEntity> findByEmail(String email);
}
