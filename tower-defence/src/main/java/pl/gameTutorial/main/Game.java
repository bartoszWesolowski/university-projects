package pl.gameTutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JTextArea;

import pl.gameTutorial.gameInfo.GameInfoPanelHandler;
import pl.gameTutorial.gameObjects.ID;
import pl.gameTutorial.gameObjects.Level;
import pl.gameTutorial.gameObjects.path.Path;
import pl.gameTutorial.gameObjects.towes.Tower;
import pl.gameTutorial.ui.GameInfoPanel;
import pl.gameTutorial.ui.Window;
import pl.gameTutorial.userInput.KeyInput;
import pl.gameTutorial.userInput.MouseInput;


public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7943393090935933789L;

	public static final int WIDTH = 1000, HEIGHT = WIDTH / 16 * 11;
	private final float gameInfoSize = 0.35f;
	private Thread thread;
	private boolean isRunning = false;
	private Handler handler;
	private GamePlay gamePlay;
	private GameInfoPanelHandler gameInfoPanelHandler;

	public static final Path GAME_PATH = Path.examplePath;
	public static Level currentLevel;
	public static boolean isCurrentLevelStarted = false;
	public static boolean isGameOver = false;
	public static JTextArea infoArea;
	public static GameInfoPanel gameInfoPanel;

	public Game() {

		handler = new Handler();
		Window window = new Window(WIDTH, HEIGHT, "Java Game tutorial", this);
		infoArea = window.infoTextArea;
		gameInfoPanel = window.gameInfoPanel;
		
		gamePlay = new GamePlay(this);
		gameInfoPanelHandler = new GameInfoPanelHandler(gameInfoPanel, gamePlay, handler);

		this.addMouseListener(gameInfoPanelHandler);
		this.addMouseMotionListener(gameInfoPanelHandler);
		this.addKeyListener(new KeyInput(handler));
		
		handler.add(GAME_PATH);
		currentLevel = gamePlay.getFirst();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			isRunning = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Game loop - something given in this tutorial without explanation.
	 */
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double ammountOfTicks = 60;
		double ns = Math.pow(10, 9) / ammountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				tick();
				delta--;
			}

			if (isRunning) {
				render();
			}

			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames);
				frames = 0;
			}
		}

		stop();
	}

	private void tick() {
		handler.tick();
		gamePlay.tick();
		gameInfoPanelHandler.tick();
	}

	private void render() {
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if (bufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics graphics = bufferStrategy.getDrawGraphics();

		graphics.setColor(new Color(184, 255, 184));
		graphics.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(graphics);

		gameInfoPanelHandler.render(graphics);

		graphics.dispose();
		bufferStrategy.show();
	}

	public static double clapm(double var, double leftBound, double rightBound) {
		if (var >= rightBound) {
			return rightBound;
		} else if (var <= leftBound) { return leftBound; }
		return var;
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
