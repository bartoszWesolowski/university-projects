package queue;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Bartosz Wesolowski on 12.03.2017.
 */
public class HeapMin {

    public Pair[] heap;

    private final int length;

    private int heapSize = 0;

    public HeapMin(final int length) {
        this.length = length;
        this.heap = new Pair[length + 1];
    }


    public HeapMin(final int length, final Pair[] elements) {
        this(length);
        if (length < elements.length) {
            throw new IllegalArgumentException("Heap length lower than number of elements to be inserted");
        }
        this.heapSize = elements.length;
        for (int i = 0; i < elements.length; i++) {
            this.heap[i + 1] = elements[i];
        }
        for (int i = Math.floorDiv(this.heapSize, 2); i >= 1; i--) {
            this.minHeapify(i);
        }
    }

    public HeapMin(final Pair[] elements) {
        this(elements.length, elements);
    }


    public HeapMin(final List<Pair> elements) {
        this(elements.size());
        elements.stream().forEach(element -> this.insert(element));
    }


    /**
     * DO PORPAWY!!! Z wykorzystaniem decreaseKey
     * @param e
     */
    public void insert(Pair e) {
        heapSize++;
        final int passedPrio = e.getPriority();
        e.setPriority(Integer.MAX_VALUE);
        heap[heapSize] = e;
        this.decreaseKey(heapSize, passedPrio);
    }

    /**
     * Removes the min element and returns it
     */
    public Pair popMin() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("You can't pop min from empty heap");
        }
        final Pair min = this.heap[1];
        heap[1] = heap[this.heapSize];
        heapSize--;
        this.minHeapify(1);
        return min;
    }


    public Pair min() {
        if (this.isEmpty()) {
            throw new UnsupportedOperationException("You can't get min from empty heap");
        }
        return this.heap[1];
    }

    public int heapSize() {
        return this.heapSize;
    }

    public int lenght() {
        return this.length;
    }

    public int parent(int i) {
        return Math.floorDiv(i, 2);
    }

    public int left(int i) {
        return 2 * i;
    }

    public int right(int i) {
        return 2 * i + 1;
    }

    /**
     * @return index of first leaf
     */
    public int firstLeaf() {
        return (int) Math.floor(this.heapSize / 2.0) + 1;
    }

    /**
     * lewy i prawy to juz kopce
     * zamieniami z rootem
     * @param i
     */
    private void minHeapify(final int i) {
        Integer left = left(i);
        Integer right = right(i);
        Integer smallest; // index of the smallest element
        if (left <= this.heapSize && this.heap[left].getPriority() < this.heap[i].getPriority()) {
            smallest = left;
        } else {
            smallest = i;
        }

        if (right <= this.heapSize() &&  this.heap[right].getPriority() < this.heap[smallest].getPriority()) {
            smallest = right;
        }
        if (smallest != i) {
            Pair smallestValue = this.heap[smallest];
            Pair valueOfRoot = this.heap[i];
            this.heap[i] = smallestValue;
            this.heap[smallest] = valueOfRoot;
            this.minHeapify(smallest);
        }
    }

    /**
     * Changes the value of element with index equal to i to a smaller value k
     * @param i
     * @param k
     */
    public void decreaseKey(int i, int k) {
        if (this.heap[i].getPriority() < k) {
            throw new IllegalArgumentException("cant decrease element with index i= " + i + "to a larger value k = " + k);
        }
        this.heap[i].setPriority(k);
        while (i > 1 && this.heap[parent(i)].getPriority() > this.heap[i].getPriority()) {
            Pair valueWithIndexI = this.heap[i];
            this.heap[i] = this.heap[parent(i)];
            this.heap[parent(i)] = valueWithIndexI;
            i = parent(i);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        for (int i = 1; i <= this.heapSize; i++) {
            sb.append(this.heap[i]);
            if (i != this.heapSize) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    /**
     * @return true if heap is empty
     */
    public boolean isEmpty() {
        return  this.heapSize == 0;
    }

    public Pair[] getElements() {
        return Arrays.copyOf(this.heap, this.heapSize + 1);
    }
}
