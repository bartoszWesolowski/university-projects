package pl.gameTutorial.gameInfo;

import pl.gameTutorial.gameObjects.towes.Tower;
import pl.gameTutorial.ui.TowerButton;


public class AddTower {

	private TowerButton towerButton;
	private boolean addTower = false;
	private Tower tower;

	public AddTower(TowerButton towerButton, Tower tower) {
		this.towerButton = towerButton;
		this.tower = tower;
	}

	public TowerButton getTowerButton() {
		return towerButton;
	}

	public void setTowerButton(TowerButton towerButton) {
		this.towerButton = towerButton;
	}

	public boolean addTower() {
		return addTower;
	}

	public void setAddTower(boolean addTower) {
		this.addTower = addTower;
	}

	public Tower getTower() {
		return tower;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
	}

}
