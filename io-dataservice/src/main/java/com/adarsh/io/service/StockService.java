package com.adarsh.io.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adarsh.io.dao.StockRepository;
import com.adarsh.io.model.AddStockRequest;
import com.adarsh.io.model.dto.Stock;

@Service
public class StockService {

	@Autowired
	private StockRepository repository;

	public Iterable<Stock> findByCodeIn(List<String> codes) {
		return repository.findByCodeIn(codes);
	}

	public Iterable<Stock> findAll() {
		return repository.findAll();
	}

	public Stock save(AddStockRequest request) {
		return repository
				.save(new Stock(request.getCode(), request.getName(), request.getCmp(), request.getAvailableUnits()));
	}

}
