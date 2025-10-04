package com.rishabh.expensemanager.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishabh.expensemanager.entity.ProfileEntity;
import com.rishabh.expensemanager.service.EmailService;
import com.rishabh.expensemanager.service.ExcelService;
import com.rishabh.expensemanager.service.ExpenseService;
import com.rishabh.expensemanager.service.IncomeService;
import com.rishabh.expensemanager.service.ProfileService;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private ExcelService excelService;
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private EmailService emailService;
	@Autowired
	private ProfileService profileService;
	
	
	
	@GetMapping("/income-excel")
	public ResponseEntity<Void> emailIncomeExcel() throws IOException{
		ProfileEntity profile = profileService.getCurrentProfile();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		excelService.writeIncomesToExcel(baos, incomeService.getcurrentMonthIncomeForCurrentUser());
		
		emailService.sendEmailWithAttachments(profile.getEmail(), "Yor Income Excel Report", "Please find attached your income report", baos.toByteArray(), "income.xlsx", "text/plain");
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/expense-excel")
	public ResponseEntity<Void> emailExpenseExcel() throws IOException{
		ProfileEntity profile = profileService.getCurrentProfile();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		excelService.writeExpensesToExcel(baos, expenseService.getcurrentMonthExpenseForCurrentUser());
		
		emailService.sendEmailWithAttachments(profile.getEmail(), "Yor Expense Excel Report", "Please find attached your expense report", baos.toByteArray(), "expense.xlsx", "text/plain");
		return ResponseEntity.ok(null);
	}

}
