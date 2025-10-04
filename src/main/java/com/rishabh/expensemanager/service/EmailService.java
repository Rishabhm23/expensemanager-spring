package com.rishabh.expensemanager.service;

import java.io.IOException;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import java.util.Base64;

import jakarta.mail.internet.MimeMessage;

import com.sendgrid.*;




@Service
public class EmailService {
	
	//@Autowired
	//private JavaMailSender mailSender;
	
	@Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${spring.mail.from:rishabhm4343@gmail.com}")
    private String defaultFrom;
	
	
	public void sendEmail(String to, String subject, String text) {
		try {
			System.out.println("inside email service");
			sendEmailInternal(to, subject, text, "text/plain");
			/*
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("rishabhm4343@gmail.com"); // optional
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(text);
	        mailSender.send(message);*/
		}catch (Exception e) {
			//throw new RuntimeException(e.getMessage());
			 e.printStackTrace();
			 throw new RuntimeException("Email sending failed: " + e.getMessage(), e);
		}
    }
	
	private void sendEmailInternal(String to, String subject, String body, String type) {
        try {
        	
        	System.out.println("inside internal service");
        	
            Email fromEmail = new Email(defaultFrom.isEmpty() ? "rishabhm4343@gmail.com" : defaultFrom);
            Email toEmail = new Email(to);
            Content content = new Content(type, body);
            
            

            Mail mail = new Mail();
            mail.setFrom(fromEmail);
            mail.setSubject(subject);
            mail.addContent(content);

            Personalization personalization = new Personalization();
            personalization.addTo(toEmail);
            mail.addPersonalization(personalization);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            System.out.println("📨 Email sent! Status Code: " + response.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("inside email error: "+ e);
            throw new RuntimeException("Email sending failed: " + e.getMessage(), e);
        }
    }
	
	public void sendEmailWithAttachments(String to, String subject, String body, byte[] attachment, String filename, String type) {
	    try {
	        System.out.println("📎 Sending email with attachment...");

	        // 1. Create email addresses
	        Email fromEmail = new Email(defaultFrom.isEmpty() ? "rishabhm4343@gmail.com" : defaultFrom);
	        Email toEmail = new Email(to);

	        // 2. Create the main content
	        Content content = new Content(type, body); // type could be "text/plain" or "text/html"

	        // 3. Create the Mail object
	        Mail mail = new Mail();
	        mail.setFrom(fromEmail);
	        mail.setSubject(subject);
	        mail.addContent(content);

	        // 4. Add personalization (to, cc, bcc, etc.)
	        Personalization personalization = new Personalization();
	        personalization.addTo(toEmail);
	        mail.addPersonalization(personalization);

	        // 5. Add attachment
	        if (attachment != null && attachment.length > 0) {
	            Attachments attachments = new Attachments();
	            attachments.setContent(Base64.getEncoder().encodeToString(attachment));
	            attachments.setType("application/octet-stream"); // or "application/pdf", "text/csv", etc.
	            attachments.setFilename(filename);
	            attachments.setDisposition("attachment");
	            mail.addAttachments(attachments);
	        }

	        // 6. Send the email
	        SendGrid sg = new SendGrid(sendGridApiKey);
	        Request request = new Request();
	        request.setMethod(Method.POST);
	        request.setEndpoint("mail/send");
	        request.setBody(mail.build());

	        Response response = sg.api(request);
	        System.out.println("✅ Email with attachment sent! Status Code: " + response.getStatusCode());

	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Failed to send email with attachment: " + e.getMessage(), e);
	    }
	}
	

}
