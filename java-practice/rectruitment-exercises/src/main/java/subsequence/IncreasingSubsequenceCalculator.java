package subsequence;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class IncreasingSubsequenceCalculator {

    /**
     * Finds Longest Increasing Subsequence
     *
     * Input  : arr[] = {3, 10, 15, 2, 1, 20}
     * Output : Length of LIS = 3
     * The longest increasing subsequence is 3, 10, 15

     * Input  : arr[] = {3, 2}
     * Output : Length of LIS = 1
     * The longest increasing subsequence is {3}
     * @param numbers
     * @return
     */
    public List<Integer> findLongestSubsequence(List<Integer> numbers) {
        List<Integer> tempLongest = new LinkedList<>();
        List<Integer> theLongest = new LinkedList<>();
        if (CollectionUtils.isEmpty(numbers) || numbers.size() == 1) {
            return new ArrayList<>(numbers);
        }

        for (int i = 0; i < numbers.size(); i++) {
            int current = numbers.get(i);
            int next = Iterables.get(numbers, i + 1, Integer.MIN_VALUE);
            tempLongest.add(current);
            if (tempLongest.size() > theLongest.size()) {
                theLongest = tempLongest;
            }
            if (next <= current) {
                tempLongest = new LinkedList<>();
            }
        }

        theLongest = tempLongest.size() > theLongest.size() ? tempLongest : theLongest;
        return theLongest;
    }
}
