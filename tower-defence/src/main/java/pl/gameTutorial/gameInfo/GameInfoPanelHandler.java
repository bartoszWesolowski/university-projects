package pl.gameTutorial.gameInfo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.MouseInputListener;

import pl.gameTutorial.gameObjects.GameObject;
import pl.gameTutorial.gameObjects.ID;
import pl.gameTutorial.gameObjects.towes.Tower;
import pl.gameTutorial.main.Game;
import pl.gameTutorial.main.GamePlay;
import pl.gameTutorial.main.Handler;
import pl.gameTutorial.ui.GameInfoPanel;
import pl.gameTutorial.ui.TowerButton;


/**
 * Klasa do obsługi Panelu z informacjami o grze.
 * @author Bartek
 * 
 */
public class GameInfoPanelHandler implements MouseInputListener {

	private GameInfoPanel gameInfoPanel;
	private GamePlay gamePlay;
	private Handler handler;
	private int x0 = 0, y0 = 0;
	public ArrayList<TowerButton> towersButtons = new ArrayList<TowerButton>();
	Map<TowerButton, AddTower> towers = new HashMap<TowerButton, AddTower>();

	public GameInfoPanelHandler(final GameInfoPanel gameInfoPanel, GamePlay gamePlay, Handler handler) {
		this.gameInfoPanel = gameInfoPanel;
		this.gamePlay = gamePlay;
		this.handler = handler;
		initButtons();

	}

	private void nexLevelStarted() {
		Game.infoArea.append("Level " + gamePlay.getLevel() + " started\n");
	}

	private void newGame() {
		gamePlay.newGame();
	}

	public void showTowerInfo(Tower tower) {
		gameInfoPanel.towerPowerLabel.setText(Double.toString(tower.getPower()));
		gameInfoPanel.towerSpeedLabel.setText(Double.toString(tower.getDelay()));
		gameInfoPanel.towerPriceLabel.setText(Integer.toString(tower.getPrice()));
	}

	public void clearTowerInfo() {
		gameInfoPanel.towerPowerLabel.setText("0");
		gameInfoPanel.towerSpeedLabel.setText("0");
		gameInfoPanel.towerPriceLabel.setText("0");
	}

	public void tick() {
		gameInfoPanel.moneyLabel.setText(Integer.toString(GamePlay.money));
		gameInfoPanel.pointsLabel.setText(Integer.toString(GamePlay.points));
		gameInfoPanel.nextLevelBtt.setText("Start level: "+ Integer.toString(gamePlay.getLevel()));
		if (Game.isCurrentLevelStarted) {
			gameInfoPanel.nextLevelBtt.setBackground(Color.RED);
		} else {
			gameInfoPanel.nextLevelBtt.setBackground(new Color(80, 182, 98));			
		}
	}

	public void render(Graphics g) {
		for (TowerButton t : towers.keySet()) {
			// && !gameInfo.getBounds().contains(new Point(x0, y0))
			if (towers.get(t).addTower()) {
				towers.get(t).getTower().setX(x0 - towers.get(t).getTower().getWidth() / 2);
				towers.get(t).getTower().setY(y0 - towers.get(t).getTower().getHeight() / 2);
				towers.get(t).getTower().updateMiddle();
				towers.get(t).getTower().showRange();
				towers.get(t).getTower().render(g);
			}
		}
	}

	public void setTowerToAdd(TowerButton towerBtt) {
		for (TowerButton t : towers.keySet()) {
			towers.get(t).setAddTower(false);
		}

		towers.get(towerBtt).setAddTower(true);
		x0 = 2000;
		y0 = 2000;
	}

	private boolean clickedOnAnyTower(Handler handler, Point e) {
		for (int i = 0; i < handler.size(); i++) {
			GameObject tempObj = handler.get(i);
			if (tempObj.getId() == ID.Tower) {
				Tower t = (Tower) tempObj;
				if (t.getBounds().contains(e)) { return true; }
			}
		}

		return false;
	}

	private boolean intersectAnyTower(Rectangle boundns) {
		for (int i = 0; i < handler.size(); i++) {
			GameObject tempObj = handler.get(i);
			if (tempObj.getId() == ID.Tower) {
				Tower t = (Tower) tempObj;
				if (t.getBounds().intersects(boundns)) {
					return true;
				}
			}
		}
		return false;
	}

