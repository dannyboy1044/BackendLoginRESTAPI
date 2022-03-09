package com.dannyg.springboottut.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dannyg.springboottut.models.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, Long>{
	AppUser findByUsername(String username);
}
