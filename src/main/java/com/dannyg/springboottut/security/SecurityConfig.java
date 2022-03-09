package com.dannyg.springboottut.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dannyg.springboottut.filter.CustomAuthenticationFilter;
import com.dannyg.springboottut.filter.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@NonNull
	private final UserDetailsService userDetailsService;
	@NonNull
	private final BCryptPasswordEncoder bCrypt;
	
	
	public SecurityConfig(@NonNull UserDetailsService userDetailsService, @NonNull BCryptPasswordEncoder bCrypt) {
		super();
		this.userDetailsService = userDetailsService;
		this.bCrypt = bCrypt;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(bCrypt);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/login", "/api/token/refresh").permitAll();
		http.authorizeRequests().antMatchers("/api/users").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
		http.authorizeRequests().antMatchers("/api/user/save").hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().antMatchers("/api/role/**").hasAnyAuthority("ROLE_ADMIN");	
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

		
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
		
}
