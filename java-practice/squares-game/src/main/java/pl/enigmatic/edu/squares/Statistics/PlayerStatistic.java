package pl.enigmatic.edu.squares.statistics;

import java.io.Serializable;


public class PlayerStatistic implements Serializable, Comparable<PlayerStatistic> {

    private static final long serialVersionUID = -2634225674644048757L;

    private String name;

    private double time;

    public static PlayerStatistic empty() {
        return new PlayerStatistic("EMPTY", 0);
    }

    public PlayerStatistic(String name, double time) {
        super();
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compareTo(PlayerStatistic other) {
        return Double.compare(this.getTime(), other.getTime());
    }
}
