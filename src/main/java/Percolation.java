import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private final boolean[] sites;
    private final WeightedQuickUnionUF topUnionFind;
    private final WeightedQuickUnionUF bottomUnionFind;
    private final int topId;
    private final int bottomId;
    private int openSites = 0;

    public Percolation(int n) {
        this.size = n;
        this.sites = new boolean[n * n];
        this.topUnionFind = new WeightedQuickUnionUF(size * size + 2);
        this.bottomUnionFind = new WeightedQuickUnionUF(size * size + 2);
        this.topId = size * size;
        this.bottomId = size * size + 1;
        for (int i = 1; i <= size; i++) {
            int topRowMemberId = getSiteId(1, i);
            int bottomRowMemberId = getSiteId(size, i);
            topUnionFind.union(topId, topRowMemberId);
            bottomUnionFind.union(topId, topRowMemberId);
            bottomUnionFind.union(bottomId, bottomRowMemberId);
        }
    }

    public void open(int row, int col) {
        verifySite(row, col);
        int siteId = getSiteId(row, col);
        if (!sites[siteId]) {
            sites[siteId] = true;
            forNeighbours(row, col, neighbourId -> {
                if (sites[neighbourId]) {
                    topUnionFind.union(siteId, neighbourId);
                    bottomUnionFind.union(siteId, neighbourId);
                }
            });
            openSites += 1;
        }
    }

    public boolean isOpen(int row, int col)  {
        verifySite(row, col);
        return sites[getSiteId(row, col)];
    }

    public boolean isFull(int row, int col)  {
        verifySite(row, col);
        int siteId = getSiteId(row, col);
        return sites[siteId] &&
                topUnionFind.connected(siteId, topId);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return bottomUnionFind.connected(topId, bottomId) && openSites > 0;
    }

    /**
     * Counts from 1.
     */
    private void forNeighbours(int row, int col, Consumer<Integer> consumer) {
        if (row > 1) {
            consumer.accept(getSiteId(row - 1, col));
        }
        if (row < size) {
            consumer.accept(getSiteId(row + 1, col));
        }
        if (col > 1) {
            consumer.accept(getSiteId(row, col - 1));
        }
        if (col < size) {
            consumer.accept(getSiteId(row, col + 1));
        }
    }

    private int getSiteId(int row, int col) {
        return (row - 1) * size + (col - 1);
    }

    private void verifySite(int row, int col) {
        if (row <= 0 || row > size) {
            throw new IllegalArgumentException("row");
        }
        if (col <= 0 || col > size) {
            throw new IllegalArgumentException("col");
        }
    }

    private interface Consumer<T> {
        void accept(T object);
    }
}