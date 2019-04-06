package pl.enigmatic.edu.squares.util;

import javax.swing.*;

/**
 * Created by Bartosz Wesolowski on 26.12.2017.
 */
public class InputUtil {

    public static String getInputFromInputDialog(String message) {
        return JOptionPane.showInputDialog(null, message);
    }
}
