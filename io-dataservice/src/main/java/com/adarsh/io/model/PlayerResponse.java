package com.adarsh.io.model;

import com.adarsh.io.model.dto.Player;

public class PlayerResponse {

	private Long pid;
	private String username;
	private Integer funds;
	private Integer portfolioValue;
	private Integer networth;

	public PlayerResponse() {
	}

	public PlayerResponse(Long pid, String username, Integer funds) {
		this.pid = pid;
		this.username = username;
		this.funds = funds;
	}

	public PlayerResponse(Player player) {
		this(player.getPid(), player.getUsername(), player.getFunds());
	}

	public PlayerResponse(String username) {
		this.username = username;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getFunds() {
		return funds;
	}

	public void setFunds(Integer funds) {
		this.funds = funds;
	}

	public Integer getPortfolioValue() {
		return portfolioValue;
	}

	public void setPortfolioValue(Integer portfolioValue) {
		this.portfolioValue = portfolioValue;
	}

	public Integer getNetworth() {
		return networth;
	}

	public void setNetworth(Integer networth) {
		this.networth = networth;
	}

}
