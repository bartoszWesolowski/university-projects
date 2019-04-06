package pl.gameTutorial.gameObjects.towes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.JEditorPane;
import javax.swing.Timer;

import pl.gameTutorial.gameObjects.GameObject;
import pl.gameTutorial.gameObjects.ID;
import pl.gameTutorial.gameObjects.enemies.Enemy;
import pl.gameTutorial.main.Handler;


public class Tower extends GameObject implements ActionListener {

	public static final Tower TOWER1 = new Tower(-500, -500, ID.Tower, 16, 16, 100, 7, 0.5, 7, Color.BLUE); 
	public static final Tower TOWER2 = new Tower(-500, -500, ID.Tower, 16, 16, 125, 45, 1, 15, Color.GREEN); 
	public static final Tower TOWER3 = new Tower(-500, -500, ID.Tower, 16, 16, 150, 150, 1.5, 35, Color.BLACK);
	public static final Tower TOWER4 = new Tower(-500, -500, ID.Tower, 16, 16, 175, 200, 0.5, 80, Color.YELLOW); 
	public static final Tower TOWER5 = new Tower(-500, -500, ID.Tower, 16, 16, 250, 500, 0.5, 250, Color.ORANGE); 
	public static final Tower TOWER6 = new Tower(-500, -500, ID.Tower, 16, 16, 300, 2500, 1.0, 400, Color.RED); 
	
	private static final int RENDER_POS = 20;
	private int radius = 50;
	private double middleX, middleY;
	private double power = 50;
	private Color color = Color.BLUE;
	private int price = 10;
	
	/**
	 * Boolean describing wheather to show the tower range or not
	 */
	private boolean isSelected = false;
	/*
	 * delay beetween shoots
	 */
	private double delay = 2;
	private Handler handler;
	private Timer timer;
	public Tower() {
		super(-500, -500, ID.Tower);
	}
	public Tower(double x, double y, ID id, Handler handler) {
		super(x, y, id);

		this.width = 15;
		this.height = 15;
		this.handler = handler;
		timer = new Timer((int)(delay * 1000), this);
		this.renderPosition = RENDER_POS;

	}

	/**
	 * Handler przekazywany prze referencję !!!
	 * @param other
	 */
	public Tower(Tower other) {
		super(other.getX(), other.getY(), other.getId());
		this.width = other.getWidth();
		this.height = other.getHeight();
		this.middleX = x + (width / 2);
		this.middleY = y + (height / 2);
		this.radius = other.getRadius();
		this.power = other.getPower();
		this.delay = other.getDelay();
		this.handler = other.getHandler();
		this.price = other.getPrice();
		timer = new Timer((int)(delay * 1000), this);
		this.color = other.getColor();
		this.renderPosition = RENDER_POS;
		
	}
	public Tower(double x, double y, ID id, int width, int height, int radius, double power, double delay, Handler handler) {
		super(x, y, id);
		this.width = width;
		this.height = height;
		this.middleX = x + (width / 2);
		this.middleY = y + (height / 2);
		this.radius = radius;
		this.power = power;
		this.delay = delay;
		this.handler = handler;
		timer = new Timer((int)(delay * 1000), this);
		this.renderPosition = RENDER_POS;
	}

	public Tower(double x, double y, ID id, int width, int height, int radius, double power, double delay, int price, Color color) {
		this(x, y, id, width, height, radius, power, delay, null);
		this.price = price;
		this.color = color;
	}
	@Override
	public void tick() {
		shoot();

	}

	/**
	 * Causes that the range of selected tower is drawn.
	 */
	public void showRange() {
		isSelected = true;
	}

	/**
	 * Causes that the range of selected tower is not drawn.
	 */
	public void hideRange() {
		isSelected = false;
	}

	@Override
	public void render(Graphics g) {
		if (isSelected) {
			g.setColor(new Color(185, 175, 235, 120));
			g.fillOval((int) middleX - (radius), (int) middleY - (radius), 2 * radius, 2 * radius);
		}
		g.setColor(color);
		g.fillRect((int) x, (int) y, width, height);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void updateMiddle() {
		this.middleX = x + (width / 2);
		this.middleY = y + (height / 2);
	}
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	
	public Color getColor() {
		return color;
	}

	
	public void setColor(Color color) {
		this.color = color;
	}

	
	public double getDelay() {
		return delay;
	}


	
	public void setDelay(double delay) {
		this.delay = delay;
	}


	
	public Handler getHandler() {
		return handler;
	}


	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}


	
	public int getPrice() {
		return price;
	}

	
	public void setPrice(int price) {
		this.price = price;
	}

	private void shoot() {
		if (!timer.isRunning()) {
			timer.start();
		}
	}
	
	public void stopShooting() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
	}
	
	public String toString() {
		return "x: " + x + " y: " + y + " range: " + radius;
	}
private long lastShoot = System.currentTimeMillis();
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < handler.size(); i++) {
			GameObject gameObj = handler.get(i);
			if (gameObj.getId() == ID.Enemy) {
				Enemy en = (Enemy) gameObj;
				Point2D.Double pos = new Point2D.Double(middleX, middleY);
				long currentTime = System.currentTimeMillis();
				long delta = currentTime - lastShoot;
				if (pos.distance(en.getCurrentPoint()) <= radius && en.isAlive() && delta >= delay * 1000) {
					//System.out.println("Wróg namierzony, pocisk wystrzelony\n\n");
					//System.out.println("last shoot: " + (System.currentTimeMillis() - lastShoot) / 1000);
					lastShoot = System.currentTimeMillis();
					Bullet bullet = new Bullet(x, y, ID.Bullet, handler, en, (int) power);
					bullet.setColor(color);
					handler.add(bullet);
					timer.setDelay(timer.getInitialDelay());
					return;
				} else {
					timer.setDelay(100);
				}
			}
		}

	}
}
