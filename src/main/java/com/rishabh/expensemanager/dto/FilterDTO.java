package com.rishabh.expensemanager.dto;

import java.time.LocalDate;

import org.springframework.data.domain.Sort;

public class FilterDTO {
	private String type;
	private LocalDate startDate;
	private LocalDate endDate;
	private String keyword;
	private Sort sort;
	private String sortField;
	private String sortOrder;
	public FilterDTO(String type, LocalDate startDate, LocalDate endDate, String keyword, Sort sort, String sortField,
			String sortOrder) {
		super();
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
		this.keyword = keyword;
		this.sort = sort;
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}
	public FilterDTO() {
		super();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Sort getSort() {
		return sort;
	}
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	@Override
	public String toString() {
		return "FilterDTO [type=" + type + ", startDate=" + startDate + ", endDate=" + endDate + ", keyword=" + keyword
				+ ", sort=" + sort + ", sortField=" + sortField + ", sortOrder=" + sortOrder + "]";
	}

}
