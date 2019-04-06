package pl.enigmatic.edu.squares.squares;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import pl.enigmatic.edu.squares.squares.components.TimeLabel;
import pl.enigmatic.edu.squares.squares.gamestrategy.DefaultGameStrategy;
import pl.enigmatic.edu.squares.squares.gamestrategy.GameStrategy;
import pl.enigmatic.edu.squares.squares.timertask.SpeedIncreaserTimerTask;
import pl.enigmatic.edu.squares.squares.timertask.UpdateTimeLabelTimerTask;
import pl.enigmatic.edu.squares.statistics.PlayerStatistic;
import pl.enigmatic.edu.squares.statistics.StatisticsManager;
import pl.enigmatic.edu.squares.util.InputUtil;
import pl.enigmatic.edu.squares.util.StopWatchUtil;
import pl.enigmatic.edu.squares.util.TimerUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.List;
import java.util.Timer;


public class GamePanel extends JPanel implements LocationChangedListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private static final String TOP_LIST_SCORE_MESSAGE_TEMPLATE = "YOU MADE IT TO TOP LIST! WRITE YOUR NAME! \n YOUR TIME: %s";

	private GameStrategy gameStrategy = new DefaultGameStrategy(this);

	final StatisticsManager statisticsManager = StatisticsManager.getInstance();

	private boolean started = false;

	private boolean ended = false;

	private final StopWatch stopWatch = new StopWatch();

	private final TimeLabel timeLabel = new TimeLabel();
	
	private Timer timer = new Timer();

	private DraggableSquare player;

	private List<SelfMovingSquare> enemies = Collections.emptyList();

	public GamePanel() {
		super(null);
	}

	public void newGame() {
		clear();
		stopWatch.reset();
		add(timeLabel);
		createEnemies();
		createPlayer();
	}

	private void clear() {
		TimerUtil.cancelQuietly(timer);
		stopWatch.reset();
		started = false;
		ended = false;
		stopEnemies();
		this.removeAll();
		this.revalidate();
		this.repaint();
	}

	@Override
	public void onLocationChanged(MoveableSquare s) {
		if (started && !ended && player.intersects(s)) {
			finishGame();
		}
	}

	private void finishGame() {
		stopGame();
		gatherStats(stopWatch.getTime() / 1000.0);
	}

	public void stopGame() {
		StopWatchUtil.stopQuietly(stopWatch);
		TimerUtil.cancelQuietly(timer);
		started = false;
		ended = true;
		stopEnemies();
	}


	private void createPlayer() {
		player = gameStrategy.getPlayer();
		player.addMouseListener(this);
		add(player);
		player.connect();
	}

	private void startEnemies() {
		enemies.forEach(SelfMovingSquare::start);
	}

	private void stopEnemies() {
		enemies.forEach(SelfMovingSquare::stop);
	}

	private void createEnemies() {
		enemies = gameStrategy.getEnemies();
		for (SelfMovingSquare e : enemies) {
			add(e);
			e.addLocationChangedListener(this);
			e.setBackground(Color.BLACK);
		}
	}

	private void gatherStats(double time) {
		PlayerStatistic last = statisticsManager.getLast();
		if (time > last.getTime()) {
			String message =  String.format(TOP_LIST_SCORE_MESSAGE_TEMPLATE, time);
			String name = InputUtil.getInputFromInputDialog(message);
			PlayerStatistic playerStatistic = new PlayerStatistic(StringUtils.defaultString(name, "Unknown"), time);
			statisticsManager.addStatistics(playerStatistic);
			statisticsManager.saveStatistics();
		} else {
			JOptionPane.showMessageDialog(null, String.format("Your score %s s.", time));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.startNewGame();
	}

	private void startNewGame() {
		if (!started && !ended) {
			startEnemies();
			started = true;
			stopWatch.start();
			timer = new Timer();
			timer.scheduleAtFixedRate(new SpeedIncreaserTimerTask(enemies), 0, 3000);
			timer.scheduleAtFixedRate(new UpdateTimeLabelTimerTask(timeLabel, stopWatch), 0, 200);
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}
}
