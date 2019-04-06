package pl.gameTutorial.gameObjects.towes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import pl.gameTutorial.gameObjects.GameObject;
import pl.gameTutorial.gameObjects.ID;
import pl.gameTutorial.gameObjects.enemies.Enemy;
import pl.gameTutorial.gameObjects.path.Line;
import pl.gameTutorial.main.Handler;


@SuppressWarnings("serial")
public class Bullet extends GameObject  {



	private Enemy enemy;
	private int power;
	private final int RADIUS = 10;
	private Handler handler;
	private Color color = Color.RED;
	private double speed = 5.0;
	public Bullet(double x, double y, ID id) {
		super(x, y, id);
	}
	
	public Bullet(double x, double y, ID id, Handler handler, Enemy enemy, int power) {
		super(x, y, id);
		this.enemy = enemy;
		this.power = power;
		this.handler = handler;
		this.renderPosition = 15;
	}

	public void shoot() {

		Point2D.Double enemyLocation = new Point2D.Double(enemy.getX(), enemy.getY());
		if(enemyLocation.distance(new Point2D.Double(x - RADIUS/2, y - RADIUS/2)) <= RADIUS) {
			//System.out.println("Trafiono wroga z pocisku !!");
			handler.remove(this);
			enemy.gotHit(power);
			return;
		} else if(enemy.isDead()) {
			handler.remove(this);
		} else {
			Line trajectory = new Line(new Point2D.Double(x, y), enemyLocation);
			Point2D.Double dir = trajectory.getDirection();
			velX =  speed * dir.x;
			velY = speed * dir.y;
		}
	
	}
	

	@Override
	public void tick() {
		x += velX;
		y += velY;
		shoot();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval((int)x - RADIUS/2, (int) y - RADIUS/2, RADIUS, RADIUS);
	}
	

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}

	
	public Color getColor() {
		return color;
	}

	
	public void setColor(Color color) {
		this.color = color;
	}
	
	

}
