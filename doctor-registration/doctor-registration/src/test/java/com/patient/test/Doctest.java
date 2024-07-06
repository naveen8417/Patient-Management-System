package com.patient.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.doctor.entity.DoctorEntity;
import com.doctor.service.DoctorService;

@SpringBootTest(classes = Doctest.class)
class Doctest {

	@Mock
	private DoctorService doctorService;

	@Test
	void testInsert() {
		DoctorEntity patient = new DoctorEntity();
		
		assertNotNull(doctorService.insert(patient));
	}

	@Test
	void testUpdatePatient() {
		DoctorEntity doctor = new DoctorEntity();
		doctor.setDoctorid(13L); // Assuming there is a doctor with ID 1L
		assertDoesNotThrow(() -> doctorService.updatePatient(doctor));
	}

	@Test
	void testExistsByEmail() {
		String email = "naveenjk8417@gmail.com";
		assertNotNull( doctorService.existsByEmail(email));
		
	}

	@Test
	void testExistsById() {
		Long id = 13L; // Assuming there is a patient with ID 1L
		assertNotNull(doctorService.existsById(id));
		
	}

	
}
