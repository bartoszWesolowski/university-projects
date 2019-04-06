package subsequence;

import java.util.Arrays;
import java.util.List;

/**
 * Longest Increasing Subsequence
 *
 * Input  : arr[] = {3, 10, 15, 2, 1, 20}
 * Output : Length of LIS = 3
 * The longest increasing subsequence is 3, 10, 15

 * Input  : arr[] = {3, 2}
 * Output : Length of LIS = 1
 * The longest increasing subsequence is {3}
 */
public class Main {

    public static void main(String[] args) {
        IncreasingSubsequenceCalculator increasingSubsequenceCalculator =
                new IncreasingSubsequenceCalculator();
        List<Integer> input = Arrays.asList(3, 1, 10, 15, 2, 1, 20);
        List<Integer> res = increasingSubsequenceCalculator.findLongestSubsequence(input);
        System.out.println(Arrays.toString(res.toArray()));
    }
}
