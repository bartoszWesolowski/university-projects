package pl.enigmatic.edu.squares.util;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopWatchUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(StopWatchUtil.class);

    public static void stopQuietly(StopWatch stopWatch) {
        try {
            stopWatch.stop();
        } catch (Exception e) {
            LOGGER.warn("Cought exception while trying to stop StopWatch", e);
        }
    }
}
