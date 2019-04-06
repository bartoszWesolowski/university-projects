package pl.enigmatic.edu.squares.statistics;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;


public class Statistics {

	private List<PlayerStatistic> playerStatistics;

	public Statistics() {
		this(new ArrayList<>());
	}

	public Statistics(final List<PlayerStatistic> playerStatistics) {
		this.playerStatistics = playerStatistics;
	}

	public List<PlayerStatistic> getPlayerStatistics() {
		return ImmutableList.copyOf(playerStatistics);
	}
}
