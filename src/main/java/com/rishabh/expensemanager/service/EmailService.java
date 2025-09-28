package com.rishabh.expensemanager.service;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
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
	

}
