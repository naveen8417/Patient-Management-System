package com.naveenj.test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.naveenj.model.UserDetails;
import com.naveenj.service.UserServiceImpl;

@SpringBootTest
public class LoginServiceTest {

    @Autowired
    UserServiceImpl userService;

    @Test
    public void testCreateUser() {
        // Create a dummy user
        UserDetails user = new UserDetails();
        user.setEmail("test@example.com");
        user.setPassword("password");

        // Test createUser method
        UserDetails createdUser = userService.createUser(user);

        // Verify that the created user is not null
        assertNotNull(createdUser);
        // Verify that the email matches
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testCheckEmail() {
        // Test checkEmail method
        assertTrue(userService.checkEmail("naveenjk8417@gmail.com"));
    }

    @Test
    public void testAuthenticate() {
        // Test authenticate method with correct credentials
        UserDetails authenticatedUser = userService.authenticate("naveenk@gmail.com", "@Navi123");

        // Verify that the authenticated user is not null
        assertNotNull(authenticatedUser);
    }

  

  

   

    
}

