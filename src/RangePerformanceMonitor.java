public class RangePerformanceMonitor {
    private int[] tree;
    private int n;

    public RangePerformanceMonitor(int[] prices) {
        if (prices.length > 0) {
            n = prices.length;
            tree = new int[4 * n];
            buildTree(prices, 0, 0, n - 1);
        }
    }

    private void buildTree(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
            return;
        }
        int mid = start + (end - start) / 2;
        buildTree(arr, 2 * node + 1, start, mid);
        buildTree(arr, 2 * node + 2, mid + 1, end);
        tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
    }

    public void update(int index, int value) {
        updateTree(0, 0, n - 1, index, value);
    }

    private void updateTree(int node, int start, int end, int index, int value) {
        if (start == end) {
            tree[node] = value;
            return;
        }
        int mid = start + (end - start) / 2;
        if (start <= index && index <= mid) {
            updateTree(2 * node + 1, start, mid, index, value);
        } else {
            updateTree(2 * node + 2, mid + 1, end, index, value);
        }
        tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
    }

    public int queryMax(int L, int R) {
        return queryTree(0, 0, n - 1, L, R);
    }

    private int queryTree(int node, int start, int end, int L, int R) {
        if (R < start || end < L) {
            return Integer.MIN_VALUE;
        }
        if (L <= start && end <= R) {
            return tree[node];
        }
        int mid = start + (end - start) / 2;
        int leftMax = queryTree(2 * node + 1, start, mid, L, R);
        int rightMax = queryTree(2 * node + 2, mid + 1, end, L, R);
        return Math.max(leftMax, rightMax);
    }
}