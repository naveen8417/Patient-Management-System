package com.naveenj.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naveenj.model.UserDetails;
import com.naveenj.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping({ "disease", "/" })
public class HomeController {

	@Autowired
	private RestTemplate restTemplate;

//    @Autowired
//    private UserRepository userrepo;

	UserService user;
	
	@GetMapping({ "/index", "/" })
	public String index() {
		return "index";
		
	}

	@GetMapping("/search")
	public String search(@RequestParam("diseaseId") long diseaseId, RedirectAttributes redirectAttributes) {
		// Redirect to the /index endpoint with the diseaseId as a query parameter
		return "redirect:/index1?diseaseid=" + diseaseId;
	}
	 @GetMapping("/getUserName")
	    public String getUserName(HttpSession session) {
	        // Retrieve user's name from session
	        String userName = (String) session.getAttribute("Name"); // Assuming the user's name is stored in the session as "userName"
	        return "{\"userName\": \"" + userName + "\"}";
	    }

//
	@GetMapping("/index1")
	public String displayUserDetails(Model model, @RequestParam("diseaseid") long diseaseId) {
		try {
			long userId = diseaseId;

			String url1 = "http://localhost:8085/doctor/register/" + userId;
			ResponseEntity<UserDetails[]> response1 = restTemplate.exchange(url1, HttpMethod.GET, null,
					UserDetails[].class);
			UserDetails[] userDetailsArray1 = response1.getBody();

			String url2 = "http://localhost:9091/patient/register/" + userId;
			ResponseEntity<UserDetails[]> response2 = restTemplate.exchange(url2, HttpMethod.GET, null,
					UserDetails[].class);
			UserDetails[] userDetailsArray2 = response2.getBody();

			if (userDetailsArray1 != null && userDetailsArray1.length > 0 && userDetailsArray2 != null
					&& userDetailsArray2.length > 0) {
				UserDetails user1 = userDetailsArray1[0];
				UserDetails user2 = userDetailsArray2[0];
				// Add user details to the model
				model.addAttribute("id", user2.getId());
				model.addAttribute("name", user2.getPatientName());
				model.addAttribute("email", user2.getEmail());
				model.addAttribute("phoneno", user2.getPhoneno());
				model.addAttribute("bloodGroup", user1.getBloodgroup());
				model.addAttribute("patientGen", user2.getPatientGen());
				model.addAttribute("age", user2.getAge());
				model.addAttribute("date", user2.getBookingDate());
				model.addAttribute("time", user2.getAppointmentTime());
				model.addAttribute("doctorid", user1.getDoctorid());
				model.addAttribute("doctorname", user1.getDoctorname());
				model.addAttribute("doctoremail", user1.getEmail());
				model.addAttribute("doctorphoneno", user1.getPhoneno());
				model.addAttribute("BP", user1.getBP());
				model.addAttribute("Sugar", user1.getSugar());
				model.addAttribute("disease1", user1.getDisease());
				model.addAttribute("healthcondition", user1.getHealthcondition());
				model.addAttribute("precaution", user1.getPrecaution());
			} else {
				// Handle the case when no UserDetails is returned
				// For example, you can add an error message to the model
				return "redirect:http://localhost:8888/disease/";
			}
		} catch (Exception ex) {
			// Handle exceptions properly
			ex.printStackTrace(); // Log the exception for debugging purposes
			return "redirect:http://localhost:8888/disease/";
		}

		return "index";
	}

}
