package com.naveenj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naveenj.model.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Long> {

	public boolean existsByEmail(String email);

	public UserDetails findByEmail(String email);

}
