package com.rishabh.expensemanager.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RecentTransactionDTO {
	
	private Long id;
	private Long profileId;
	private String icon;
	private String name;
	private BigDecimal amount;
	private LocalDate date;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String type;
	public RecentTransactionDTO(Long id, Long profileId, String icon, String name, BigDecimal amount, LocalDate date,
			LocalDateTime createdAt, LocalDateTime updatedAt, String type) {
		super();
		this.id = id;
		this.profileId = profileId;
		this.icon = icon;
		this.name = name;
		this.amount = amount;
		this.date = date;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.type = type;
	}
	public RecentTransactionDTO() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProfileId() {
		return profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "RecentTransactionDTO [id=" + id + ", profileId=" + profileId + ", icon=" + icon + ", name=" + name
				+ ", amount=" + amount + ", date=" + date + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", type=" + type + "]";
	}
	

}
