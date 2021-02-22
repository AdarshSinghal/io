package com.adarsh.io.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adarsh.io.constants.Constants;
import com.adarsh.io.dao.PlayerRepository;
import com.adarsh.io.dao.PortfolioRepository;
import com.adarsh.io.dao.StockRepository;
import com.adarsh.io.model.AddPlayerRequest;
import com.adarsh.io.model.PlayerResponse;
import com.adarsh.io.model.UpdatePlayerRequest;
import com.adarsh.io.model.dto.Player;
import com.adarsh.io.model.dto.PortfolioEntry;
import com.adarsh.io.utils.ErrorTemplate;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private PortfolioRepository portfolioRepository;
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private ErrorTemplate error;

	public Player addplayer(AddPlayerRequest player) {
		Player reqeustedPlayer = new Player(player.getUsername());
		reqeustedPlayer.setFunds(Constants.INITIAL_FUNDS);
		return playerRepository.save(reqeustedPlayer);
	}

	public PlayerResponse getByUsername(String username) {
		Optional<Player> playerOption = playerRepository.findByUsername(username);

		if (playerOption.isEmpty())
			return new PlayerResponse();

		return getPlayerResponse(playerOption);
	}

	public PlayerResponse findById(Long pid) {
		Optional<Player> playerOptional = playerRepository.findById(pid);

		if (playerOptional.isEmpty())
			return new PlayerResponse();

		return getPlayerResponse(playerOptional);
	}

	private PlayerResponse getPlayerResponse(Optional<Player> playerOptional) {
		PlayerResponse playerResponse = new PlayerResponse(playerOptional.get());
		int portfolioValue = getPortfolioValue(playerResponse.getPid());
		playerResponse.setPortfolioValue(portfolioValue);
		int networth = playerResponse.getFunds() + portfolioValue;
		playerResponse.setNetworth(networth);
		return playerResponse;
	}

	public int getPortfolioValue(Long pid) {
		List<PortfolioEntry> portfolioEntries = portfolioRepository.findByPid(pid);
		List<String> stocksInPortfolio = portfolioEntries.stream().map(entry -> entry.getCode())
				.collect(Collectors.toList());
		Map<String, Integer> stockPrice = new HashMap<>();
		stockRepository.findByCodeIn(stocksInPortfolio).forEach(s -> {
			stockPrice.put(s.getCode(), s.getCmp());
		});

		AtomicInteger portfolioValue = new AtomicInteger();

		portfolioEntries.forEach(entry -> {
			int cmp = stockPrice.get(entry.getCode());
			int holding = cmp * entry.getUnits();
			portfolioValue.addAndGet(holding);
		});
		return portfolioValue.get();
	}

	public Iterable<Player> getPlayers() {
		return playerRepository.findAll();
	}

	public Player updatePlayer(UpdatePlayerRequest playerRequest) throws Exception {
		Player player = new Player(playerRequest.getPid(), playerRequest.getUsername(), playerRequest.getFunds());

		String format = String.format("Player (pid:%s)", playerRequest.getPid());
		playerRepository.findById(playerRequest.getPid()).orElseThrow(error.notExistSupplier(format));

		return playerRepository.save(player);
	}

}
