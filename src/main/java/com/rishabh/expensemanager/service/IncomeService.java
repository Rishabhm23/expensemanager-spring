package com.rishabh.expensemanager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rishabh.expensemanager.dto.ExpenseDTO;
import com.rishabh.expensemanager.dto.IncomeDTO;
import com.rishabh.expensemanager.entity.CategoryEntity;
import com.rishabh.expensemanager.entity.ExpenseEntity;
import com.rishabh.expensemanager.entity.IncomeEntity;
import com.rishabh.expensemanager.entity.ProfileEntity;
import com.rishabh.expensemanager.repository.CategoryRepository;
import com.rishabh.expensemanager.repository.IncomeRepository;


@Service
public class IncomeService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private IncomeRepository incomeRepository;
	@Autowired
	private ProfileService profileService;
    private final String CACHE_INCOME = "income";

    @CacheEvict(value = CACHE_INCOME, allEntries = true)
	public IncomeDTO addIncome(IncomeDTO dto) {
		ProfileEntity profile = profileService.getCurrentProfile();
		CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
									.orElseThrow( () -> new RuntimeException("Category not found"));
		
		IncomeEntity newIncome = toEntity(dto, profile, category);
		incomeRepository.save(newIncome);
		return toDTO(newIncome);
		
	}

    @Cacheable(value = CACHE_INCOME, key = "'income_' + #root.target.getCurrentProfileId()")
	public List<IncomeDTO> getcurrentMonthIncomeForCurrentUser(){

        ProfileEntity profile = profileService.getCurrentProfile();
		
		LocalDate now = LocalDate.now();
		LocalDate startDate = now.withDayOfMonth(1);
		LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
		
		List<IncomeEntity> incomeList = incomeRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
		return incomeList.stream().map(this::toDTO).toList();
		
	}

    @CacheEvict(value = CACHE_INCOME, allEntries = true)
	public void deleteIncome(Long incomeId){
		ProfileEntity profile = profileService.getCurrentProfile();
		
		IncomeEntity entity = incomeRepository.findById(incomeId)
											.orElseThrow(() ->  new RuntimeException("Income Not found"));
		if(!entity.getProfile().getId().equals(profile.getId())) {
			throw new RuntimeException("Unathorized to delete this expense");
		}
		
		incomeRepository.delete(entity);
		
	}

	public List<IncomeDTO> getLatest5IncomesForCurrentUser() {
		
		ProfileEntity profile = profileService.getCurrentProfile();
		List<IncomeEntity> incomeList = incomeRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
		return incomeList.stream().map(this::toDTO).toList();
		
	}
	
	public BigDecimal getTotalIncomeForCurrentUser() {
		
		ProfileEntity profile = profileService.getCurrentProfile();
		BigDecimal total = incomeRepository.findTotalIncomeByProfileId(profile.getId());
		return total != null ? total:BigDecimal.ZERO;
	}
	
	public List<IncomeDTO> filterIncomes(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) {
	
		
		ProfileEntity profile = profileService.getCurrentProfile();
		List<IncomeEntity> incomeList = incomeRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(), startDate, endDate, keyword, sort);
		return incomeList.stream().map(this::toDTO).toList();
		
	}

	private IncomeEntity toEntity(IncomeDTO dto, ProfileEntity profile, CategoryEntity category) {
		
		IncomeEntity newEntity = new IncomeEntity();
		newEntity.setName(dto.getName());
		newEntity.setIcon(dto.getIcon());
		newEntity.setAmount(dto.getAmount());
		newEntity.setDate(dto.getDate());
		newEntity.setProfile(profile);
		newEntity.setCategory(category);
		
		return newEntity;
	
	}
	
	private IncomeDTO toDTO(IncomeEntity entity) {
		
		IncomeDTO newDTO = new IncomeDTO();
		
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

    public Long getCurrentProfileId() {
        return profileService.getCurrentProfile().getId();
    }

}
