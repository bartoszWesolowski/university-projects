package queue;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Bartosz Wesolowski on 20.03.2017.
 */
public class PriorityMinQueueTest {

    @Test
    public void testAllMethodsWithOne() {
        final PriorityMinQueue model = new PriorityMinQueue();
        Assert.assertTrue(model.isEmpty());
        model.insert(new Pair(0, 10));
        Assert.assertFalse(model.isEmpty());
        Assert.assertEquals(10, model.min().getPriority());
        Assert.assertEquals(10, model.extractMin().getPriority());
        Assert.assertTrue(model.isEmpty());
    }

    @Test
    public void testAllMethodsWithArray() {
        final List<Integer> list = Arrays.asList(1, 10, 100, 30, 30, 2, 7, 15);
        final PriorityMinQueue model = new PriorityMinQueue();
        list.stream().forEach(e -> model.insert(new Pair(0, e)));
        Assert.assertFalse(model.isEmpty());
        Assert.assertEquals(1, model.min().getPriority());
        Assert.assertEquals(1, model.extractMin().getPriority());
        Assert.assertEquals(2, model.extractMin().getPriority());
        Assert.assertEquals(7, model.extractMin().getPriority());

    }
}