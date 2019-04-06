package subsequence;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

public class IncreasingSubsequenceCalculatorTest {

    private final IncreasingSubsequenceCalculator testedModel =
            new IncreasingSubsequenceCalculator();

    @Test
    public void shoundReturnEmptyResultForEmptyInput() {
        List<Integer> result = testedModel.findLongestSubsequence(Collections.emptyList());
        assertThat(result, empty());
    }

    @Test
    public void shouldReturnOneElementListForOneElementInput() {
        List<Integer> input = Arrays.asList(10);
        List<Integer> result = testedModel.findLongestSubsequence(input);
        assertThat(result, is(input));
    }

    @Test
    public void shouldReturnFirstElementIfAllElementsAreTheSame() {
        List<Integer> input = Arrays.asList(10, 10, 10);
        List<Integer> result = testedModel.findLongestSubsequence(input);
        assertThat(result, is(Arrays.asList(10)));
    }

    @Test
    public void shouldFindLargestIncreasingSequenceAtTheEnd() {
        List<Integer> input = Arrays.asList(10, 10, 15, 1, 2, 3, 4, 5);
        List<Integer> result = testedModel.findLongestSubsequence(input);
        assertThat(result, is(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @Test
    public void shouldFindLargestIncreasingSequenceInTheMiddle() {
        List<Integer> input = Arrays.asList(10, 10, 15, 1, 2, 3, 4, 5, 4, 6, 7, 809);
        List<Integer> result = testedModel.findLongestSubsequence(input);
        assertThat(result, is(Arrays.asList(1, 2, 3, 4, 5)));
    }
}