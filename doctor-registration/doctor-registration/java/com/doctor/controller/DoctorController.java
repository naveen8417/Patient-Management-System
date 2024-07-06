package com.doctor.controller;
 
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doctor.entity.DoctorEntity;
import com.doctor.exception.DoctorNotFoundException;
import com.doctor.repo.DoctorRepo;
import com.doctor.service.DoctorService;
import com.itextpdf.text.DocumentException;

import lombok.AllArgsConstructor;
 
@AllArgsConstructor
@Controller
@RequestMapping({ "doctors", "/" })
public class DoctorController {
	@Autowired
	DoctorRepo patrepo;
	
	private DoctorService doctorService;
	@GetMapping( "register")
	public String dashboard() {
		return "register";
	}
	@GetMapping( "List")
	public String dashboard1() {
		return "List";
	}
 
	@PostMapping("save")
	public String data(@ModelAttribute DoctorEntity doctorEntity, Model model) {
	    // Check if the userId already exists
		  
	    if (doctorService.existsById(doctorEntity.getDoctorid())) {
	        String errorMessage = "User ID already exists";
	        model.addAttribute("errorMessage", errorMessage);
	        return "register"; // Return to the registration form with an error message
	    }
 
	    // Check if the email already exists
	    if (doctorService.existsByEmail(doctorEntity.getDoctoremail())) {
	        String errorMessage = "Email already exists";
	        model.addAttribute("errorMessage", errorMessage);
	        return "register"; // Return to the registration form with an error message
	    }
 
	    Long doctorid = doctorService.insert(doctorEntity);
	    String emailRegex = "^(.+)@(.+)$";
	    Pattern emailPattern = Pattern.compile(emailRegex);
	    Matcher emailMatcher = emailPattern.matcher(doctorEntity.getDoctoremail());
 
	    if (!emailMatcher.matches() || doctorEntity.getDoctoremail().length() > 50) {
	        return "redirect:/index";
	    }
	    String message = "doctor '" + doctorid + "' Created";
	    // send message to UI
	    model.addAttribute("message", message);
	    return "register";
	}
 
	@GetMapping({"all","/"})
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getPatients(@PageableDefault(page = 0, size = 3) Pageable pageable,
			@RequestParam(required = false) String message, Model model) {
		Page<DoctorEntity> page = doctorService.getAllPatients(pageable);
		// send this data to UI/View
		model.addAttribute("list", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("message", message);
		return "doctorList";
	}
	
	@GetMapping("/list")
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getPatientsList(@PageableDefault(page = 0, size = 3) Pageable pageable,
	                              @RequestParam(required = false) String message, Model model) {
	    Page<DoctorEntity> page = doctorService.getAllPatients(pageable);
	    // Send the doctor list and pagination details to the view
	    model.addAttribute("list", page.getContent());
	    model.addAttribute("page", page);
	    model.addAttribute("message", message);
	    return "List"; // Return the view name
	}
	
	
	
	
	
	//4. Delete based on ID
	@GetMapping("/delete")
	public String doDelete(@RequestParam Long doctorid,RedirectAttributes attributes)
	{
		try {
			//perform delete operation
			doctorService.deletePatient(doctorid);
			//send message to All
			attributes.addAttribute("message", "Patient '"+doctorid+"' Deleted");
		} catch (DoctorNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		//redirect
		return "redirect:http://localhost:8888/doctors/all";
	}
 
	//5. Show data in Edit Page
	@GetMapping("/edit")
	public String showEdit(
			@RequestParam Long doctorid,
			Model model
			)
	{
		String page = null;
		try {
			//fetch data from DB using service
			DoctorEntity doctorEntity = doctorService.getOnePatient(doctorid);
			//send to UI
			model.addAttribute("patient", doctorEntity);
			//goto edit page
			page = "doctorEdit";
		} catch (DoctorNotFoundException e) {
			e.printStackTrace();
			//read exception message
			model.addAttribute("message", e.getMessage());
			//goto all page
			page ="redirect:all";
		}
		return page;
	}
	@GetMapping("/view")
	public String showView(@RequestParam Long doctorid ,Model model)
	{
		String page = null;
		try {
			//fetch data from DB using service
			DoctorEntity doctorEntity = doctorService.getOnePatient(doctorid);
			//send to UI
			model.addAttribute("patient", doctorEntity);
			//goto edit page
			page = "doctorView";
		} catch (DoctorNotFoundException e) {
			e.printStackTrace();
			//read exception message
			model.addAttribute("message", e.getMessage());
			//goto all page
			page ="redirect:all";
		}
		return page;
	}
 
	//6. Update on Edit Form submit
	@PostMapping("/update")
	public String updateForm(@RequestParam Long doctorid, @ModelAttribute DoctorEntity patient, RedirectAttributes attributes)
	{
		//update form data
		doctorService.updatePatient(patient);
		attributes.addAttribute("message", "PATIENT '"+patient.getDoctorid()+"' Updated");
		return "redirect:all";
	}
	
	
	
	@GetMapping("/register/{doctorid}")
	public ResponseEntity<List<DoctorEntity>> showPatientById(@PathVariable long doctorid) {
	    java.util.Optional<DoctorEntity> patientOptional = patrepo.findById(doctorid); // Find PatientEntity by ID
	    // Check if patientOptional contains a value
	    if (patientOptional.isPresent()) {
	        // If patientOptional contains a value, return it in the response body
	        DoctorEntity patient = patientOptional.get();
	        return ResponseEntity.ok(Collections.singletonList(patient));
	    } else {
	        // If patientOptional does not contain a value, return a not found response
	        return ResponseEntity.notFound().build();
	    }
	}
 
	
	    
	 @GetMapping("/downloadExcel")
	    public ResponseEntity<byte[]> downloadExcel() {
	    	 List<DoctorEntity> patients = doctorService.getAllPatients();

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
	         headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	         headers.setContentDispositionFormData("attachment", "doctors.xlsx");
	         headers.setContentLength(excelBytes.length);

	         // Return the Excel file as a ResponseEntity
	         return ResponseEntity.ok()
	                              .headers(headers)
	                              .body(excelBytes);
	     }
	    @GetMapping("/exportToPDF")
	    public ResponseEntity<byte[]> exportToPDF() {
	        try {
	            List<DoctorEntity> patients = doctorService.getAllPatients();
	            byte[] pdfBytes = ExcelExporters.exportToPDF(patients);
	            return ResponseEntity.ok()
	                    .header("Content-Disposition", "attachment; filename=doctors.pdf")
	                    .body(pdfBytes);
	        } catch (DocumentException | IOException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }
	    
	    @PostMapping("/uploadExcel")
	    public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) {
	        if (file.isEmpty()) {
	            model.addAttribute("message", "Please select a file to upload.");
	            return "redirect:http://localhost:8888/doctors/";
	        }

	        try {
	            // Read Excel file into InputStream
	            InputStream inputStream = file.getInputStream();

	            // Import data from Excel
	            ExcelExporter.importFromExcel(inputStream);

	            model.addAttribute("message", "File uploaded successfully and data imported.");
	        } catch (IOException e) {
	            model.addAttribute("message", "Error uploading or importing data: " + e.getMessage());
	        }

	        return "redirect:http://localhost:8888/doctors/";
	    }
	}


	
