package pl.enigmatic.edu.squares.squares.components;

import javax.swing.*;
import java.text.DecimalFormat;

public class TimeLabel extends JLabel {

    private DecimalFormat df = new DecimalFormat("#.##");

    public TimeLabel() {
        super();
        this.setSize(100, 50);
    }

    public void displayTime(long milliseconds) {
        double seconds = milliseconds / 1000.0;
        this.setText(df.format(seconds));
    }
}
