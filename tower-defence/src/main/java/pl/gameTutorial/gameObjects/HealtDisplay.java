package pl.gameTutorial.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import pl.gameTutorial.main.Game;


public class HealtDisplay {

	public static final double MAX_HEALTH = 100;
	public static double CURRENT_HELTH = MAX_HEALTH;

	public void tick() {
		CURRENT_HELTH = Game.clapm(CURRENT_HELTH, 0, 2*MAX_HEALTH);
	}

	private final int WIDTH = 50;
	private final int X = 15;
	private final int Y = 15;
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(X, Y, (int)(2 * MAX_HEALTH), WIDTH);
		g.setColor(Color.GREEN);
		g.fillRect(X, Y, (int)(2 * CURRENT_HELTH), WIDTH);
		g.setColor(Color.WHITE);
		g.drawRect(X, Y, (int)(2 * MAX_HEALTH), WIDTH);
	}
}
