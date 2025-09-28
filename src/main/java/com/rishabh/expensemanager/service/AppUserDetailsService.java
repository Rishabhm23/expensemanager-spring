package com.rishabh.expensemanager.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rishabh.expensemanager.entity.ProfileEntity;
import com.rishabh.expensemanager.repository.ProfileRepository;



@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	ProfileRepository profileRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		ProfileEntity existingProfile = profileRepository.findByEmail(email)
						.orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: "+ email));
		
		return User.builder()
				.username(existingProfile.getEmail())
				.password(existingProfile.getPassword())
				.authorities(Collections.emptyList())
				.build();
	}
	
	
	
	

}
