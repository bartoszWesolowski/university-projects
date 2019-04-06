package pl.enigmatic.edu.squares.util;

import java.util.Timer;

public class TimerUtil {

    public static void cancelQuietly(Timer timer) {
        if (timer != null) {
            timer.cancel();
        }
    }
}
