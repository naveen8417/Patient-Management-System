package com.naveenj.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.naveenj.service.UserService;

@SpringBootTest
public class diseasetest {
	@Mock
	UserService user;

	@Test
	public void testGetUserById() {
		Long id = 4007L;
		String url1 = "http://localhost:8085/doctor/register/" + id;
		assertNotNull(url1);

	}

} 
