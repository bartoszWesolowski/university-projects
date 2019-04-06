package pl.enigmatic.edu.squares.squares.timertask;

import pl.enigmatic.edu.squares.squares.SelfMovingSquare;

import java.util.List;
import java.util.TimerTask;

public class SpeedIncreaserTimerTask extends TimerTask {

    private static final double DEFAULT_SPEED_RATIO_INCREASED_BY = 1.1;

    final List<SelfMovingSquare> enemies;

    private double increaseSpeedRatioBy;

    public SpeedIncreaserTimerTask(final List<SelfMovingSquare> enemies, final double increaseSpeedRatioBy) {
        this.enemies = enemies;
        this.increaseSpeedRatioBy = increaseSpeedRatioBy;
    }

    public SpeedIncreaserTimerTask(final List<SelfMovingSquare> enemies) {
        this(enemies, DEFAULT_SPEED_RATIO_INCREASED_BY);
    }

    @Override
    public void run() {
        enemies.forEach(enemy -> {
            enemy.setSpeed(enemy.getVx() * increaseSpeedRatioBy, enemy.getVy() * increaseSpeedRatioBy);
        });
    }
}
