package com.rishabh.expensemanager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rishabh.expensemanager.dto.ExpenseDTO;
import com.rishabh.expensemanager.entity.CategoryEntity;
import com.rishabh.expensemanager.entity.ExpenseEntity;
import com.rishabh.expensemanager.entity.ProfileEntity;
import com.rishabh.expensemanager.repository.CategoryRepository;
import com.rishabh.expensemanager.repository.ExpenseRepository;

@Service
public class ExpenseService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private ProfileService profileService;
	
	
	public ExpenseDTO addExpense(ExpenseDTO dto) {
		ProfileEntity profile = profileService.getCurrentProfile();
		CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
									.orElseThrow( () -> new RuntimeException("Category not found"));
		
		ExpenseEntity newExpense = toEntity(dto, profile, category);
		expenseRepository.save(newExpense);
		return toDTO(newExpense);
		
	}
	
	public List<ExpenseDTO> getcurrentMonthExpenseForCurrentUser(){
		ProfileEntity profile = profileService.getCurrentProfile();
		
		LocalDate now = LocalDate.now();
		LocalDate startdate = now.withDayOfMonth(1);
		LocalDate enddate = now.withDayOfMonth(now.lengthOfMonth());
		
		List<ExpenseEntity> expenseList = expenseRepository.findByProfileIdAndDateBetween(profile.getId(), startdate, enddate);
		return expenseList.stream().map(this::toDTO).toList();
		
	}
	
	
	public void deleteExpense(Long expenseId){
		ProfileEntity profile = profileService.getCurrentProfile();
		
		ExpenseEntity entity = expenseRepository.findById(expenseId)
											.orElseThrow(() ->  new RuntimeException("Expense Not found"));
		if(!entity.getProfile().getId().equals(profile.getId())) {
			throw new RuntimeException("Unathorized to delete this expense");
		}
		
		expenseRepository.delete(entity);
		
	}
	
	public List<ExpenseDTO> getLatest5ExpensesForCurrentUser() {
		
		ProfileEntity profile = profileService.getCurrentProfile();
		List<ExpenseEntity> expenseList = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
		return expenseList.stream().map(this::toDTO).toList();
		
	}
	
	public BigDecimal getTotalExpenseForCurrentUser() {
		
		ProfileEntity profile = profileService.getCurrentProfile();
		BigDecimal total = expenseRepository.findTotalExpenseByProfileId(profile.getId());
		return total != null ? total:BigDecimal.ZERO;
	}
	
	public List<ExpenseDTO> filterExpenses(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) {
	
		
		ProfileEntity profile = profileService.getCurrentProfile();
		List<ExpenseEntity> expenseList = expenseRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(), startDate, endDate, keyword, sort);
		return expenseList.stream().map(this::toDTO).toList();
		
	}
	
	
	public List<ExpenseDTO> getExpensesForUserOnDate(Long profileId, LocalDate date){
		
		
		List<ExpenseEntity> expenseList = expenseRepository.findByProfileIdAndDate(profileId, date);
		return expenseList.stream().map(this::toDTO).toList();
		
	}
	
	
	private ExpenseEntity toEntity(ExpenseDTO dto, ProfileEntity profile, CategoryEntity category) {
		
		ExpenseEntity newEntity = new ExpenseEntity();
		newEntity.setName(dto.getName());
		newEntity.setIcon(dto.getIcon());
		newEntity.setAmount(dto.getAmount());
		newEntity.setDate(dto.getDate());
		newEntity.setProfile(profile);
		newEntity.setCategory(category);
		
		return newEntity;
	
	}
	
	private ExpenseDTO toDTO(ExpenseEntity entity) {
		
		ExpenseDTO newDTO = new ExpenseDTO();
		
		newDTO.setId(entity.getId());
		newDTO.setName(entity.getName());
		newDTO.setIcon(entity.getIcon());
		newDTO.setAmount(entity.getAmount());
		newDTO.setDate(entity.getDate());
		newDTO.setCategoryId(entity.getCategory() != null ? entity.getCategory().getId() : null);
		newDTO.setCategoryName(entity.getCategory() != null ? entity.getCategory().getName() : null);
		newDTO.setCreatedAt(entity.getCreatedAt());
		newDTO.setUpdatedAt(entity.getUpdatedAt());
		
		return newDTO;
	
	}

}
