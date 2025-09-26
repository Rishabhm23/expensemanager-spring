package com.rishabh.expensemanager.dto;

import java.time.LocalDateTime;



public class ProfileDTO {
	
	public ProfileDTO() {
		super();
	}
	public ProfileDTO(Long id, String fullname, String email, String password, String profileImageUrl,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.profileImageUrl = profileImageUrl;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	private Long id;
	private String fullname;
	private String email;
	private String password;
	private String profileImageUrl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "ProfileDTO [id=" + id + ", fullname=" + fullname + ", email=" + email + ", password=" + password
				+ ", profileImageUrl=" + profileImageUrl + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}


}
