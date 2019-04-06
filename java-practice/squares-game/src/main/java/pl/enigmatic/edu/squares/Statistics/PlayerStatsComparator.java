package pl.enigmatic.edu.squares.statistics;

import java.util.Comparator;

public class PlayerStatsComparator implements Comparator<PlayerStatistic> {

    @Override
    public int compare(final PlayerStatistic o1, final PlayerStatistic o2) {
        return o2.compareTo(o1);
    }
}
