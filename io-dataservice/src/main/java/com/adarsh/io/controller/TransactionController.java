package com.adarsh.io.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adarsh.io.model.BuySellRequest;
import com.adarsh.io.model.PlayerResponse;
import com.adarsh.io.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PutMapping("/buy")
	public PlayerResponse buy(@Valid @RequestBody BuySellRequest request) {
		return transactionService.buy(request);
	}

	@PutMapping("/sell")
	public PlayerResponse sell(@Valid @RequestBody BuySellRequest request) {
		return transactionService.sell(request);
	}

}
