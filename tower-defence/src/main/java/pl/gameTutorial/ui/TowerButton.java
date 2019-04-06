package pl.gameTutorial.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;

import pl.gameTutorial.gameObjects.ID;
import pl.gameTutorial.gameObjects.towes.Tower;


@SuppressWarnings("serial")
public class TowerButton extends JButton {

	
	private Tower tower = new Tower();


	public TowerButton() { 
		super();
	}

	public int getTowersWidth() {
		return tower.getWidth();
	}

	public void setTowersWidth(int towersWidth) {
		this.tower.setWidth(towersWidth);
	}
	public void setTowersSize(Dimension size) {
		setTowersWidth(size.width);
		setTowersHeight(size.height);
	}
	public int getTowersHeight() {
		return tower.getHeight();
	}

	public void setTowersHeight(int towersHeight) {
		this.tower.setHeight(towersHeight);
	}

	public int getRadius() {
		return tower.getRadius();
	}

	public void setRadius(int radius) {
		tower.setRadius(radius);
	}

	public double getPower() {
		return tower.getPower();
	}

	public void setPower(double power) {
		tower.setPower(power);
	}

	public void setDelay(double delay) {
		tower.setDelay(delay);
	}

	public pl.gameTutorial.main.Handler getHandler() {
		return tower.getHandler();
	}

	public void setPrice(int price) {
		tower.setPrice(price);
	}

	public void setColor(Color color) {
		tower.setColor(color);
	}

	
	public Tower getTower() {
		return tower;
	}

	
	public void setTower(Tower tower) {
		this.tower = new Tower(tower);
		setBackground(tower.getColor());
	}



}
