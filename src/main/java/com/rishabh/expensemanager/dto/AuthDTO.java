package com.rishabh.expensemanager.dto;

public class AuthDTO {
	
	
	private String email;
	private String password;
	private String token;
	public AuthDTO(String email, String password, String token) {
		super();
		this.email = email;
		this.password = password;
		this.token = token;
	}
	public AuthDTO() {
		super();
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "AuthDTO [email=" + email + ", password=" + password + ", token=" + token + "]";
	}
	

}
