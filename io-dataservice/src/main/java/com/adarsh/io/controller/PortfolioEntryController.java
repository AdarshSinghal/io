package com.adarsh.io.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adarsh.io.model.UpdatePortfolioEntryRequest;
import com.adarsh.io.model.dto.PortfolioEntry;
import com.adarsh.io.model.dto.PortfolioEntryKey;
import com.adarsh.io.service.PortfolioEntryService;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioEntryController {

	@Autowired
	private PortfolioEntryService portfolioEnrtyService;

	@GetMapping("/{pid}")
	public Iterable<PortfolioEntry> getPortfolio(@PathVariable Long pid) {
		return portfolioEnrtyService.getPortfolioEntry(pid);
	}

	@GetMapping("/{pid}/{code}")
	public PortfolioEntry getPortfolio(@PathVariable Long pid, @PathVariable String code) {
		return portfolioEnrtyService.getPortfolioEntry(new PortfolioEntryKey(pid, code));
	}

	@PutMapping
	public PortfolioEntry updatePortfolioEntry(@Valid @RequestBody UpdatePortfolioEntryRequest portfolio) {
		return portfolioEnrtyService.update(portfolio);

	}

}
