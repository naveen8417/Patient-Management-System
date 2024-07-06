package com.naveenj.controller;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naveenj.model.UserDetails;
import com.naveenj.repository.UserRepository;
import com.naveenj.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "user", "/" })
public class HomeController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userrepo;

	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/doctorList")
	public String index2() {
		return "doctorList";
	}

	@GetMapping("/userList")
	public String index1() {
		return "userList";
	}

	@PostMapping("/createUser")
	public String createUser(@ModelAttribute UserDetails user, HttpSession session) {
		// Validate email format and length
		String emailRegex = "^(.+)@(.+)$";
		Pattern emailPattern = Pattern.compile(emailRegex);
		Matcher emailMatcher = emailPattern.matcher(user.getEmail());

		if (!emailMatcher.matches()) {
			session.setAttribute("msg", "Invalid email format");
			return "redirect:/index";
		}

		UserDetails userDetails = userService.createUser(user);
		if (userDetails != null) {
			session.setAttribute("msg", "Registered Successfully");
		} else {
			session.setAttribute("msg", "Registration failed");
		}

		return "redirect:/index";
	}

	@GetMapping({ "all", "/" })
	// @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getPatients(@PageableDefault(page = 0, size = 3) Pageable pageable,
			@RequestParam(required = false) String message, Model model) {
//		Page<UserDetails> page = userService.getAllPatients(pageable);
		Page<UserDetails> page = userService.getAllPatients(pageable);
		// send this data to UI/View
		model.addAttribute("list", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("message", message);
		return "userlist";
	}

	@GetMapping("/getAllPatients")
	public ResponseEntity<List<UserDetails>> getAllPatients() {
		List<UserDetails> patients = userService.getAllPatients(); // Assuming getAllPatients() method returns a list of
																	// UserDetails
		return new ResponseEntity<>(patients, HttpStatus.OK);
	}

	@GetMapping("/getDoctorAppointments")
	public ResponseEntity<List<UserDetails>> getAllPatients1() {
		List<UserDetails> patients = userService.getAllPatients(); // Assuming getAllPatients() method returns a list of
																	// UserDetails
		return new ResponseEntity<>(patients, HttpStatus.OK);
	}

	@PostMapping("/updateAppointmentStatus")
	public ResponseEntity<String> updateAppointmentStatus(@RequestParam("appointmentid") Long appointmentId,
			@RequestParam("status") String status) {
		try {
			// Implement logic to update appointment status in the database based on
			// appointmentId
			// You can use the userrepo to update the appointment status
			UserDetails userDetails = userrepo.findById(appointmentId).orElse(null);
			if (userDetails != null) {
				userDetails.setStatus(status);
				userrepo.save(userDetails);
				return ResponseEntity.ok("Appointment with ID: " + appointmentId + " changed to status: " + status);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Appointment with ID: " + appointmentId + " not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating appointment status: " + e.getMessage());
		}
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<String> deleteAllAppointments() {
		try {
			userService.deleteAllAppointments(); // Assuming you have a method in AppointmentService to delete all
													// appointments
			return new ResponseEntity<>("All appointments deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to delete appointments: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/delete")
	public ResponseEntity<String> deleteAppointmentById(@RequestParam long appointmentid) {
		try {
			Optional<UserDetails> appointmentOptional = userrepo.findById(appointmentid);
			if (appointmentOptional.isPresent()) {
				UserDetails appointment = appointmentOptional.get();
				userrepo.delete(appointment);
				return ResponseEntity.noContent().build(); // Return 204 status for successful deletion
			} else {
				return ResponseEntity.notFound().build(); // Return 404 status if appointment not found
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

}
