package pl.gameTutorial.gameObjects.path;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import pl.gameTutorial.gameObjects.GameObject;
import pl.gameTutorial.gameObjects.ID;


/**
 * Klasa reprezentująca ścieżkę. Zawiera takie elementy jak łuki, odcinki oraz skrzyżowania.
 * @author Bartek
 * 
 */
public class Path extends GameObject {

	public Path(Point2D.Double starPoint, ID id) {
		super(starPoint.x, starPoint.y, id);
		this.startPoint = starPoint;
		this.renderPosition = 5;
		
	}

	private ArrayList<Line> segments = new ArrayList<Line>();
	private Point2D.Double startPoint;


	public void tick() {
		// TODO Auto-generated method stub
		
	}
	public int size() {
		return segments.size();
	}

	public ArrayList<Line> getSegments() {
		return segments;
	}

	public Line get(int index) {
		return segments.get(index);
	}

	public Iterator<Line> iterator() {
		return segments.iterator();
	}

	public void setSegments(ArrayList<Line> segments) {
		this.segments = segments;
	}

	public Point2D.Double getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point2D.Double startPoint) {
		this.startPoint = startPoint;
	}

	public Line getLastLine() {
		if (segments != null && !segments.isEmpty()) { return segments.get(segments.size() - 1); }
		System.err.println("GetLastLine() method returned null");
		return null;
	}

	public Point2D.Double getLastPoint() {
		if (segments != null && !segments.isEmpty()) { return getLastLine().getEnd(); }

		return null;
	}

	private boolean lineTo(Point2D.Double point) {
		if (segments != null && segments.isEmpty()) {
			segments.add(new Line(startPoint, point));
			return true;
		} else if (segments != null && !segments.isEmpty()) {
			segments.add(new Line(getLastPoint(), point));
			return true;
		}

		System.err.println("Error in method lineTo()");
		return false;
	}

	public boolean lineTo(Directions direction, int len) {
		Point2D.Double start = null;
		Point2D.Double end = null;
		if (segments != null && segments.isEmpty()) {
			start = startPoint;
		} else if (segments != null && !segments.isEmpty()) {
			start = getLastPoint();
		}

		if (start != null) {
			switch (direction) {
				case NORTH:
					end = new Point2D.Double(start.x, start.y - len);
				break;
				case SOUTH:
					end = new Point2D.Double(start.x, start.y + len);
				break;
				case EAST:
					end = new Point2D.Double(start.x + len, start.y);
				break;
				case WEST:
					end = new Point2D.Double(start.x - len, start.y);
				break;
			}

			Line l = new Line(start, end);
			l.setDir(direction);
			segments.add(l);
			return true;
		}
		System.err.println("Error in function LineTo(Directions direction, int len)");
		return false;
	}

	public String toString() {
		String result = "";
		for (Line l : segments) {
			result += l.toString() + "\n";
		}

		return result;
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		for (Line l : segments) {
			l.render(g);
		}

	}

	public static Path examplePath = new Path(new Point2D.Double(100,-10), ID.Path);
	
	public boolean intersects(Rectangle rectangle) {
		for (Line l : segments) {
			if (l.getBounds().intersects(rectangle)) {
				return true;
			}
		}
		
		return false;
	}
	static {
		examplePath.lineTo(Directions.SOUTH, 210);
		examplePath.lineTo(Directions.EAST, 200);
		examplePath.lineTo(Directions.SOUTH, 200);
		examplePath.lineTo(Directions.WEST, 200);
		examplePath.lineTo(Directions.SOUTH, 100);
		examplePath.lineTo(Directions.EAST, 400);
		examplePath.lineTo(Directions.NORTH, 310);
		examplePath.lineTo(Directions.EAST, 50);
		//examplePath.lineTo(Directions.NORTH, 100);
		//examplePath.lineTo(Directions.EAST, 100);
		//examplePath.lineTo(Directions.SOUTH, 300);
	}

	public Rectangle getBounds() {
		return null;
	}
}
