package com.rishabh.expensemanager.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_incomes")
public class IncomeEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String icon;
	private LocalDate date;
	private BigDecimal amount;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private CategoryEntity category;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", nullable = false)
	private ProfileEntity profile;
	
	
	public IncomeEntity(Long id, String name, String icon, LocalDate date, BigDecimal amount, LocalDateTime createdAt,
			LocalDateTime updatedAt, CategoryEntity category, ProfileEntity profile) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.date = date;
		this.amount = amount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.category = category;
		this.profile = profile;
	}


	public IncomeEntity() {
		super();
	}

	@PrePersist
	public void prePersist() {
		if(this.date == null) {
			this.date = LocalDate.now();
		}
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


	public CategoryEntity getCategory() {
		return category;
	}


	public void setCategory(CategoryEntity category) {
		this.category = category;
	}


	public ProfileEntity getProfile() {
		return profile;
	}


	public void setProfile(ProfileEntity profile) {
		this.profile = profile;
	}


	@Override
	public String toString() {
		return "IncomeEntity [id=" + id + ", name=" + name + ", icon=" + icon + ", date=" + date + ", amount=" + amount
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", category=" + category + ", profile="
				+ profile + "]";
	}
	
}
