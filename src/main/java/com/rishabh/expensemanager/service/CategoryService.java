package com.rishabh.expensemanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rishabh.expensemanager.dto.CategoryDTO;
import com.rishabh.expensemanager.entity.CategoryEntity;
import com.rishabh.expensemanager.entity.ProfileEntity;
import com.rishabh.expensemanager.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private ProfileService profileService;
	@Autowired
	private CategoryRepository categoryRepository;
	
	public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
		ProfileEntity profile = profileService.getCurrentProfile();
		
		if(categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId())) {
			throw new RuntimeException("Category with this name already exists");
		}
		
		CategoryEntity newCategory = categoryRepository.save(toEntity(categoryDTO, profile));
		
		return toDTO(newCategory);
		
		
	}
	
	public List<CategoryDTO> getCategoriesForCurrentUser(){
		ProfileEntity profile = profileService.getCurrentProfile();
		
		List<CategoryEntity> categories = categoryRepository.findByProfileId(profile.getId());
		return categories.stream().map(this::toDTO).toList();
	}
	
	public List<CategoryDTO> getCategoriesByTypeForCurrentUser(String type){
		ProfileEntity profile = profileService.getCurrentProfile();
		
		List<CategoryEntity> categories = categoryRepository.findByTypeAndProfileId(type, profile.getId());
		return categories.stream().map(this::toDTO).toList();
	}
	
	public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
		
		ProfileEntity profile = profileService.getCurrentProfile();
		
		CategoryEntity existingCategory = categoryRepository.findByIdAndProfileId(categoryId, profile.getId())
												.orElseThrow(() -> new RuntimeException("Category not found or not accessible")); 
				
		existingCategory.setName(categoryDTO.getName());
		existingCategory.setIcon(categoryDTO.getIcon());
		existingCategory.setType(categoryDTO.getType());
		
		existingCategory = categoryRepository.save(existingCategory);
		
		return toDTO(existingCategory);
		
		
	}
	
	
	
	private CategoryEntity toEntity(CategoryDTO categoryDTO, ProfileEntity profileEntity) {
		
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setName(categoryDTO.getName());
		categoryEntity.setIcon(categoryDTO.getIcon());
		categoryEntity.setProfile(profileEntity);
		categoryEntity.setType(categoryDTO.getType());
		
		return categoryEntity;
		
	}
	
	private CategoryDTO toDTO(CategoryEntity entity) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(entity.getId());
		categoryDTO.setName(entity.getName());
		categoryDTO.setIcon(entity.getIcon());
		categoryDTO.setProfileId(entity.getProfile() != null ? entity.getProfile().getId(): null);
		categoryDTO.setType(entity.getType());
		categoryDTO.setCreatedAt(entity.getCreatedAt());
		categoryDTO.setUpdatedAt(entity.getUpdatedAt());
		
		return categoryDTO;
	}

}
