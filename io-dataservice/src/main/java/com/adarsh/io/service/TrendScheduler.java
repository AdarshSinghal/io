package com.adarsh.io.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.adarsh.io.dao.TrendRepository;
import com.adarsh.io.model.dto.Trend;

@Service
public class TrendScheduler {

	private static final long DELAY = 3000L;
	private static final long DELETE_DELAY = 30000L;

	private AtomicInteger latestTrend = new AtomicInteger(0);

	private static final int TREND_DURATION_FACTOR = 100;

	private static final double TREND_SET_DELTA = 0.05d;
	private static final double BASE_UP_TREND = 0.66d;
	private static final double BASE_DOWN_TREND = 0.33d;
	private static final double DELTA_LIMIT = 0.10d;

	double downTrendFactor = BASE_DOWN_TREND;
	double upTrendFactor = BASE_UP_TREND;
	int counter = 0;
	int sum;

	@Autowired
	private TrendRepository trendRepository;

	@Scheduled(fixedDelay = DELAY)
	public void trendSetter() {

		trendRepository.findFirstByOrderByTimestampDesc().ifPresent(lt -> {
			latestTrend.set(lt.getTrend());
		});
		Trend trend = new Trend();
		int newTrend = getNewTrend(latestTrend.get());

		trend.setTrend(newTrend);
		trendRepository.save(trend);
	}

	public int getNewTrend(int oldTrend) {
		if (++counter == TREND_DURATION_FACTOR) {
			counter = 1;
			sum = 0;
		}
		int trendShifter = getTrendShifter();

		// System.out.print("Old:" + oldTrend + ", Shift:" + trendShifter + ",
		// downTrendFactor:" + downTrendFactor
		// + ", upTrendFactor:" + upTrendFactor + ", ");

		int newTrend = oldTrend + trendShifter;
		sum += newTrend;
		double avg = (double) sum / (double) counter;

		newTrend = validateTrendValue(newTrend);

		if (avg > 0) {
			preferDownTrend();
		} else {
			preferUpTrend();
		}

		// System.out.println("New: " + newTrend + ", Avg:" + avg);
		return newTrend;
	}

	private int validateTrendValue(int newTrend) {
		if (newTrend < -2) {
			newTrend = -2;
		} else if (newTrend > 2) {
			newTrend = 2;
		}
		return newTrend;
	}

	private int getTrendShifter() {
		int trendShifter = 0;
		double trendShifterRandom = Math.random();

		if (trendShifterRandom <= downTrendFactor)
			trendShifter--;
		else {

			if (trendShifterRandom > upTrendFactor)
				trendShifter++;
		}
		return trendShifter;
	}

	private void preferDownTrend() {
		if (downTrendFactor < BASE_DOWN_TREND + DELTA_LIMIT)
			downTrendFactor *= 1 + TREND_SET_DELTA;
		if (upTrendFactor < BASE_UP_TREND + DELTA_LIMIT)
			upTrendFactor *= 1 + TREND_SET_DELTA;
	}

	private void preferUpTrend() {
		if (downTrendFactor > BASE_DOWN_TREND - DELTA_LIMIT)
			downTrendFactor *= 1 - TREND_SET_DELTA;
		if (upTrendFactor > BASE_UP_TREND - DELTA_LIMIT)
			upTrendFactor *= 1 - TREND_SET_DELTA;
	}

	@Scheduled(fixedDelay = DELETE_DELAY)
	public void deleteOldRecords() {
		trendRepository.deleteOldRecords(1);
	}

	public int getLatestTrend() {
		return latestTrend.get();
	}

}
