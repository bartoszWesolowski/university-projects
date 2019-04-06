package pl.gameTutorial.userInput;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import pl.gameTutorial.gameObjects.enemies.Enemy;
import pl.gameTutorial.main.Handler;


public class KeyInput extends KeyAdapter {

	@SuppressWarnings("unused")
	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
		if (key == KeyEvent.VK_SPACE) {
			Enemy.changeHealtBarDesplay();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
