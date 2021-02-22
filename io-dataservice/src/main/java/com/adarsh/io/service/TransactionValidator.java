package com.adarsh.io.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adarsh.io.dao.PlayerRepository;
import com.adarsh.io.dao.StockRepository;
import com.adarsh.io.model.BuySellRequest;
import com.adarsh.io.model.dto.Player;
import com.adarsh.io.model.dto.Stock;
import com.adarsh.io.utils.ErrorTemplate;

@Service
public class TransactionValidator {

	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	PlayerService playerService;
	@Autowired
	StockRepository stockRepository;
	@Autowired
	private ErrorTemplate error;

	void validateBuyRequest(BuySellRequest request, Player player, Stock stock) {
		if (request.getUnits() > stock.getAvailableUnits()) {
			String message = String.format("Not enough units available. You can try ordering units less than %d",
					stock.getAvailableUnits());
			throw error.illegalState(message);
		}

		int transactionAmt = request.getUnits() * stock.getCmp();
		if (transactionAmt > player.getFunds()) {
			String message = String.format(
					"Insufficient funds.\nTransaction amount(%d unitsx%d cmp =%d) is higher than available funds(%d).",
					request.getUnits(), stock.getCmp(), transactionAmt, player.getFunds());
			throw error.illegalState(message);
		}
	}

	Stock validateAndGetStock(BuySellRequest request) {
		String invalidStockMessage = String.format("Stock (code:%s)", request.getCode());
		Stock stock = stockRepository.findById(request.getCode())
				.orElseThrow(error.notExistSupplier(invalidStockMessage));

		if (stock.getAvailableUnits() == 0) {
			throw error.illegalState("Stock unavailable");
		}

		return stock;
	}

	Player validateAndGetPlayer(BuySellRequest request) {
		String invalidPlayerMessage = String.format("Player (pid:%d)", request.getPid());
		Player player = playerRepository.findById(request.getPid())
				.orElseThrow(error.notExistSupplier(invalidPlayerMessage));

		if (player.getFunds() == 0) {
			throw error.illegalState("You don't have funds.");
		}
		return player;
	}

}
