package com.adarsh.io.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class BuySellRequest {

	@Min(1)
	private long pid;
	@NotBlank
	private String code;
	@Min(1)
	private int units;

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

}
