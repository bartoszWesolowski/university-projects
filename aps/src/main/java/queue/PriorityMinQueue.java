package queue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartosz Wesolowski on 12.03.2017.
 */
public class PriorityMinQueue {

    private static final int DEFAULT_SIZE = 1000;

    private final HeapMin heapMin;

    public PriorityMinQueue(final int size) {
        this.heapMin = new HeapMin(size);
    }

    public PriorityMinQueue() {
        this(DEFAULT_SIZE);
    }

    public Pair min() {
        return this.heapMin.min();
    }

    public Pair extractMin() {
        return heapMin.popMin();
    }

    public void insert(Pair e) {
        this.heapMin.insert(e);
    }

    public boolean isEmpty() {
        return this.heapMin.isEmpty();
    }

    public List<Pair> getElements() {
        final List<Pair> result = new ArrayList<>();
        for (final Pair p : heapMin.getElements()) {
            if(p != null) result.add(p);
        }
        return result;
    }

    public void decreaseKey(final Pair pair, int newValue) {
        final int index = this.getElements().indexOf(pair);
        if (index >= 0) {
            //index przesuwamy w prawo o jeden bo w tej liście nie ma pierwszego nulla (elementu o indexie zero w kopcu)
            heapMin.decreaseKey(index + 1, newValue);
        } else {
            System.out.println("Cant decrease key of pair that is not in the queue");
        }
    }
}
