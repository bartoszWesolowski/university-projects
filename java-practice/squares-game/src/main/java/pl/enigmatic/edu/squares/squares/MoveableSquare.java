package pl.enigmatic.edu.squares.squares;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

public class MoveableSquare extends JPanel {

	private static final long serialVersionUID = -2996904676334741794L;
	private final List<LocationChangedListener> locationChangedListeners = new LinkedList<LocationChangedListener>();

	public MoveableSquare() {
	}

	public int getRight() {
		return getX() + getWidth();
	}

	public int getBottom() {
		return getY() + getHeight();
	}

	public boolean intersects(MoveableSquare other) {
		return !(getX() > other.getRight() || other.getX() > getRight()
				|| getY() > other.getBottom() || other.getY() > getBottom());
	}

	public boolean addLocationChangedListener(LocationChangedListener e) {
		return locationChangedListeners.add(e);
	}

	public boolean removeLocationChangedListener(Object o) {
		return locationChangedListeners.remove(o);
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		for (LocationChangedListener l : locationChangedListeners) {
			l.onLocationChanged(this);
		}
	}
}
