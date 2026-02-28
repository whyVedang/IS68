import java.util.*;

public class StreamingMaxAnalytics {

    public int[] maxSlidingWindow(int[] latencyStream, int k) {
        if (latencyStream == null || latencyStream.length == 0) return new int[0];

        int n = latencyStream.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() && latencyStream[deque.peekLast()] < latencyStream[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            if (i >= k - 1) {
                result[i - k + 1] = latencyStream[deque.peekFirst()];
            }
        }

        return result;
    }
}