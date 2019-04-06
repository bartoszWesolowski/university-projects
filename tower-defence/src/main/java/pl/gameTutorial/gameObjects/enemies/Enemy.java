package pl.gameTutorial.gameObjects.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import pl.gameTutorial.gameObjects.GameObject;
import pl.gameTutorial.gameObjects.ID;
import pl.gameTutorial.gameObjects.path.Line;
import pl.gameTutorial.gameObjects.path.Path;
import pl.gameTutorial.main.Game;
import pl.gameTutorial.main.GamePlay;
import pl.gameTutorial.main.Handler;


@SuppressWarnings("serial")
public class Enemy extends GameObject {

	private int width = 15, height = 15;
	private static final int healthBarHeight = 5;
	private static final int RENDER_POS = 10;
	private static boolean healthBarDispley = true;
	private int value = 1;
	private int pointsValue = 1;
	private double speed = 1.0;
	private Handler handler;
	
	private Path path = Game.GAME_PATH;
	private Point2D currentPoint;
	private int currentLineInx;
	private Line currentLine;
	private Color color = Color.RED;
	/**
	 * Shows if the enemy is redy to walk. 
	 */
	private boolean isReady = false;
	
	private int health;
	private final int MAX_HEALTH;

	public Enemy(double x, double y, ID id, Handler handler, int health) {
		super(x, y, id);
		this.handler = handler;
		this.health = health;
		MAX_HEALTH = health;
		this.renderPosition = RENDER_POS;

	}
/**
 * Creates enemy with the parameters of other enemy. It doesn't copy path or handler in a deep way.
 * @param other
 */
	public Enemy(Enemy other) {
		super(other.getX(), other.getY(), other.getId());
		this.handler = other.getHandler();
		this.health = other.getHealth();
		this.MAX_HEALTH = health;
		
		this.currentPoint = path.getStartPoint();
		this.currentLineInx = 0;
		this.currentLine = path.get(currentLineInx);
		
		this.width = other.getWidth();
		this.health = other.getHealth();
		this.renderPosition = RENDER_POS;
		this.color = other.getColor();
		this.value = other.getValue();
		this.speed = other.getSpeed();
		this.pointsValue = other.getPointsValue();
	}
	@Override
	public void tick() {
		walk();
		health = (int) Game.clapm(health, 0, MAX_HEALTH);

	}


	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int) x - width /2 , (int) y - width / 2, width, height);
		if (healthBarDispley) {
			g.setColor(Color.GRAY);
			g.fillRect((int) x - width /2, (int) y - healthBarHeight - 2 - (width / 2), width, healthBarHeight);

			g.setColor(Color.GREEN);
			g.fillRect((int) x - width / 2, (int) y - healthBarHeight - 2 - (width / 2), (width * health) / MAX_HEALTH, healthBarHeight);
		

			g.setColor(Color.WHITE);
			g.drawRect((int) x - width /2, (int) y - healthBarHeight - 2 - (width / 2), width, healthBarHeight);
		}

	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void changeHealtBarDesplay() {
		healthBarDispley = !healthBarDispley;
	}
	public boolean isAlive() {
		return health > 0;
	}

	public boolean isDead() {
		return !isAlive();
	}

	/**
	 * Symuluje akcję w któej obiekt został trafiony
	 * @param power - jak mocno został trafiony
	 */
	public void gotHit(int power) {
		health -= power;
	}

	public Point2D getCurrentPoint() {
		return new Point2D.Double(x, y);
	}


	
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	public int getHealth() {
		return health;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
		this.currentPoint = path.getStartPoint();
		this.currentLineInx = 0;
		this.currentLine = path.get(currentLineInx);
		if(path != null) {
			isReady = true;
		}
	}

	/**
	 * 
	 * @return true if enemy is ready to walk
	 */
	public boolean isRedy() {
		return isReady;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	
	
	
	public static boolean isHealthBarDispley() {
		return healthBarDispley;
	}

	
	public static void setHealthBarDispley(boolean healthBarDispley) {
		Enemy.healthBarDispley = healthBarDispley;
	}

	public int getWidth() {
		return width;
	}

	
	public void setWidth(int width) {
		this.width = width;
	}

	
	public int getHeight() {
		return height;
	}

	
	public void setHeight(int height) {
		this.height = height;
	}

	
	public Handler getHandler() {
		return handler;
	}

	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	
	
	public int getPointsValue() {
		return pointsValue;
	}
	
	public void setPointsValue(int pointsValue) {
		this.pointsValue = pointsValue;
	}
	public int getMAX_HEALTH() {
		return MAX_HEALTH;
	}

	/**
	 * @return Point in the middle of the enemy rectangle
	 */
	public Point2D.Double getMiddlePoint() {
		return new Point2D.Double(x + width / 2, y + health / 2);
	}

	public void walk() {

		if (isDead()) {
			//Game.infoArea.append("Enemy killed! Extra " + value + "$\n");
			GamePlay.money += value;
			GamePlay.points += pointsValue;
			handler.remove(this);
			return;
		}

		Point2D vec = currentLine.getDirection();
		if (currentPoint.distance(currentLine.getStart()) < currentLine.getLenght()) {
			currentPoint = new Point2D.Double(currentPoint.getX() + speed * vec.getX(), currentPoint.getY() + speed * vec.getY());
		} else {
			if (currentLineInx >= path.size() - 1) {
				currentLineInx = 0;
				currentPoint = path.getStartPoint();
				currentLine = path.get(currentLineInx);
				// System.out.println("Enemy doszedł do końca ściezki");
				// return;
			} else {
				currentLineInx++;
				currentLine = path.get(currentLineInx);
				currentPoint = currentLine.getStart();
			}
		}

		x = currentPoint.getX();
		y = currentPoint.getY();

	}
	
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

}
