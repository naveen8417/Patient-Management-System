package com.naveenj.controller;

import java.util.List;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naveenj.model.UserDetails;
import com.naveenj.repository.UserRepository;
import com.naveenj.service.userservice;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:8888")
@Controller
@RequestMapping({ "management", "/" })
public class HomeController {
	@Autowired
	private userservice userService;
	@Autowired
	private UserRepository userrepo;

	@GetMapping({ "/index", "/" })
	public String index() {
		return "index";
	}

	@GetMapping({ "/forgot" })
	public String index1() {
		return "forgot";
	}

	@GetMapping({ "/admin" })
	public String admin() {
		return "admin";
	}

	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(12);

	@PostMapping("/createUser")
	public String createUser(@ModelAttribute UserDetails user, HttpSession session) {
		// Validate email format and length
		String emailRegex = "^(.+)@(.+)$";
		Pattern emailPattern = Pattern.compile(emailRegex);
		Matcher emailMatcher = emailPattern.matcher(user.getEmail());

		if (!emailMatcher.matches() || user.getEmail().length() > 50) {
			session.setAttribute("msg", "Invalid email format or length (max 50 characters)");
			return "redirect:/index";
		}

		// Validate password format and length
		String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		Pattern passwordPattern = Pattern.compile(passwordRegex);
		Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());

		if (!passwordMatcher.matches()) {
			session.setAttribute("msg",
					"Invalid password format. Password should contain at least one uppercase letter, one lowercase letter, one digit, one special character, and should be at least 8 characters long.");
			return "redirect:/index";
		}

		boolean emailExists = userService.checkEmail(user.getEmail());

		if (emailExists) {
			session.setAttribute("msg", "Email id already registered");
		} else {
			if (user.getPassword().equals(user.getConfirmpassword())) {
				user.setPassword(bcrypt.encode(user.getPassword()));
				user.setConfirmpassword(bcrypt.encode(user.getConfirmpassword()));
				user.setRole("user");
				UserDetails userDetails = userService.createUser(user);
				if (userDetails != null) {
					session.setAttribute("msg", "Registered Successfully");
				} else {
					session.setAttribute("msg", "Registration failed");
				}
			} else {
				session.setAttribute("msg", "Passwords do not match");
			}
		}
		return "redirect:http://localhost:8888/management/";
	}

	@PostMapping("/management/login")
	public ResponseEntity<String> login(@ModelAttribute("user") UserDetails user) {

	    UserDetails authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());

	    if (authenticatedUser != null) {
	        // Retrieve user details from the database
	        UserDetails userDetails = userrepo.findByEmail(user.getEmail());

	        if (userDetails != null) {
	            // Redirect URL base
	            String redirectUrlBase = null;
	            
	            // Check the user's role
	            if ("user".equalsIgnoreCase(userDetails.getRole())) {
	                // Redirect to the patient page with email parameter
	                redirectUrlBase = "http://localhost:8888/patient/?email=";
	            } else if ("doctor".equalsIgnoreCase(userDetails.getRole())) {
	                // Redirect to the doctor page with email parameter
	                redirectUrlBase = "http://localhost:8888/doctor/?email=";
	            } else {
	                // Redirect to the disease page with user ID in the query parameter
	                String redirectUrl = "http://localhost:8888/disease/?userId=" + userDetails.getId();
	                return ResponseEntity.status(HttpStatus.FOUND).header("Location", redirectUrl).build();
	            }
	            
	            // Construct the redirect URL with email parameter
	            String redirectUrl = redirectUrlBase + userDetails.getEmail();
	            return ResponseEntity.status(HttpStatus.FOUND).header("Location", redirectUrl).build();
	        } else {
	            // User details not found
	            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:8888/management/").build();
	        }
	    } else {
	        // Set an error message in session and redirect to login page if authentication fails
	        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:8888/management/").build();
	    }
	}



	@PostMapping("/forgot-pass")
	public String forgot(@ModelAttribute("user") UserDetails user, HttpSession session) {

		// Check if the email exists in the database
		boolean emailExists = userService.checkEmail(user.getEmail());

		if (emailExists) {
			// Retrieve the user's old password from the database

			String Password = userService.getOldPasswordByEmail(user.getEmail());
			String Password1 = user.getPassword1();
			// Check if the old password matches the one provided by the user
			if (userService.authenticated(Password1, Password)) {
				// Check if the new passwords match
				String Password2 = bcrypt.encode(user.getConfirmpassword());

				// Update the user's password
				userService.updatePasswordByEmail(user.getEmail(), Password2);

				// Set a message to indicate successful password reset
				session.setAttribute("msg", "Password reset successfully");

				// Redirect to login page or any other appropriate page
				return "redirect:http://localhost:8888/management/";
			} else {
				// Set a message to indicate incorrect old password
				session.setAttribute("msg", "Incorrect old password");
				return "redirect:/forgot"; // Redirect back to forgot password page

			}
		} else {
			// Set a message to indicate email not found
			session.setAttribute("msg", "Email not found");
			return "redirect:/forgot"; // Redirect back to forgot password page
		}
	}

	@PutMapping("/forgot-password")
	public ResponseEntity<String> forgotpassword(@RequestParam String email) {
		return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserDetails>> allUsers() {
		List<UserDetails> userList = userService.allmails();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	 @PostMapping("/update")
	    public ResponseEntity<String> updateAppointmentStatus(@RequestParam("email") String email, @RequestParam("role") String role) {
	        try {
	            // Implement logic to update appointment status in the database based on appointmentId
	            // You can use the userrepo to update the appointment status
	            UserDetails userDetails = userrepo.findByEmail(email);
	            if (userDetails != null) {
	                userDetails.setRole(role);
	                userrepo.save(userDetails);
	                return ResponseEntity.ok("Appointment with ID: " + email + " changed to status: " + role);
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment with ID: " + email + " not found");
	            }
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating appointment status: " + e.getMessage());
	        }
	    }
	 
	 @GetMapping("/delete")
	 public ResponseEntity<String> deleteUserByEmail(@RequestParam String email , RedirectAttributes attributes) {
	     try {
	         UserDetails user = userrepo.findByEmail(email);
	         if (user != null) {
	             userrepo.delete(user);
	             return ResponseEntity.ok("User deleted successfully");
	         } else {
	             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
	         }
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	     }
	 }
    
	 @GetMapping("/details")
	 public ResponseEntity<UserDetails> getSingleUser(@RequestParam Long id) {
	     Optional<UserDetails> userOptional = userrepo.findById(id);
	     if (userOptional.isPresent()) {
	         UserDetails user = userOptional.get();
	         return new ResponseEntity<>(user, HttpStatus.OK);
	     } else {
	         return ResponseEntity.notFound().build();
	     }
	 }

	 

}
