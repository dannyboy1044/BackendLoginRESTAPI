package com.dannyg.springboottut.service;

import java.util.List;

import com.dannyg.springboottut.models.AppUser;
import com.dannyg.springboottut.models.Role;

public interface AppUserService {
	AppUser saveUser(AppUser user);
	Role saveRole(Role role);
	void addRoleToUser(String username, String roleName);
	AppUser getUser(String username);
	
	//When a request is made send a page with first say 10 users, then another request if the client wants to see more
	//Don't load all the users
	List<AppUser> getUsers();
	
	
}