	public void mouseClicked(MouseEvent e) {

		for (int i = 0; i < handler.size(); i++) {
			GameObject tempObj = handler.get(i);
			if (tempObj.getId() == ID.Tower) {
				Tower t = (Tower) tempObj;
				if (t.getBounds().contains(e.getPoint())) {
					t.showRange();
					showTowerInfo(t);
				} else {
					t.hideRange();
				}
			}
		}

		if (!clickedOnAnyTower(handler, e.getPoint())) {
			clearTowerInfo();
		}

		// ---------------dotąd zostaje ----------------

		for (TowerButton towerBtt : towers.keySet()) {

			if (towers.get(towerBtt).addTower() && e.getButton() == MouseEvent.BUTTON1) {
				towers.get(towerBtt).setAddTower(false);
				Tower t = new Tower(towers.get(towerBtt).getTower());
				t.setHandler(handler);
				t.setX(e.getX() - t.getWidth() / 2);
				t.setY(e.getY() - t.getHeight() / 2);
				if (!Game.GAME_PATH.intersects(t.getBounds()) && ! intersectAnyTower(t.getBounds())) {
					if (GamePlay.money >= t.getPrice()) {
						GamePlay.money -= t.getPrice();
						handler.add(new Tower(t));
					} else {
						Game.infoArea.append("YOU DON'T HAVE MONEY TO BUY THAT TOWER!\n");
					}
				} else {
					Game.infoArea.append("YOU CAN NOT SET TOWER THERE!\n");
				}
			}

		}
		// kliknięcie lewym anuluje wszystko
		if (e.getButton() != MouseEvent.BUTTON1) {
			for (TowerButton t : towers.keySet()) {
				towers.get(t).setAddTower(false);
			}
		}
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// System.out.println(e.getPoint());

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		x0 = e.getX();
		y0 = e.getY();
	}

	private void initButtons() {

		// ---------------Tower Button 1 -----------------------
		gameInfoPanel.tower1Button.setText("");
		gameInfoPanel.tower1Button.setTower(Tower.TOWER1);
		gameInfoPanel.tower1Button.setOpaque(true);
		gameInfoPanel.tower1Button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showTowerInfo(gameInfoPanel.tower1Button.getTower());
				setTowerToAdd(gameInfoPanel.tower1Button);

			}
		});

		towersButtons.add(gameInfoPanel.tower1Button);

		// ---------------Tower Button 2 -----------------------
		gameInfoPanel.tower2Button.setText("");
		gameInfoPanel.tower2Button.setTower(Tower.TOWER2);
		gameInfoPanel.tower2Button.setOpaque(true);
		
		gameInfoPanel.tower2Button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showTowerInfo(gameInfoPanel.tower2Button.getTower());
				setTowerToAdd(gameInfoPanel.tower2Button);
			}
		});

		towersButtons.add(gameInfoPanel.tower2Button);

		// ---------------Tower Button 3 -----------------------
		gameInfoPanel.tower3Button.setText("");
		gameInfoPanel.tower3Button.setTower(Tower.TOWER3);
		gameInfoPanel.tower3Button.setOpaque(true);
		
		gameInfoPanel.tower3Button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showTowerInfo(gameInfoPanel.tower3Button.getTower());
				setTowerToAdd(gameInfoPanel.tower3Button);
			}
		});

		towersButtons.add(gameInfoPanel.tower3Button);
		
		// ---------------Tower Button 4-----------------------
		gameInfoPanel.tower4Button.setText("");
		gameInfoPanel.tower4Button.setTower(Tower.TOWER4);
		gameInfoPanel.tower4Button.setOpaque(true);
		
		gameInfoPanel.tower4Button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showTowerInfo(gameInfoPanel.tower4Button.getTower());
				setTowerToAdd(gameInfoPanel.tower4Button);
			}
		});

		towersButtons.add(gameInfoPanel.tower4Button);		
		
		// ---------------Tower Button 5-----------------------
		gameInfoPanel.tower5Button.setText("");
		gameInfoPanel.tower5Button.setTower(Tower.TOWER5);
		gameInfoPanel.tower5Button.setOpaque(true);
		
		gameInfoPanel.tower5Button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showTowerInfo(gameInfoPanel.tower5Button.getTower());
				setTowerToAdd(gameInfoPanel.tower5Button);
			}
		});

		towersButtons.add(gameInfoPanel.tower5Button);
		
		
		// ---------------Tower Button 6-----------------------
		gameInfoPanel.tower6Button.setText("");
		gameInfoPanel.tower6Button.setTower(Tower.TOWER6);
		gameInfoPanel.tower6Button.setOpaque(true);
		
		gameInfoPanel.tower6Button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showTowerInfo(gameInfoPanel.tower6Button.getTower());
				setTowerToAdd(gameInfoPanel.tower6Button);
			}
		});

		towersButtons.add(gameInfoPanel.tower6Button);
		
		// ---------------Next Level Button -----------------------
		gameInfoPanel.nextLevelBtt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!Game.isCurrentLevelStarted) {
					Game.isCurrentLevelStarted = true;
					Game.currentLevel.start();
					nexLevelStarted();
				}

			}
		});

		// --------------- RESTART GAME BUTTON -----------------------
		gameInfoPanel.restartGameButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});;

		for (TowerButton t : towersButtons) {
			Tower tower = t.getTower();
			tower.setHandler(handler);
			towers.put(t, new AddTower(t, tower));
		}
	}
}
