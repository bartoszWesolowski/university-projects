package pl.enigmatic.edu.squares.squares.gamestrategy;

import pl.enigmatic.edu.squares.squares.DraggableSquare;
import pl.enigmatic.edu.squares.squares.SelfMovingSquare;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DefaultGameStrategy implements GameStrategy {

    private Component parentComponent;

    public DefaultGameStrategy(Component component) {
        this.parentComponent = component;
    }

    @Override
    public List<SelfMovingSquare> getEnemies() {
        List<SelfMovingSquare> enemies = new LinkedList<>();
        {
            SelfMovingSquare e = new SelfMovingSquare();
            e.setSize(60, 50);
            e.setSpeed(10, 12);
            enemies.add(e);
        }
        {
            SelfMovingSquare e = new SelfMovingSquare();
            e.setSize(50, 30);
            e.setLocation(getWidth() - e.getWidth(), (getHeight() - e.getHeight()) / 2);
            e.setSpeed(5, 7);
            enemies.add(e);
        }
        {
            SelfMovingSquare e = new SelfMovingSquare();
            e.setSize(50, 80);
            e.setLocation(0, (getHeight() - e.getHeight()) / 2);
            e.setSpeed(7, 5);
            enemies.add(e);
        }
        return enemies;
    }

    @Override
    public DraggableSquare getPlayer() {
        DraggableSquare player = new DraggableSquare();
        player.setSize(50, 50);
        player.setBackground(Color.RED);
        player.setLocation((getWidth() - player.getWidth()) / 2, (getHeight() - player.getHeight()) / 2);
        return player;
    }

    private int getHeight() {
        return parentComponent.getHeight();
    }

    private int getWidth() {
        return parentComponent.getWidth();
    }

}
