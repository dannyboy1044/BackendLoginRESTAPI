package com.dannyg.springboottut.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dannyg.springboottut.models.AppUser;
import com.dannyg.springboottut.models.Role;
import com.dannyg.springboottut.repo.AppUserRepo;
import com.dannyg.springboottut.repo.RoleRepo;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService{
	
	@NonNull
	private final AppUserRepo userRepo;
	@NonNull
	private final RoleRepo roleRepo;
	@NonNull
	private final PasswordEncoder passwordEncoder;
	

	public AppUserServiceImpl(@NonNull AppUserRepo userRepo, @NonNull RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public AppUser saveUser(AppUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(roleName);
		user.getRoles().add(role);
	}

	@Override
	public AppUser getUser(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public List<AppUser> getUsers() {
		return userRepo.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepo.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found in database");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(r -> {authorities.add(new SimpleGrantedAuthority(r.getName()));});
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

}
