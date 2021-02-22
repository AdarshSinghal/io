package com.adarsh.io.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adarsh.io.dao.PortfolioRepository;
import com.adarsh.io.model.UpdatePortfolioEntryRequest;
import com.adarsh.io.model.dto.PortfolioEntry;
import com.adarsh.io.model.dto.PortfolioEntryKey;
import com.adarsh.io.model.dto.Stock;
import com.adarsh.io.utils.ErrorTemplate;

@Service
public class PortfolioEntryService {

	@Autowired
	private PortfolioRepository portfolioRepository;
	@Autowired
	StockService stockService;
	@Autowired
	PlayerService playerService;

	@Autowired
	ErrorTemplate error;

	public PortfolioEntry save(PortfolioEntry portfolio) {
		if (portfolio.getUnits() == 0)
			throw new IllegalArgumentException("New Entry must have units > 0");
		return portfolioRepository.save(portfolio);
	}

	public Iterable<PortfolioEntry> getPortfolioEntry(Long pid) {
		return portfolioRepository.findByPid(pid);
	}

	public PortfolioEntry getPortfolioEntry(PortfolioEntryKey portfolioKey) {
		return portfolioRepository.findById(portfolioKey).orElseThrow(error.notExistSupplier("Portfolio Entry"));
	}

	public PortfolioEntry update(UpdatePortfolioEntryRequest request) {

		PortfolioEntryKey key = new PortfolioEntryKey(request.getPid(), request.getCode());
		// Check whether Portfolio entry exists
		PortfolioEntry entry = portfolioRepository.findById(key).orElseGet(() -> {
			playerService.findById(request.getPid());
			Iterable<Stock> stocks = stockService.findByCodeIn(Arrays.asList(request.getCode()));
			if (!stocks.iterator().hasNext()) {
				throw error.notExist(request.getCode());
			}
			return this.save(new PortfolioEntry(request.getPid(), request.getCode(), request.getUnits()));
		});

		if (request.getUnits() == 0) {
			portfolioRepository.deleteById(key);
			return entry;
		}

		entry.setUnits(request.getUnits());

		return portfolioRepository.save(entry);
	}

}
