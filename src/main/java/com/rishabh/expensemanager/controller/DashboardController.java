package com.rishabh.expensemanager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishabh.expensemanager.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping
	public ResponseEntity<Map<String, Object>> getDashboardData(){
		Map<String, Object> dashboard = dashboardService.getDashboardData();
		
		return ResponseEntity.ok(dashboard);
	}
	

}
