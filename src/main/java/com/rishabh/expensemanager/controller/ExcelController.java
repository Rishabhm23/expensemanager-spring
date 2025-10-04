package com.rishabh.expensemanager.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishabh.expensemanager.service.ExcelService;
import com.rishabh.expensemanager.service.ExpenseService;
import com.rishabh.expensemanager.service.IncomeService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/excel")
public class ExcelController {
	
	@Autowired
	private ExcelService excelService;
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private ExpenseService expenseService;
	
	
	@GetMapping("/download/income")
	public void downloadIncomeExcel(HttpServletResponse response) throws IOException {
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheethtml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=income.xlsx");
		
		excelService.writeIncomesToExcel(response.getOutputStream(), incomeService.getcurrentMonthIncomeForCurrentUser());
	
		
		
	}
	
	@GetMapping("/download/expense")
	public void downloadExpenseExcel(HttpServletResponse response) throws IOException {
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheethtml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=expense.xlsx");
		
		excelService.writeExpensesToExcel(response.getOutputStream(), expenseService.getcurrentMonthExpenseForCurrentUser());
	
		
		
	}
	
	
	

}
