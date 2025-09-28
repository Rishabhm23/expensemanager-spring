package com.rishabh.expensemanager.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishabh.expensemanager.dto.ExpenseDTO;
import com.rishabh.expensemanager.dto.IncomeDTO;
import com.rishabh.expensemanager.dto.RecentTransactionDTO;
import com.rishabh.expensemanager.entity.ProfileEntity;

@Service
public class DashboardService {
	
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private ExpenseService expenseService;
	@Autowired
	private ProfileService profileService;
	
	
	public Map<String, Object> getDashboardData(){
		
		ProfileEntity profile = profileService.getCurrentProfile();
		Map<String, Object> returnValue = new LinkedHashMap<>();
		List<ExpenseDTO> latestExpenses = expenseService.getLatest5ExpensesForCurrentUser();
		List<IncomeDTO> latestIncomes = incomeService.getLatest5IncomesForCurrentUser();
		
		List<RecentTransactionDTO> recentTransactions = Stream.concat(
		        latestIncomes.stream().map(income -> toRecentTransactionDTO(income, profile, "income")), 
		        latestExpenses.stream().map(expense -> toRecentTransactionDTO(expense, profile, "expense"))
		    )
		    .sorted((a, b) -> {
		        int cmp = b.getDate().compareTo(a.getDate());
		        if (cmp == 0 && a.getCreatedAt() != null && b.getCreatedAt() != null) {
		            return b.getCreatedAt().compareTo(a.getCreatedAt());
		        }
		        return cmp;
		    })
		    .collect(Collectors.toList());
		
		returnValue.put("totalBalance", 
				incomeService.getTotalIncomeForCurrentUser()
				.subtract(expenseService.getTotalExpenseForCurrentUser()));
		returnValue.put("totalIncome", incomeService.getTotalIncomeForCurrentUser());
		returnValue.put("totalExpense", expenseService.getTotalExpenseForCurrentUser());
		returnValue.put("recent5Expenses",latestExpenses);
		returnValue.put("recent5Incomes",latestIncomes);
		returnValue.put("recentTransactions",recentTransactions);
		
		return returnValue;
				
		
	}
	
	
	private RecentTransactionDTO toRecentTransactionDTO(IncomeDTO income, ProfileEntity profile, String type) {
		
		RecentTransactionDTO recentTransactionDTO = new RecentTransactionDTO();
		recentTransactionDTO.setId(income.getId());
		recentTransactionDTO.setProfileId(profile.getId());
		recentTransactionDTO.setName(income.getName());
		recentTransactionDTO.setIcon(income.getIcon());
		recentTransactionDTO.setAmount(income.getAmount());
		recentTransactionDTO.setDate(income.getDate());
		recentTransactionDTO.setCreatedAt(income.getCreatedAt());
		recentTransactionDTO.setUpdatedAt(income.getUpdatedAt());
		recentTransactionDTO.setType(type);
		
		
		return recentTransactionDTO;
	
	}
	
	private RecentTransactionDTO toRecentTransactionDTO(ExpenseDTO expense, ProfileEntity profile, String type) {
	    RecentTransactionDTO recentTransactionDTO = new RecentTransactionDTO();
	    recentTransactionDTO.setId(expense.getId());
	    recentTransactionDTO.setProfileId(profile.getId());
	    recentTransactionDTO.setName(expense.getName());
	    recentTransactionDTO.setIcon(expense.getIcon());
	    recentTransactionDTO.setAmount(expense.getAmount());
	    recentTransactionDTO.setDate(expense.getDate());
	    recentTransactionDTO.setCreatedAt(expense.getCreatedAt());
	    recentTransactionDTO.setUpdatedAt(expense.getUpdatedAt());
	    recentTransactionDTO.setType(type);
	    return recentTransactionDTO;
	}

}
