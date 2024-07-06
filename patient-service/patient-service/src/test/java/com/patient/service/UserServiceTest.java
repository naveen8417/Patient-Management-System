package com.patient.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.patient.entity.PatientEntity;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	PatientServiceImpl patientserve;


	@Test
	public void testInsert() {
		// Create a dummy patient entity
		PatientEntity patient = new PatientEntity(); 

		// Test insertion
		assertNotNull(patientserve.insert(patient));
	}

	@Test
	public void testGetAll() {
		// Test getting all patients
		assertNotNull(patientserve.getAll());
	}

	@Test
	public void testGetAllPatients() {
		// Test getting all patients
		assertNotNull(patientserve.getAllPatients());
	}

	@Test
	public void testGetOnePatient() {
		// Assuming there's a patient with ID 4007L
		long id = 4007L;

		// Test getting one patient by ID
		assertNotNull(patientserve.getOnePatient(id));
	}

	@Test
	public void testGetAllPatientsPageable() {
		// Create a dummy Pageable object
		Pageable pageable = PageRequest.of(0, 10);

		// Test getting all patients with pagination
		assertNotNull(patientserve.getAllPatients(pageable));
	}

	

	@Test
	public void testExistsByEmail() {
		// Test if a patient exists with a specific email
		assertTrue(patientserve.existsByEmail("virat@gmail.com"));
	}

	@Test
	public void testExistsByPhoneno() {
		// Test if a patient exists with a specific phone number
		assertTrue(patientserve.existsByPhoneno("8784389376"));
	}

	@Test
	public void testExistsById() {
		// Assuming there's a patient with ID 1L
		long id = 4008L;

		// Test if a patient exists with a specific ID
		assertTrue(patientserve.existsById(id));
	}

	@Test
	public void testSave() {
		// Create a dummy patient entity
		PatientEntity patient = new PatientEntity();

		// Test saving a patient
		assertNotNull(patientserve.save(patient));
	}

	@Test
	public void testDeletePatient() {
		// Assuming there's a patient with ID 1L
		long id = 1L;

		// Test deleting a patient
		assertDoesNotThrow(() -> patientserve.deletePatient(id));
	}

}
