package pl.enigmatic.edu.squares.squares;

import java.util.Timer;
import java.util.TimerTask;

public class SelfMovingSquare extends MoveableSquare {

	private static final long serialVersionUID = 1L;

	private double vx = 1;
	private double vy = 1;
	private final Timer timer = new Timer();
	private final TimerTask task = new TimerTask() {

		@Override
		public void run() {
			double x = getX() + vx;
			double y = getY() + vy;
			if (x < 0) {
				x = 0;
				vx *= -1;
			} else if (x + getWidth() > getParent().getWidth()) {
				x = getParent().getWidth() - getWidth();
				vx *= -1;
			}
			if (y < 0) {
				y = 0;
				vy *= -1;
			} else if (y + getHeight() > getParent().getHeight()) {
				y = getParent().getHeight() - getHeight();
				vy *= -1;
			}
			setLocation((int) x, (int) y);
		}
	};

	public SelfMovingSquare() {
	}

	public void start() {
		timer.scheduleAtFixedRate(task, 0L, 1000 / 24);
	}

	public void stop() {
		timer.cancel();
	}

	public double getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public void setSpeed(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}
}
