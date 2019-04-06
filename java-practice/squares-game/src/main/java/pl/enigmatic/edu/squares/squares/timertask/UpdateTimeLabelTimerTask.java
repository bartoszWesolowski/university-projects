package pl.enigmatic.edu.squares.squares.timertask;

import org.apache.commons.lang3.time.StopWatch;
import pl.enigmatic.edu.squares.squares.components.TimeLabel;

import java.util.TimerTask;

public class UpdateTimeLabelTimerTask extends TimerTask {

    private TimeLabel timeLabel;

    private StopWatch stopWatch;

    public UpdateTimeLabelTimerTask(final TimeLabel timeLabel, final StopWatch stopWatch) {
        this.timeLabel = timeLabel;
        this.stopWatch = stopWatch;
    }

    @Override
    public void run() {
        timeLabel.displayTime(stopWatch.getTime());
    }

    @Override
    public boolean cancel() {
        return super.cancel();
    }
}
