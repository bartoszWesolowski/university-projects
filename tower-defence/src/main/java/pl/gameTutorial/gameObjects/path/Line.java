package pl.gameTutorial.gameObjects.path;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;


public class Line {

	private Point2D.Double start;
	private Point2D.Double end;

	private Point2D.Double direction;
	Directions dir;
	private double lenght;
	private final int RADIUS = 10;
	private final int width = 30;

	public Line(Point2D.Double start, Point2D.Double end) {
		super();
		this.start = start;
		this.end = end;

		lenght = (int) Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
		direction = new Point2D.Double((end.x - start.x) / lenght, (end.y - start.y) / lenght);
	}

	public Line(Double start, Directions dir, int lenght) {
		super();
		this.start = start;
		this.dir = dir;
		switch (dir) {
			case NORTH:
				this.end = new Point2D.Double(start.x, start.y - lenght);
			break;
			case SOUTH:
				this.end = new Point2D.Double(start.x, start.y + lenght);
			break;
			case EAST:
				this.end = new Point2D.Double(start.x + lenght, start.y);
			break;
			case WEST:
				this.end = new Point2D.Double(start.x - lenght, start.y);
			break;
		}

		lenght = (int) Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
		direction = new Point2D.Double((end.x - start.x) / lenght, (end.y - start.y) / lenght);
	}

	public Point2D.Double getStart() {
		return start;
	}

	
	public Directions getDir() {
		return dir;
	}

	
	public void setDir(Directions dir) {
		this.dir = dir;
	}

	public Point getStartInt() {
		return getPoint(start);
	}

	public void setStart(Point2D.Double start) {
		this.start = start;
	}

	public Point2D.Double getEnd() {
		return end;
	}

	public void setEnd(Point2D.Double end) {
		this.end = end;
	}

	public Point2D.Double getDirection() {

		return direction;

	}

	public double getLenght() {
		return lenght;
	}

	public void setLenght(double lenght) {
		this.lenght = lenght;
	}

	public String toString() {
		String result = "(";
		result += start.x + ", " + start.y + ") -> (" + end.x + ", " + end.y + ")";
		result += " vec [" + direction.getX() + ", " + direction.getY() + "]";
		return result;
	}

	public Rectangle getBounds() {
		Point2D.Double tempStart = start;
		if (dir == Directions.WEST || dir == Directions.NORTH) {
			tempStart = end;
		}

		if (dir == Directions.EAST || dir == Directions.WEST) {
			return new Rectangle((int) tempStart.x - (width / 2), (int) tempStart.y - (width / 2), (int) lenght + (width), width);
		} else {
			return new Rectangle((int) tempStart.x - (width / 2), (int) tempStart.y - (width / 2), width, (int) lenght + width);
		}

	};

	public void render(Graphics g) {
/*
		g.drawLine((int) start.x, (int) start.y, (int) end.x, (int) end.y);
		g.setColor(Color.DARK_GRAY);
		// g.drawRect(start.x, start.y, tempWid, tempHe);
		g.fillOval((int) start.x - RADIUS / 2, (int) start.y - RADIUS / 2, RADIUS, RADIUS);
		g.fillOval((int) end.x - RADIUS / 2, (int) end.y - RADIUS / 2, RADIUS, RADIUS);
*/
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GRAY);
		g2d.fill(getBounds());
		
		
	}

	private static Point getPoint(Point2D.Double p2D) {
		return new Point((int) p2D.x, (int) p2D.y);
	}
}
