package pl.enigmatic.edu.squares.squares;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.event.AncestorEvent;

import pl.enigmatic.edu.squares.util.MathHelper;

public class DraggableSquare extends MoveableSquare implements
        MouseMotionListener, MouseListener, ComponentListener {

    private static final long serialVersionUID = -8821491537786282810L;
    private int x0 = 0;
    private int y0 = 0;

    public DraggableSquare() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void connect() {
        getParent().addComponentListener(this);
    }

    public void disconnect() {
        getParent().removeComponentListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x0 = e.getX();
        y0 = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        x = MathHelper.clamp(getX() + x - x0, 0, getParent().getWidth() - getWidth());
        y = MathHelper.clamp(getY() + y - y0, 0, getParent().getHeight() - getHeight());
        setLocation(x, y);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int x = MathHelper
                .clamp(getX(), 0, getParent().getWidth() - getWidth());
        int y = MathHelper.clamp(getY(), 0, getParent().getHeight()
                - getHeight());
        setLocation(x, y);
    }


    public void mouseMoved(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) {  }

    public void mouseEntered(MouseEvent e) {  }

    public void mouseExited(MouseEvent e) { }

    public void componentMoved(ComponentEvent e) {  }

    public void componentShown(ComponentEvent e) { }

    public void componentHidden(ComponentEvent e) { }

    public void ancestorAdded(AncestorEvent e) { }

    public void mouseClicked(MouseEvent e) {
    }


}
