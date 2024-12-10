package final_stats_library;

import java.util.ArrayList;

/**
 * The FinalTestLibrary class is tests the methods created in the
 * FinalStatsLibrary class. It provides sample inputs for the formulas.
 */
public class FinalTestLibrary {
    /**
     * Main method to test the functions of FinalStatsLibrary.
     * Each test prints the variables, their values, and the results.
     *
     * @param args
     */
    public static void main(String[] args) {
        FinalStatsLibrary stats = new FinalStatsLibrary();

        // Hypergeometric Distribution
        int N = 20, M = 7, n = 5, y = 3;
        System.out.println("______________________________________________________________________________________________________________________________");
        System.out.printf("Hypergeometric Distribution Test:\n");
        System.out.printf("N (total population) = %d, M (successes in population) = %d, n (sample size) = %d, y (successes desired) = %d\n", N, M, n, y);
        System.out.printf("P(Y = y) = %.5f\n\n", stats.hypergeometricDistribution(N, M, n, y));

        // Poisson Distribution
        double lambda = 3.5;
        y = 2;
        System.out.printf("Poisson Distribution Test:\n");
        System.out.printf("lambda (mean rate) = %.1f, y (number of events) = %d\n", lambda, y);
        System.out.printf("P(Y = y) = %.5f\n\n", stats.poissonDistribution(lambda, y));

        // Tchebysheff's Theorem
        double mean = 50, variance = 25, k = 2.0;
        System.out.printf("Tchebysheff's Theorem Test:\n");
        System.out.printf("mean = %.1f, variance = %.1f, k (standard deviations) = %.1f\n", mean, variance, k);
        System.out.printf("Proportion of data within %.1f standard deviations: %.5f\n\n", k, stats.tchebysheff(mean, variance, k));

        // Probability-Generating Function
        double t = 0.5;
        ArrayList<Double> probabilities = new ArrayList<>();
        probabilities.add(0.1);
        probabilities.add(0.2);
        probabilities.add(0.4);
        probabilities.add(0.3);
        System.out.printf("Probability-Generating Function Test:\n");
        System.out.printf("t = %.1f, probabilities = %s\n", t, probabilities);
        System.out.printf("P(t) = %.5f\n\n", stats.probabilityGeneratingFunction(t, probabilities));

        // kth Factorial Moment
        int kFactorial = 2;
        System.out.printf("Factorial Moment Test:\n");
        System.out.printf("k = %d, probabilities = %s\n", kFactorial, probabilities);
        System.out.printf("k-th Factorial Moment: %.5f\n", stats.factorialMoment(probabilities, kFactorial));
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }
}
