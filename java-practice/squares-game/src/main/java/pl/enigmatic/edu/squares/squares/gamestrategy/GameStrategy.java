package pl.enigmatic.edu.squares.squares.gamestrategy;

import pl.enigmatic.edu.squares.squares.DraggableSquare;
import pl.enigmatic.edu.squares.squares.SelfMovingSquare;

import java.util.List;

public interface GameStrategy {

    List<SelfMovingSquare> getEnemies();

    DraggableSquare getPlayer();
}
