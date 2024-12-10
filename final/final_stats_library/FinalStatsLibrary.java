package final_stats_library;

import java.util.ArrayList;

/**
 * The FinalStatsLibrary class gives methods that demonstrate various 
 * formulas and properties from the second half of our class.
 */
public class FinalStatsLibrary {

    /**
     * Computes the hypergeometric probability of getting exactly y successes 
     * in a sample of size n from a population of size N that has M successes.
     *
     * @param N the population size
     * @param M the number of successes in the population
     * @param n the sample size
     * @param y the desired number of successes in the sample
     * @return the hypergeometric probability
     */
    public double hypergeometricDistribution(int N, int M, int n, int y) {
        return combination(M, y) * combination(N - M, n - y) / combination(N, n);
    }

    /**
     * Computes the Poisson probability of y events when the average 
     * rate of occurrence is lambda.
     *
     * @param lambda the average rate of occurrences
     * @param y the number of events observed
     * @return the Poisson probability
     */
    public double poissonDistribution(double lambda, int y) {
        return Math.pow(lambda, y) * Math.exp(-lambda) / factorial(y);
    }

    /**
     * Calculates the proportion of data within k standard deviations of the mean
     * using Tchebysheff's theorem.
     *
     * @param mean the mean of the dataset
     * @param variance the variance of the dataset
     * @param k the number of standard deviations
     * @return the proportion of data within k standard deviations
     */
    public double tchebysheff(double mean, double variance, double k) {
        return 1 - (1 / (k * k));
    }

    /**
     * Evaluates the probability-generating function P(t) for a random variable.
     *
     * @param t the input value for the probability-generating function
     * @param probabilities the list of probabilities for different outcomes
     * @return the value of the probability-generating function at t
     */
    public double probabilityGeneratingFunction(double t, ArrayList<Double> probabilities) {
        double result = 0.0;
        for (int y = 0; y < probabilities.size(); y++) {
            result += probabilities.get(y) * Math.pow(t, y);
        }
        return result;
    }

    /**
     * Calculates the kth factorial moment of a random variable.
     *
     * @param probabilities the list of probabilities for different outcomes
     * @param k the order of the factorial moment
     * @return the kth factorial moment
     */
    public double factorialMoment(ArrayList<Double> probabilities, int k) {
        double result = 0.0;
        for (int y = 0; y < probabilities.size(); y++) {
            double product = 1.0;
            for (int j = 0; j < k; j++) {
                product *= (y - j);
            }
            result += probabilities.get(y) * product;
        }
        return result;
    }

    /**
     * Finds the combination given n and r.
     *
     * @param n the total number of items
     * @param r the number of items to choose
     * @return the number of ways to choose r items from n items
     */
    public double combination(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    /**
     * Calculates the factorial of a given number n.
     *
     * @param n the number to find the factorial for
     * @return the factorial of n
     */
    public double factorial(int n) {
        double result = 1.0;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
