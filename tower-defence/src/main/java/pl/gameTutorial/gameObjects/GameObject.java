package pl.gameTutorial.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *Class that represents all of game objects.
 * @author Bartek
 */
public abstract class GameObject implements Comparable<GameObject> {
	
	protected double x, y;
	protected ID id;
	protected double velX, velY;
	protected int width = 15, height = 15;
	/**
	 * The positon of GameObjet in render queue. The smallest position renders first.
	 */
	protected int renderPosition = 1;
	public GameObject(double x, double y, ID id) {
		super();
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();
	
	public double getX() {
		return x;
	}

	
	public void setX(double x) {
		this.x = x;
	}

	
	public double getY() {
		return y;
	}

	
	public void setY(double y) {
		this.y = y;
	}

	
	public ID getId() {
		return id;
	}

	
	public void setId(ID id) {
		this.id = id;
	}

	
	public double getVelX() {
		return velX;
	}

	
	public void setVelX(double velX) {
		this.velX = velX;
	}

	
	public double getVelY() {
		return velY;
	}

	
	public void setVelY(double velY) {
		this.velY = velY;
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
	
	
	
	public int getRenderPosition() {
		return renderPosition;
	}

	
	public void setRenderPosition(int renderPosition) {
		this.renderPosition = renderPosition;
	}

	public int compareTo(GameObject other) {
		 
		return  (new Integer(renderPosition).compareTo(other.getRenderPosition()));

 
	}	
	
}
