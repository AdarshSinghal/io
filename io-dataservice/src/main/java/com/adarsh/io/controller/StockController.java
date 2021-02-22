package com.adarsh.io.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adarsh.io.model.AddStockRequest;
import com.adarsh.io.model.dto.Stock;
import com.adarsh.io.service.StockService;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

	@Autowired
	private StockService stockService;

	@GetMapping
	public Iterable<Stock> findByCodeIn(@RequestParam(required = false) List<String> codes) {
		if (codes == null)
			return stockService.findAll();
		return stockService.findByCodeIn(codes);
	}

	@PostMapping
	public Stock save(@RequestBody AddStockRequest request) {
		return stockService.save(request);
	}

}
