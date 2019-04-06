package pl.gameTutorial.main;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

import pl.gameTutorial.gameObjects.GameObject;
import pl.gameTutorial.gameObjects.ID;
import pl.gameTutorial.gameObjects.Level;
import pl.gameTutorial.gameObjects.enemies.Enemy;
import pl.gameTutorial.gameObjects.towes.Tower;


public class GamePlay {

	private Game game;
	private int level = 0;
	public static final int START_MONEY = 70;
	private static final int START_ENEMIES_NUMBER = 10;
	private static final int START_ENEMIES_HEALTH = 20;
	private static final int START_ENEMIES_VALUE = 1;
	private static final int INCREASE_ENEMIES_VALUE_PERIOD = 5;
	private static final int START_ENEMIES_POINTS_VALUE = 5;
	private static final int MAX_LEVEL = 150;

	public static int money = START_MONEY;
	public static int points = 0;

	private static LinkedList<Color> colors = new LinkedList<Color>();
	Random random = new Random(System.currentTimeMillis());

	public GamePlay(Game g) {
		this.game = g;
	}

	public void tick() {
		if (!Game.isGameOver) {
			if (Game.currentLevel != null && Game.currentLevel.isOver()) {
				level++;
				if (MAX_LEVEL > level) {
					Game.currentLevel.stop();
					Level l;
					if (level % 10 == 0 && level != 0) {
						l = bossLevel(level);
						Game.infoArea.append("\n\nBOSS LEVEL!!!\n");
					} else if (level % 11 == 0 && level != 0) {
						Game.infoArea.append("\n\nFAST LEVEL!!!\n");
						l = newFastLevel(level);
					} else if (level % (random.nextInt(17) + 1) == 0 && level != 0) {
						l = newBonusLevel(level);
						Game.infoArea.append("\n\nBONUS LEVEL!!!\n");
					} else {
						l = newLevel(level);
					}
					Game.currentLevel = l;
					Game.isCurrentLevelStarted = false;
					Game.infoArea.append(nextLevelMassage(level, l));

				} else {
					Game.isGameOver = true;
					Game.infoArea.append("GAME OVER");
				}
			}
		}
	}

	// można by napisać jedną ogólną funkcję później żeby bylo ładnie !
	private Level newLevel(int level) {
		Enemy enemy = new Enemy(-100, -100, ID.Enemy, game.getHandler(), START_ENEMIES_HEALTH + (level * 125));
		enemy.setValue(START_ENEMIES_VALUE + (int) (level / INCREASE_ENEMIES_VALUE_PERIOD));
		enemy.setColor(colors.get(level % colors.size()));
		enemy.setPointsValue(START_ENEMIES_POINTS_VALUE + level);
		return new Level(game.getHandler(), START_ENEMIES_NUMBER + level, enemy, 1);
	}

	private Level newFastLevel(int level) {
		Enemy enemy = new Enemy(-100, -100, ID.Enemy, game.getHandler(), START_ENEMIES_HEALTH + (level * 50));
		enemy.setValue(START_ENEMIES_VALUE + (int) (level / INCREASE_ENEMIES_VALUE_PERIOD) * 2);
		enemy.setColor(colors.get(level % colors.size()));
		enemy.setPointsValue(START_ENEMIES_POINTS_VALUE + level * 2);
		enemy.setSpeed(2.0);
		return new Level(game.getHandler(), level, enemy, 1);
	}

	private Level bossLevel(int level) {
		Enemy enemy = new Enemy(-100, -100, ID.Enemy, game.getHandler(), level * 1000);
		enemy.setValue(level * 25);
		enemy.setColor(colors.get(level % colors.size()));
		enemy.setPointsValue(level * 25);
		return new Level(game.getHandler(), 1, enemy, 1);
	}

	private Level newBonusLevel(int level) {
		Enemy enemy = new Enemy(-100, -100, ID.Enemy, game.getHandler(), level * 10);
		enemy.setValue(level * 10);
		enemy.setColor(colors.get(level % colors.size()));
		enemy.setPointsValue(level * 5);
		return new Level(game.getHandler(), level + 1, enemy, 1);
	}

	public Level getFirst() {
		Level first = newLevel(0);
		Game.infoArea.append(nextLevelMassage(0, first));
		return first;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void restartGame() {

	}

	public void newGame() {
		
		if (Game.currentLevel != null) {
			Game.currentLevel.stop();
		}
		for (int i = 0; i < game.getHandler().size(); i++) {
			GameObject g = game.getHandler().get(i);
			if (g.getId() == ID.Tower) {
				Tower t = (Tower) g;
				t.stopShooting();
			}
		}

		Game.infoArea.setText("New Game");
		game.getHandler().clear();
		game.getHandler().add(Game.GAME_PATH);
		Game.currentLevel = getFirst();
		Game.isCurrentLevelStarted = false;
		Game.isGameOver = false;
		money = START_MONEY;
		level = 0;
	}

	private String nextLevelMassage(int level, Level l) {
		StringBuilder result = new StringBuilder();
		result.append("Next level: " + level + "\t");
		result.append("Enemies number: " + l.getNumberOfEnemies() + "\t");
		result.append("Heatlh of each enemy: " + l.getEnemy().getHealth() + "\t");
		result.append("Value of each enemy: " + l.getEnemy().getValue() + "\n\n");
		return result.toString();
	}
	static {
		colors.add(Color.RED);
		colors.add(Color.BLACK);
		colors.add(Color.PINK);
		colors.add(Color.CYAN);
		colors.add(Color.WHITE);
		colors.add(new Color(149, 25, 200));
		colors.add(Color.YELLOW);
		colors.add(new Color(120, 140, 160));
	}
}
