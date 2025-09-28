package com.rishabh.expensemanager.dto;

import java.time.LocalDateTime;

public class CategoryDTO {
	
	
	private Long id;
	private String name;
	private Long profileId;
	private String icon;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String type;
	
	public CategoryDTO() {
		super();
	}
	public CategoryDTO(Long id, String name, Long profileId, String icon, LocalDateTime createdAt,
			LocalDateTime updatedAt, String type) {
		super();
		this.id = id;
		this.name = name;
		this.profileId = profileId;
		this.icon = icon;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.type = type;
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
		return "CategoryDTO [id=" + id + ", name=" + name + ", profileId=" + profileId + ", icon=" + icon
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", type=" + type + "]";
	}

}
