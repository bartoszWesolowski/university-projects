package queue.general;

import queue.Pair;
import org.junit.Assert;
import org.junit.Test;
import queue.HeapMin;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by bartosz.wesolowski on 05.04.2017.
 */
public class HeapMinTest {


    private static final Pair[] EXAMPLE_ELEMENTS = new Pair[] {
            new Pair(1, 10),
            new Pair(2, 15),
            new Pair(3, 3),
            new Pair(4, 17),
            new Pair(5, 35),
            new Pair(6, 20),
            new Pair(7, 1),
            new Pair(8, 100),
            new Pair(9, 2),
            new Pair(10, 4),
            new Pair(11, 40)
    };

    private void checkIfValid(final HeapMin heapMin) {
        final Pair[] elements = heapMin.getElements();
        for (int i = heapMin.heapSize(); i > 1; i--) {
            Pair element = elements[i];
            int parentIndex = heapMin.parent(i);
            Pair parent = elements[parentIndex];
            if (element.getPriority() < parent.getPriority()) {
                System.out.println(String.format("Validation for heap: %s failed. Parent of element with index %d was grater than his child", heapMin.toString(), i));
                Assert.fail();
            }
        }
    }

    private void checkPoppingMinValues(final HeapMin model, int... values) {
        this.checkIfValid(model);
        for (int e : values) {
            Assert.assertEquals(e, model.popMin().getPriority());
            this.checkIfValid(model);
        }
    }

    @Test
    public void constructorWithArray() throws Exception {
        final HeapMin model = new HeapMin(100, EXAMPLE_ELEMENTS);
        checkPoppingMinValues(model,1, 2);
        Assert.assertEquals(EXAMPLE_ELEMENTS.length  - 2, model.heapSize());
        //System.out.println(model);
    }

    @Test
    public void insertionTest() throws Exception {
        final HeapMin model = new HeapMin(100);
        for (Pair e : EXAMPLE_ELEMENTS) {
            model.insert(e);
        }
        checkPoppingMinValues(model, 1, 2);

    }

    @Test
    public void withOneElement() throws Exception {
        final HeapMin model = new HeapMin(100, new Pair[] { new Pair(0, 2)});
        this.checkIfValid(model);
        Assert.assertNotNull(model.toString());
        Assert.assertEquals(2, model.min().getPriority());
        Assert.assertEquals(1, model.heapSize());
        Assert.assertEquals(2, model.popMin().getPriority());
        this.checkIfValid(model);
    }

    @Test
    public void randomizedTestWithoutDuplicateValues() throws Exception {
        final Random random = new Random(System.nanoTime());
        final int[] ints = random.ints(30, 1, 1000).toArray();
        final List<Pair> elements = Arrays.stream(ints).boxed()
                .collect(Collectors.toSet())
                .stream()
                .map(e -> new Pair(0, e))
                .collect(Collectors.toList());
        final int minValue = Arrays.stream(ints).min().getAsInt();
        final HeapMin model = new HeapMin(elements);
        this.checkPoppingMinValues(model, minValue);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void popFromEmpty() {
        new HeapMin(1).popMin();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void minFromEmpty() {
        new HeapMin(1).min();
    }
}