package com.rishabh.expensemanager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rishabh.expensemanager.dto.AuthDTO;
import com.rishabh.expensemanager.dto.ProfileDTO;
import com.rishabh.expensemanager.service.ProfileService;

@RestController
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@PostMapping("/register")
	public ResponseEntity<ProfileDTO> registerProfile(@RequestBody ProfileDTO profileDTO){
		System.out.println("Hi in register");
		System.out.println(profileDTO.toString());
		ProfileDTO registeredProfile = profileService.registeredProfile(profileDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredProfile);
	}
	
	@GetMapping("/activate")
	public ResponseEntity<String> activateProfile(@RequestParam String token){
		
		boolean isActivated = profileService.activateProfile(token);
		if(isActivated) {
			return ResponseEntity.ok("Profile activated successfully");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation token not found or already used");
		}
		
	}
	
	@PostMapping("login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDTO authDTO){
		
		try {
			if(!profileService.isAccountActive(authDTO.getEmail())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
						"message" ,"Account is not active Please activate your account"
				));
				
			}
			Map<String, Object> response = profileService.authenticatAndGenerateToken(authDTO);
			return ResponseEntity.ok(response);
		}catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
					"message" ,e.getMessage()
			));
			
		}
			
	}
	
	@GetMapping("/profile")
	public ResponseEntity<ProfileDTO> getPublicProfile(){
		
		System.out.println("inside profile");
		
		ProfileDTO profileDTO= profileService.getPublicProfile(null);
		System.out.println(profileDTO.toString());
		return ResponseEntity.ok(profileDTO);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
