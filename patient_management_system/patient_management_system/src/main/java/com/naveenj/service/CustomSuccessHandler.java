package com.naveenj.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		var authourities = authentication.getAuthorities();
		var roles = authourities.stream().map(r -> r.getAuthority()).findFirst();
		
		if (roles.orElse("").equals("USER")) {
			response.sendRedirect("/patient");
		} else if (roles.orElse("").equals("doctor")) {
			response.sendRedirect("/doctor");
		} else {
			response.sendRedirect("/disease");
		}
		
	}

}
