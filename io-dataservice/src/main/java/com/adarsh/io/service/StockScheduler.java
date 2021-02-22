package com.adarsh.io.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.adarsh.io.dao.StockRepository;
import com.adarsh.io.dao.TrendRepository;
import com.adarsh.io.utils.DoubleUtils;
import com.google.common.util.concurrent.AtomicDouble;

@Service
public class StockScheduler {

	@Autowired
	TrendRepository trendRepository;

	private static final long DELAY = 5000L;
	private final long BASE_AVAILABLE_UNITS = 100000000l;

	@Autowired
	private StockRepository repo;

	@Scheduled(fixedDelay = DELAY)
	public void updateStockPrices() {

		AtomicInteger atomicTrend = new AtomicInteger();
		trendRepository.findFirstByOrderByTimestampDesc().ifPresent(lt -> {
			atomicTrend.set(lt.getTrend());
		});

		System.out.println("Trend: " + atomicTrend.get());

		repo.findAll().forEach(stock -> {
			int cmp = stock.getCmp();
			int availableUnits = stock.getAvailableUnits();
			double availableUnitsDelta = (double) (availableUnits + BASE_AVAILABLE_UNITS)
					/ (BASE_AVAILABLE_UNITS * availableUnits);

			double randomFactor = 0.01d + Math.random() / 10000;
			if (availableUnitsDelta/2 > 0.5d) {
				randomFactor += (5d * availableUnitsDelta);
			} else {
				randomFactor -= (5d * availableUnitsDelta);
			}

			AtomicDouble atomicTrendRandom = new AtomicDouble(Math.random() / 3);
			atomicTrendRandom.addAndGet(0.5d * atomicTrend.get());
			atomicTrendRandom.addAndGet(-Math.random() / 3);

			double changeFactor = randomFactor + atomicTrendRandom.get();
			System.out.println("AU Delta:" + DoubleUtils.get(availableUnitsDelta) + ", RF:" + randomFactor);// +",
			// TF:"+atomicTrendRandom.get()

		});

	}

}
