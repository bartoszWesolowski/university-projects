package pl.enigmatic.edu.squares.statistics;

import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StatisticsManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsManager.class);

    private static final String STATISTICS_STORAGE_FILE_NAME = "stats.json";

    private static final int NUMBER_OF_SAVED_STATS = 15;

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private MinMaxPriorityQueue<PlayerStatistic> currentStats = MinMaxPriorityQueue
            .orderedBy(new PlayerStatsComparator())
            .maximumSize(NUMBER_OF_SAVED_STATS)
            .create();

    private StatisticsManager() {
        Statistics statistics = readFromFile();
        currentStats.addAll(statistics.getPlayerStatistics());
    }

    private static final StatisticsManager instance = new StatisticsManager();

    public static StatisticsManager getInstance() {
        return instance;
    }

    public Statistics getStatistics() {
        return new Statistics(getStatisticList());
    }

    public void addStatistics(PlayerStatistic playerStatistic) {
        currentStats.add(playerStatistic);
    }

    public void saveStatistics() {
        try (Writer writer = new FileWriter(STATISTICS_STORAGE_FILE_NAME)) {
            Statistics statistics = getStatistics();
            GSON.toJson(statistics, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save file with stats.", e);
        }
    }

    public PlayerStatistic getLast() {
        return currentStats.peekLast();
    }

    private List<PlayerStatistic> getStatisticList() {
        List<PlayerStatistic> playerStatistics = currentStats.stream()
                .collect(Collectors.toList());
        playerStatistics.sort(new PlayerStatsComparator());
        return playerStatistics;
    }

    private Statistics readFromFile() {
        File file = new File(STATISTICS_STORAGE_FILE_NAME);
        Statistics statistics = null;
        try {
            String statJson = Files.toString(file, Charset.forName("UTF-8"));
            statistics = GSON.fromJson(statJson, Statistics.class);
        } catch (IOException e) {
            LOGGER.error("Error occurred while reading file with satirists.", e);
        }
        return Optional.ofNullable(statistics)
                .orElseGet(() -> emptyStats());
    }

    private Statistics emptyStats() {
        List<PlayerStatistic> stats = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_SAVED_STATS; i++) {
            stats.add(PlayerStatistic.empty());
        }
        return new Statistics(stats);
    }
}
