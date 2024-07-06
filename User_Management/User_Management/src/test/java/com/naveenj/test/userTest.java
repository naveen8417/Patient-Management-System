package com.naveenj.test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.naveenj.model.UserDetails;
import com.naveenj.repository.UserRepository;
import com.naveenj.service.UserService;

@SpringBootTest
public class userTest{

    @Mock
    private UserService userServe;
    @Test
    public void testGetAll() { 
        assertNotNull(userServe.getAll());
    }
    
    @Test
    public void createUser() { 
    	UserDetails user = new UserDetails(1,"ff","ff","ff","ff","ff","ff","ff","ff","ff","ff","ff");
        assertNull(userServe.createUser(user));
    }
    
   
        


}
