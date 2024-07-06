package com.naveenj.service;

import java.util.List;

import com.naveenj.model.UserDetails;

public interface userservice {
	public UserDetails createUser(UserDetails user);

	public boolean checkEmail(String email);

	public UserDetails authenticate(String email, String password);

	public void updatePasswordByEmail(String email, String newPassword);

	public String getOldPasswordByEmail(String email);

	public String getByenmail(String email);

	public String forgotPassword(String email);
	public List<UserDetails> allmails();
	 public void updateRoleByEmail(String email, String role);

	public boolean authenticated(String password1, String password);
}
