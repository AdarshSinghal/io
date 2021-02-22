package com.adarsh.io.model.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(PortfolioEntryKey.class)
@Table(name="Portfolio")
public class PortfolioEntry {

	@Id
	private Long pid;
	@Id
	private String code;
	private int units;

	public PortfolioEntry() {
	}

	public PortfolioEntry(Long pid, String code, int units) {
		this.pid = pid;
		this.code = code;
		this.units = units;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
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
