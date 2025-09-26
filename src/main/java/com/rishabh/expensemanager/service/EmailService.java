package com.rishabh.expensemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	public void sendEmail(String to, String subject, String text) {
		try {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("rishabhm4343@gmail.com"); // optional
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(text);
	        mailSender.send(message);
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
    }
	

}
