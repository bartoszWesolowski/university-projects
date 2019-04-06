package queue;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by bartosz.wesolowski on 05.04.2017.
 */
public class Pair {

    private final int key;

    private int priority;

    public Pair(int key, int value) {
        this.key = key;
        this.priority = value;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int newValue) {
        this.priority = newValue;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null && obj instanceof Pair) {
            final Pair other = (Pair) obj;
            return key == other.getKey() && priority == other.getPriority();
        }
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("key", key)
                .append("prio", priority)
                .toString();
    }

    public int getKey() {
        return key;
    }
}
