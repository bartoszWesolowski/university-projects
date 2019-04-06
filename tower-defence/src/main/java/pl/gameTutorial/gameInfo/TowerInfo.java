package pl.gameTutorial.gameInfo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class TowerInfo {
	private Rectangle towerInfo;
	private int power;
	private int price;
	private double delay;
	
	
	public TowerInfo(Rectangle towerInfo) {
		super();
		this.towerInfo = towerInfo;
	}

	public Rectangle getTowerInfo() {
		return towerInfo;
	}
	
	public void setTowerInfo(Rectangle towerInfo) {
		this.towerInfo = towerInfo;
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	
	public double getDelay() {
		return delay;
	}

	
	public void setDelay(double delay) {
		this.delay = delay;
	}

	
	public int getPrice() {
		return price;
	}

	
	public void setPrice(int price) {
		this.price = price;
	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fill(towerInfo);
		
		g2.setColor(Color.BLACK);
		g2.drawString("Power: " + power, towerInfo.x + 15, towerInfo.y + 15);
		g2.drawString("Shoots every: " + delay + " second" , towerInfo.x + 15, towerInfo.y + 35);
		g2.drawString("Price: " +  price , towerInfo.x + 15, towerInfo.y + 55);
		
	}
	
}
