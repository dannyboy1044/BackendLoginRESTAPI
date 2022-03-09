package com.dannyg.springboottut.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dannyg.springboottut.models.Role;

public interface RoleRepo extends JpaRepository<Role, Long>{
	Role findByName(String name);
}
