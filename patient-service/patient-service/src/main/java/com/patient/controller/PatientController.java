package com.patient.controller;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;
import com.patient.entity.PatientEntity;
import com.patient.exception.PatientNotFoundException;
import com.patient.repo.PatientRepo;
import com.patient.service.PatientService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "patient", "/" })
@CrossOrigin(origins = ".")
public class PatientController {

	@Autowired
	PatientRepo patrepo;

	@Autowired
	private PatientService patientService;

	@GetMapping("register")
	public String dashboard() {
		return "register";
	}

	@PostMapping("save")
	public String data(@ModelAttribute PatientEntity patientEntity, Model model, HttpSession session) {
		// Check if the userId already exists
		if (patientService.existsById(patientEntity.getId())) {
			String message = "User ID already exists";
			model.addAttribute("message", message);
			session.setAttribute("message", "Invalid email format or length (max 50 characters)");
			return "register"; // Return to the registration form with an error message
		}
		if (patientService.existsByPhoneno(patientEntity.getPhoneno())) {
			String message = "phoneno already exists";
			model.addAttribute("message", message);
			return "register"; // Return to the registration form with an error message

		}

		// Check if the email already exists
		if (patientService.existsByEmail(patientEntity.getEmail())) {
			String message = "Email already exists";
			model.addAttribute("message", message);
			return "register"; // Return to the registration form with an error message
		}

		Long id = patientService.insert(patientEntity);
		String emailRegex = "^(.+)@(.+)$";
		Pattern emailPattern = Pattern.compile(emailRegex);
		Matcher emailMatcher = emailPattern.matcher(patientEntity.getEmail());

		if (!emailMatcher.matches() || patientEntity.getEmail().length() > 50) {
			return "redirect:/index";
		}
		String message = "patient '" + id + "' Created";
		// send message to UI
		model.addAttribute("message", message);
		return "register";
	}

	@GetMapping({ "all", "/" })
	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getPatients(@PageableDefault(page = 0, size = 3) Pageable pageable,
			@RequestParam(required = false) String message, Model model) {
		Page<PatientEntity> page = patientService.getAllPatients(pageable);
		// send this data to UI/View
		model.addAttribute("list", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("message", message);
		return "patientList";
	}

	// Delete based on ID
	@GetMapping("/delete")
	public String doDelete(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			// Perform delete operation
			patientService.deletePatient(id);
			// Send message to All
			attributes.addAttribute("message", "Patient '" + id + "' Deleted");
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		// Redirect
		return "redirect:http://localhost:8888/patient/all";
	}

	// Show data in Edit Page
	@GetMapping("/edit")
	public String showEdit(@RequestParam Long id, Model model) {
		String page = null;
		try {
			// Fetch data from DB using service
			PatientEntity patientEntity = patientService.getOnePatient(id);
			// Send to UI
			model.addAttribute("patient", patientEntity);
			// Go to edit page
			page = "PatientEdit";
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
			// Read exception message
			model.addAttribute("message", e.getMessage());
			// Go to all page
			page = "redirect:all";
		}
		return page;
	}

	@GetMapping("/view")
	public String showView(@RequestParam Long id, Model model) {
		String page = null;
		try {
			// Fetch data from DB using service
			PatientEntity patientEntity = patientService.getOnePatient(id);
			// Send to UI
			model.addAttribute("patient", patientEntity);
			// Go to edit page
			page = "PatientView";
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
			// Read exception message
			model.addAttribute("message", e.getMessage());
			// Go to all page
			page = "redirect:all";
		}
		return page;
	}

	// Update on Edit Form submit
	@PostMapping("/update")
	public String updateForm(@ModelAttribute PatientEntity patient, RedirectAttributes attributes) {
		// Update form data
		patientService.updatePatient(patient);
		attributes.addAttribute("message", "PATIENT '" + patient.getId() + "' Updated");
		return "redirect:http://localhost:8888/patient/all";
	}

	@GetMapping("/register/{id}")
	public ResponseEntity<List<PatientEntity>> showPatientById(@PathVariable long id) {
		java.util.Optional<PatientEntity> patientOptional = patrepo.findById(id); // Find PatientEntity by ID
		// Check if patientOptional contains a value
		if (patientOptional.isPresent()) {
			// If patientOptional contains a value, return it in the response body
			PatientEntity patients = patientOptional.get();
			return ResponseEntity.ok(Collections.singletonList(patients));

		} else {
			// If patientOptional does not contain a value, return a not found response
			return ResponseEntity.notFound().build();
		}
	}

	// Send a GET request to the /downloadExcel endpoint
	@GetMapping("/downloadExcel")
	public ResponseEntity<byte[]> downloadExcel() {
		List<PatientEntity> patients = patientService.getAllPatients();

		// Generate Excel bytes from the patient data
		byte[] excelBytes;
		try {
			excelBytes = ExcelExporter.exportToExcel(patients);
		} catch (IOException e) {
			// Handle the exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		// Set the content type and attachment disposition headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(
				MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
		headers.setContentDispositionFormData("attachment", "patients.xlsx");
		headers.setContentLength(excelBytes.length);

		// Return the Excel file as a ResponseEntity
		return ResponseEntity.ok().headers(headers).body(excelBytes);
	}

	@GetMapping("/exportToPDF")
	public ResponseEntity<byte[]> exportToPDF() {
		try {
			List<PatientEntity> patients = patientService.getAllPatients();
			byte[] pdfBytes = ExcelExporters.exportToPDF(patients);
			return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=patients.pdf")
					.body(pdfBytes);
		} catch (DocumentException | IOException e) {
			e.printStackTrace(); // Handle the exception appropriately
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	
	@GetMapping("/getAllData/{email}")
	public ResponseEntity<List<PatientEntity>> showPatientByEmail(@PathVariable String email) {
	    java.util.Optional<PatientEntity> patientOptional = patrepo.findByEmail(email); // Find PatientEntity by email
	    // Check if patientOptional contains a value
	    if (patientOptional.isPresent()) {
	        // If patientOptional contains a value, return it in the response body
	        PatientEntity patient = patientOptional.get();
	        return ResponseEntity.ok(Collections.singletonList(patient));
	    } else {
	        // If patientOptional does not contain a value, return a not found response
	        return ResponseEntity.notFound().build();
	    }
	}

}
