package com.rishabh.expensemanager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class ExpenseDTO {
	
	private Long id;
	private String name;
	private String icon;
	private String categoryName;
	private Long categoryId;
	private LocalDate date;
	private BigDecimal amount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	public ExpenseDTO(Long id, String name, String icon, String categoryName, Long categoryId, LocalDate date,
			BigDecimal amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.categoryName = categoryName;
		this.categoryId = categoryId;
		this.date = date;
		this.amount = amount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public ExpenseDTO() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Override
	public String toString() {
		return "ExpenseDTO [id=" + id + ", name=" + name + ", icon=" + icon + ", categoryName=" + categoryName
				+ ", categoryId=" + categoryId + ", date=" + date + ", amount=" + amount + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

}
