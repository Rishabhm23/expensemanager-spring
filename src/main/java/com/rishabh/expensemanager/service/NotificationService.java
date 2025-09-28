package com.rishabh.expensemanager.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rishabh.expensemanager.dto.ExpenseDTO;
import com.rishabh.expensemanager.entity.ProfileEntity;
import com.rishabh.expensemanager.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationService {
	
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ExpenseService expenseService;
	private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
	
	
	private String frontendurl = "fghjkl";
	
	@Scheduled(cron = "0 0 22 * * *", zone = "IST")
	public void sendDailyIncomeExpenseReminder() {
		
		log.info("Job started: sendDailyIncomeExpenseReminder()");
		List<ProfileEntity> profiles = profileRepository.findAll();
		for(ProfileEntity profile : profiles) {
			String body = "Hi " + profile.getFullname();
			emailService.sendEmail(profile.getEmail(), "Daily Remainder", body);
			
		}
		log.info("Job completed: sendDailyIncomeExpenseReminder()");
		
	}
	
	@Scheduled(cron = "0 0 22 * * *", zone = "IST")
	public void sendDailyExpenseSummary() {
		
		log.info("Job started: sendDailyExpenseSummary()");
		List<ProfileEntity> profiles = profileRepository.findAll();
		for(ProfileEntity profile : profiles) {
			
			List<ExpenseDTO> todaysExpenses = expenseService.getExpensesForUserOnDate(profile.getId(), LocalDate.now(ZoneId.of("Asia/Kolkata")));
			String body = "Hi " + profile.getFullname();
			if(!todaysExpenses.isEmpty()) {
				StringBuilder table = new StringBuilder();
				table.append("hi");
				table.append("gi");
				int i= 1;
				for(ExpenseDTO expense : todaysExpenses) {
					
					table.append("hi").append(expense.getName());
					
				}
			}
			
			emailService.sendEmail(profile.getEmail(), "Daily Summary", body);
			
		}
		
	}
	
	

}
