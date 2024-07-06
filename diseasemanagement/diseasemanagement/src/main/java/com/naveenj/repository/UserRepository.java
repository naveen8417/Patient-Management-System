
package com.naveenj.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.naveenj.model.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Long> {

	UserDetails getUserById(long diseaseId);
  
}