package pl.enigmatic.edu.squares.util;

import java.awt.*;

public class FramePositionUtil {

    public static void positionFrame(Component c) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        c.setSize((int) (dimension.getWidth() / 2), (int) (dimension.getHeight() / 2));
        int x = (int) ((dimension.getWidth() - c.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - c.getHeight()) / 2);
        c.setLocation(x, y);
    }
}
