package com.adarsh.io.model.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stock {

	@Id
	String code;
	String name;
	Integer cmp;
	Integer availableUnits;

	public Stock() {
	}

	public Stock(String code, String name, Integer cmp, Integer availableUnits) {
		this.code = code;
		this.name = name;
		this.cmp = cmp;
		this.availableUnits = availableUnits;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCmp() {
		return cmp;
	}

	public void setCmp(Integer cmp) {
		this.cmp = cmp;
	}

	public Integer getAvailableUnits() {
		return availableUnits;
	}

	public void setAvailableUnits(Integer availableUnits) {
		this.availableUnits = availableUnits;
	}

}
