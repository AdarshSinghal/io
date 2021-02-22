package com.adarsh.io.service;

import java.util.concurrent.ThreadPoolExecutor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.adarsh.io.dao.PlayerRepository;
import com.adarsh.io.dao.PortfolioRepository;
import com.adarsh.io.dao.StockRepository;
import com.adarsh.io.model.BuySellRequest;
import com.adarsh.io.model.PlayerResponse;
import com.adarsh.io.model.dto.Player;
import com.adarsh.io.model.dto.PortfolioEntry;
import com.adarsh.io.model.dto.PortfolioEntryKey;
import com.adarsh.io.model.dto.Stock;
import com.adarsh.io.utils.ErrorTemplate;

@Service
public class TransactionService {

	@Autowired
	private PortfolioRepository PortfolioRepository;
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	PlayerService playerService;
	@Autowired
	StockRepository stockRepository;
	@Autowired
	ErrorTemplate error;

	@Autowired
	private TransactionValidator transactionValidator;

	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public PlayerResponse buy(@Valid BuySellRequest request) throws IllegalStateException {

		Player player = transactionValidator.validateAndGetPlayer(request);
		Stock stock = transactionValidator.validateAndGetStock(request);
		transactionValidator.validateBuyRequest(request, player, stock);

		PortfolioEntry entity = new PortfolioEntry(request.getPid(), request.getCode(), request.getUnits());
		PortfolioRepository.findById(new PortfolioEntryKey(request.getPid(), request.getCode()))
				.ifPresent(existingPortfolioEntry -> {
					entity.setUnits(existingPortfolioEntry.getUnits() + request.getUnits());
				});

		PortfolioRepository.save(entity);
		stock.setAvailableUnits(stock.getAvailableUnits() - request.getUnits());
		stockRepository.save(stock);

		player.setFunds(player.getFunds() - (stock.getCmp() * request.getUnits()));
		playerRepository.save(player);

		return playerService.findById(request.getPid());
	}

	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public PlayerResponse sell(@Valid BuySellRequest request) throws IllegalStateException {

		Player player = transactionValidator.validateAndGetPlayer(request);
		Stock stock = transactionValidator.validateAndGetStock(request);

		PortfolioEntry entity = new PortfolioEntry(request.getPid(), request.getCode(), request.getUnits());
		PortfolioRepository.findById(new PortfolioEntryKey(request.getPid(), request.getCode()))
				.ifPresentOrElse(existingPortfolioEntry -> {
					if (existingPortfolioEntry.getUnits() >= request.getUnits())
						entity.setUnits(existingPortfolioEntry.getUnits() - request.getUnits());
				}, () -> {
					String message = "Sell units should be less than or equal to units in portfolio.";
					throw error.illegalState(message);
				});

		PortfolioRepository.save(entity);

		stock.setAvailableUnits(stock.getAvailableUnits() + request.getUnits());
		stockRepository.save(stock);

		player.setFunds(player.getFunds() + (stock.getCmp() * request.getUnits()));
		playerRepository.save(player);

		return playerService.findById(request.getPid());
	}

}
