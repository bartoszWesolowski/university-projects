package pl.gameTutorial.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Player extends GameObject {

	private final int WIDTH = 32;
	private final int HEIGHT = 32;
	
	public Player(double x, double y, ID id) {
		super(x, y, id);
	}

	
	
	@Override
	public void tick() {
		x += velX;
		y += velY;
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
		
	}



	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
	}

	
	
}
