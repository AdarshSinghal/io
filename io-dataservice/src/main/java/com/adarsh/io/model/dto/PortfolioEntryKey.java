package com.adarsh.io.model.dto;

import java.io.Serializable;

public class PortfolioEntryKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4830002878580849038L;
	private Long pid;
	private String code;

	public PortfolioEntryKey() {
	}

	public PortfolioEntryKey(Long pid, String code) {
		this.pid = pid;
		this.code = code;
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

}
