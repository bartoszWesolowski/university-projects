package pl.gameTutorial.gameObjects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import pl.gameTutorial.gameObjects.enemies.Enemy;
import pl.gameTutorial.gameObjects.path.Path;
import pl.gameTutorial.main.Game;
import pl.gameTutorial.main.Handler;


public class Level implements ActionListener {

	private static int idNumber = 0;
	private Handler handler;
	private Path path;
	private int numberOfEnemies;

	/**
	 * delay bettwen enemies creation.
	 */
	private double delay;

	private final int id;

	private Enemy enemy;

	private int count = 0;
	private Timer timer;

	private boolean wasStarted = false;

	public Level(Handler handler, int numberOfEnemies, Enemy enemy, double delay) {
		super();
		this.handler = handler;
		this.numberOfEnemies = numberOfEnemies;
		this.enemy = enemy;
		this.delay = delay;
		this.id = idNumber;
		idNumber++;

		timer = new Timer((int)(delay * 1000), this);
	}

	public void start() {
		//handler.clear(ID.Tower);
		handler.add(Game.GAME_PATH);
		timer.start();
	}

	public void stop() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
	}

	public void restart() {
		count = 0;
		wasStarted = false;
	}
	public boolean wasStarted() {
		return wasStarted;
	}

	public boolean isOver() {
		return wasStarted && !hasEnemies() && count >= numberOfEnemies;
	}

	public boolean hasEnemies() {
		for (int i = 0; i < handler.size(); i++) {
			if (handler.get(i).getId() == ID.Enemy) { return true; }
		}
		return false;
	}

	public void actionPerformed(ActionEvent e) {

		if (count < numberOfEnemies) {
			handler.add(new Enemy(enemy));// TODO Auto-generated method stub
			count++;
		} else {
			timer.stop();
		}

		wasStarted = true;

	}

	
	public int getNumberOfEnemies() {
		return numberOfEnemies;
	}

	
	public void setNumberOfEnemies(int numberOfEnemies) {
		this.numberOfEnemies = numberOfEnemies;
	}

	
	public Enemy getEnemy() {
		return enemy;
	}

	
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

}
