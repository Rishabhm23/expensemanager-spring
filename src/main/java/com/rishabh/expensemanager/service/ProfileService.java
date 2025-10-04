package com.rishabh.expensemanager.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rishabh.expensemanager.dto.AuthDTO;
import com.rishabh.expensemanager.dto.ProfileDTO;
import com.rishabh.expensemanager.entity.ProfileEntity;
import com.rishabh.expensemanager.repository.ProfileRepository;
import com.rishabh.expensemanager.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	
	@Value("${app.activation.url}")
	private String activationURL;
	
	public ProfileDTO registeredProfile(ProfileDTO profileDTO) {
		
		ProfileEntity newProfile = toEntity(profileDTO);
		newProfile.setActivationToken(UUID.randomUUID().toString());
		newProfile = profileRepository.save(newProfile);
		String activationLink = activationURL+"/api/v1.0/activate?token="+newProfile.getActivationToken();
		String subject = "Activate your money Manager Account";
		String body = "Click on the following link to activate your account: " + activationLink;
		emailService.sendEmail(newProfile.getEmail(), subject, body);
		
		System.out.println("outside email service");

		return toDTO(newProfile);
		
		
	}
	
	public ProfileEntity toEntity(ProfileDTO profileDTO) {
		
		ProfileEntity profileEntity = new ProfileEntity();
		profileEntity.setId(profileDTO.getId());
		profileEntity.setEmail(profileDTO.getEmail());
		profileEntity.setFullname(profileDTO.getFullname());
		profileEntity.setPassword(passwordEncoder.encode(profileDTO.getPassword()));
		profileEntity.setProfileImageUrl(profileDTO.getProfileImageUrl());
		profileEntity.setCreatedAt(profileDTO.getCreatedAt());
		profileEntity.setUpdatedAt(profileDTO.getUpdatedAt());
		
		return profileEntity;
	}
	
	public ProfileDTO toDTO(ProfileEntity profileEntity) {
		
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setId(profileEntity.getId());
		profileDTO.setEmail(profileEntity.getEmail());
		profileDTO.setFullname(profileEntity.getFullname());
		profileDTO.setProfileImageUrl(profileEntity.getProfileImageUrl());
		profileDTO.setCreatedAt(profileEntity.getCreatedAt());
		profileDTO.setUpdatedAt(profileEntity.getUpdatedAt());
		
		return profileDTO;
		
	}
	
	public boolean activateProfile(String activationToken) {
		return profileRepository.findByActivationToken(activationToken)
				.map(profile -> {
					profile.setIsActive(true);
					profileRepository.save(profile);
					System.out.println("Inside find at");					
					return true;
				})
				.orElse(false);
				
	}
	
	public boolean isAccountActive(String email) {
		return profileRepository.findByEmail(email)
				.map(ProfileEntity::getIsActive)
				.orElse(false);
	}
	
	public ProfileEntity getCurrentProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return profileRepository.findByEmail(authentication.getName())
		.orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: "+ authentication.getName()));
		
	}
	
	public ProfileDTO getPublicProfile(String email) {
		
		ProfileEntity currentUser = null;
		
		
		if(email == null) {
			currentUser = getCurrentProfile();
		}else {
			currentUser = profileRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: "+ email));
		}
		
		return toDTO(currentUser);
		
		
	}

	public Map<String, Object> authenticatAndGenerateToken(AuthDTO authDTO) throws Exception {
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(),authDTO.getPassword()));
			String token = jwtUtil.generateToken(authDTO.getEmail());
			return Map.of(
					"token",token,
					"user", getPublicProfile(authDTO.getEmail()));
				
		}catch (Exception e){
			throw new Exception("insert email or password");
		}
	}
}
