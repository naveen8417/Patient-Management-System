package com.naveenj.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.naveenj.model.UserDetails;
import com.naveenj.repository.UserRepository;
import com.naveenj.util.EmailUtil;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements userservice {

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private EmailUtil emailUtil;

	@Override
	public UserDetails createUser(UserDetails user) {
		// TODO Auto-generated method stub
		return userrepo.save(user);
	}

	@Override
	public boolean checkEmail(String email) {

		return userrepo.existsByEmail(email);
	}

	@Transactional
	public void updatePasswordByEmail(String email, String newPassword) {
		// Find the user by email
		UserDetails user = userrepo.findByEmail(email);

		if (user != null) {
			// Update the user's password
			user.setPassword(newPassword);
			user.setConfirmpassword(newPassword);
			// Save the updated user object to the database

		}
	}

	public UserDetails authenticate(String email, String password) {
		UserDetails user = userrepo.findByEmail(email); // Retrieve user by email from the database
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Check if the user exists and if the provided password matches the hashed
		// password in the database
		if (user != null && passwordEncoder.matches(password, user.getPassword())) {
			return user; // Return the authenticated user
		}
		return null; // Return null if authentication fails
	}

	public boolean authenticated(String password1, String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

		// Check if the user exists and if the provided password matches the hashed
		// password in the database
		if (passwordEncoder.matches(password1, password)) {
			return true; // Return the authenticated user
		}
		return false; // Return null if authentication fails
	}

	public String getOldPasswordByEmail(String email) {
		// Retrieve user by email from the UserRepository
		UserDetails user = userrepo.findByEmail(email);

		// Check if user exists
		if (user != null) {
			// Return the old password of the user (assuming it's stored in a field named
			// oldPassword)
			return user.getPassword();
		} else {
			// User does not exist, return null or handle accordingly
			return null;
		}
	}

	@Override
	public String getByenmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forgotPassword(String email) {
		UserDetails user = userrepo.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User with email " + email + " not found.");
		}

		// Generate a random temporary password
		String newPassword = generateRandomPassword();

		// Hash the new password using bcrypt
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(newPassword);

		// Update the user's password in the database
		user.setPassword(hashedPassword);
		userrepo.save(user);

		// Send the new password via email
		try {
			emailUtil.sendsetPassword(email, newPassword);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send email.");
		}

		return "Check your email for the new password.";
	}

	private String generateRandomPassword() {
		// Generate a random alphanumeric password
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder newPassword = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			newPassword.append(characters.charAt(random.nextInt(characters.length())));
		}
		return newPassword.toString();
	}

	@Override
	public List<UserDetails> allmails() {
	
		return userrepo.findAll();
	}
	
	 @Override
	 public void updateRoleByEmail(String email, String role) {
	        // Retrieve the user by email
	        UserDetails user = userrepo.findByEmail(email);

	        // Update the role if the user is found
	        if (user != null) {
	            user.setRole(role);
	            userrepo.save(user); // Save the updated user
	        } else {
	            // Handle case where user is not found by email
	            throw new RuntimeException("User not found for email: " + email);
	        }
	    }

}
