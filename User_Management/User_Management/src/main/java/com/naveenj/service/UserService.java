package com.naveenj.service;




import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.naveenj.model.UserDetails;

public interface UserService {
	public  UserDetails createUser(UserDetails user);
	public List<UserDetails> getAll();
	public List<UserDetails> getAllPatients();
	public Page<UserDetails> getAllPatients(Pageable pageable);
	public void deleteAllAppointments();
	
	
}
