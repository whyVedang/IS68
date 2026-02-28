import java.util.Arrays;

public class OptimalResourceAllocation {

    public int minCostAssignment(int[][] costMatrix) {
        int n = costMatrix.length;
        if (n == 0) return 0;

        int limit = 1 << n;
        int[] dp = new int[limit];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        for (int mask = 0; mask < limit; mask++) {
            int worker = Integer.bitCount(mask);

            if (worker == n) continue;

            for (int task = 0; task < n; task++) {
                if ((mask & (1 << task)) == 0) {
                    int nextMask = mask | (1 << task);
                    dp[nextMask] = Math.min(dp[nextMask], dp[mask] + costMatrix[worker][task]);
                }
            }
        }

        return dp[limit - 1];
    }
}