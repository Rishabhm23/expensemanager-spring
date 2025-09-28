package com.rishabh.expensemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishabh.expensemanager.dto.CategoryDTO;
import com.rishabh.expensemanager.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@PostMapping
	public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO){
		CategoryDTO savedCategory = categoryService.saveCategory(categoryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	}
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getCategories(){
		List<CategoryDTO> categories = categoryService.getCategoriesForCurrentUser();
		return ResponseEntity.ok(categories);
				
		
	}
	
	@GetMapping("/{type}")
	public ResponseEntity<List<CategoryDTO>> getCategoriesByType(@PathVariable String type){
		List<CategoryDTO> categories = categoryService.getCategoriesByTypeForCurrentUser(type);
		return ResponseEntity.ok(categories);
				
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO){
		CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedCategory);
	}
	
	

}
