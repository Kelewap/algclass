import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96d;
    private final double[] trials;
    private final double mean;
    private final double stddev;

    public PercolationStats(int n, int trials) {
        check(n > 0, "n not > 0");
        check(trials > 0, "tiranls not > 0");
        double size = n * n;
        this.trials = new double[trials];
        for (int i = 0; i < trials; i++) {
            this.trials[i] = conduct(n) / size;
        }

        this.mean = StdStats.mean(this.trials);
        this.stddev = StdStats.stddev(this.trials);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trials.length));
    }

    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trials.length));
    }

    private int conduct(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            percolation.open(row, col);
        }
        return percolation.numberOfOpenSites();
    }

    private void check(boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean                    = %f\n", percolationStats.mean());
        StdOut.printf("stddev                  = %f\n", percolationStats.stddev());
        StdOut.printf("95%s confidence interval = [%f, %f]\n", "%", percolationStats.confidenceLo(), percolationStats.confidenceHi());
    }
}