package com.naveenj.service;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.naveenj.model.UserDetails;
import com.naveenj.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails createUser(UserDetails user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}
	public List<UserDetails> getAll() {
		List<UserDetails> list = userRepo.findAll();
		return list;

	}
	@Override
	public List<UserDetails> getAllPatients() {
		// TODO Auto-generated method stub
		 
		    return userRepo.findAll(); // Assuming findAll() method returns a list of patients
		   
	}
	public Page<UserDetails> getAllPatients(Pageable pageable) {
		return userRepo.findAll(pageable);
	}
    
	 public void deleteAllAppointments() {
	        userRepo.deleteAll(); // Assuming your AppointmentRepository extends JpaRepository
	    }

	

}
