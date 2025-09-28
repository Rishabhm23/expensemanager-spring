package com.rishabh.expensemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishabh.expensemanager.dto.IncomeDTO;
import com.rishabh.expensemanager.service.IncomeService;


@RestController
@RequestMapping("/incomes")
public class IncomeController {
	
	@Autowired
	private IncomeService incomeService;
	
	@PostMapping
	public ResponseEntity<IncomeDTO> addExpense(@RequestBody IncomeDTO dto){
		
		IncomeDTO saved = incomeService.addIncome(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@GetMapping
	public ResponseEntity<List<IncomeDTO>> getExpenses(){
		List<IncomeDTO> income = incomeService.getcurrentMonthIncomeForCurrentUser();
		return ResponseEntity.ok(income);
				
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIncome(@PathVariable Long id){
		
		incomeService.deleteIncome(id);
		return ResponseEntity.noContent().build();
	}

}
