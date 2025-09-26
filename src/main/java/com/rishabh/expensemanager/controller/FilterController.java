package com.rishabh.expensemanager.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishabh.expensemanager.dto.ExpenseDTO;
import com.rishabh.expensemanager.dto.FilterDTO;
import com.rishabh.expensemanager.dto.IncomeDTO;
import com.rishabh.expensemanager.service.ExpenseService;
import com.rishabh.expensemanager.service.IncomeService;

@RestController
@RequestMapping("/filter")
public class FilterController {

	
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private ExpenseService expenseService;
	
	@PostMapping
	public ResponseEntity<?> filterTransactions(@RequestBody FilterDTO filter){

		LocalDate startDate = filter.getStartDate() != null ? filter.getStartDate() : LocalDate.now();
		LocalDate endDate = filter.getEndDate() != null ? filter.getEndDate() : LocalDate.now();
		System.out.println(startDate);
		System.out.println(endDate);
		
		String keyword = filter.getKeyword() != null ? filter.getKeyword() : "";
		String sortField = filter.getSortField() != null ? filter.getSortField() : "date";
		Sort.Direction direction = "desc".equalsIgnoreCase(filter.getSortOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC;
		Sort sort = Sort.by(direction, sortField);
		
		System.out.println(keyword);
		System.out.println(sortField);
		System.out.println(direction);
		System.out.println(sort);
		
		
		if("income".equalsIgnoreCase(filter.getType())) {

			
			List<IncomeDTO> incomes = incomeService.filterIncomes(startDate, endDate, keyword, sort);
			System.out.println(incomes);
			
			return ResponseEntity.ok(incomes);
			
		}else if("expense".equalsIgnoreCase(filter.getType())) {

			List<ExpenseDTO> expenses = expenseService.filterExpenses(startDate, endDate, keyword, sort);
			System.out.println(expenses);
			return ResponseEntity.ok(expenses);
			
		}else {
			return ResponseEntity.badRequest().body("Invalid type. Must be income or expense");
		}
		
	}
}
