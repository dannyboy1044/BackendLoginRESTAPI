package com.dannyg.springboottut;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dannyg.springboottut.models.AppUser;
import com.dannyg.springboottut.models.Role;
import com.dannyg.springboottut.service.AppUserService;

@SpringBootApplication
public class SpringSecurityTutJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityTutJwtApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CommandLineRunner run(AppUserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			
			userService.saveUser(new AppUser(null,"Daniel Gallant", "dgallant00@yahoo.ca", "pass", new ArrayList<>()));
			userService.saveUser(new AppUser(null,"Dyl Gallant", "dylh2@yahoo.ca", "pass2", new ArrayList<>()));
			userService.saveUser(new AppUser(null,"Sun Gallant", "msjedd@gmail.com", "pass3", new ArrayList<>()));
			userService.saveUser(new AppUser(null,"Daniel Cook", "gallantdx01@gmail.com", "pass4", new ArrayList<>()));
			
			userService.addRoleToUser("dgallant00@yahoo.ca", "ROLE_ADMIN");
			userService.addRoleToUser("dgallant00@yahoo.ca", "ROLE_USER");
			userService.addRoleToUser("dylh2@yahoo.ca", "ROLE_USER");
			userService.addRoleToUser("msjedd@gmail.com", "ROLE_USER");
			userService.addRoleToUser("gallantdx01@gmail.com", "ROLE_USER");
			
		};
	}

}
