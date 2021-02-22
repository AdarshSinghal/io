package com.adarsh.io.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AddStockRequest {
	@NotBlank
	String code;
	@NotBlank
	String name;
	@Min(0)
	int cmp;
	int availableUnits;

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

	public int getCmp() {
		return cmp;
	}

	public void setCmp(int cmp) {
		this.cmp = cmp;
	}

	public int getAvailableUnits() {
		return availableUnits;
	}

	public void setAvailableUnits(int availableUnits) {
		this.availableUnits = availableUnits;
	}

}
