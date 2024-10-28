package statslibrary;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * The StatsLibrary class contains methods that compute different formulas from Statistics.
 */
public class StatsLibrary{
    /**
     * Calculates the mean of a list of integers
     *
     * @param listOfNumbers the list of numbers
     * @return the mean of the numbers
     */
    public double mean(ArrayList<Integer> listOfNumbers){
        int sum = 0;
        for (int singleNumber : listOfNumbers){
            sum += singleNumber;
        }
        return sum / (double) listOfNumbers.size();
    }

    /**
     * Calculates the median of a list of integers
     *
     * @param listOfNumbers the list of numbers
     * @return the median of the numbers
     */
    public double median(ArrayList<Integer> listOfNumbers) {
        int[] sortedArray = new int[listOfNumbers.size()];
        for (int i = 0; i < listOfNumbers.size(); i++) {
            sortedArray[i] = listOfNumbers.get(i);
        }
        java.util.Arrays.sort(sortedArray);
        int size = sortedArray.length;
        if (size % 2 == 0) {
            return (sortedArray[size / 2 - 1] + sortedArray[size / 2]) / 2.0;
        } else {
            return sortedArray[size / 2];
        }
    }

    /**
     * Finds the mode of a list of integers
     *
     * @param listOfNumbers the list of numbers
     * @return a list of mode values
     */
    public ArrayList<Integer> mode(ArrayList<Integer> listOfNumbers){
        int maxNum = Integer.MIN_VALUE;
        int minNum = Integer.MAX_VALUE;
        for (int number : listOfNumbers) {
            if (number > maxNum) maxNum = number;
            if (number < minNum) minNum = number;
        }
        int[] frequency = new int[maxNum - minNum + 1];
        for (int number : listOfNumbers) {
            frequency[number - minNum]++;
        }

        int maxFrequency = 0;
        ArrayList<Integer> modes = new ArrayList<>();

        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] > maxFrequency) {
                maxFrequency = frequency[i];
                modes.clear();
                modes.add(i + minNum);
            } else if (frequency[i] == maxFrequency && maxFrequency > 0) {
                modes.add(i + minNum);
            }
        }

        return modes;
    }

    /**
     * Gets the standard deviation of a list of integers
     *
     * @param listOfNumbers the list of numbers
     * @return the standard deviation of the numbers
     */
    public double standardDeviation(ArrayList<Integer> listOfNumbers){
        double mean = mean(listOfNumbers);
        double sum = 0;
        for (int number : listOfNumbers) {
            sum += Math.pow(number - mean, 2);
        }
        return Math.sqrt(sum / listOfNumbers.size());
    }

    /**
     * Calculates the factorial of a number
     *
     * @param n the number for factorial calculation
     * @return the factorial as a BigInteger
     */
    public BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    /**
     * Calculates the number of combinations (n choose r)
     *
     * @param n the total number of items
     * @param r the number of items chosen
     * @return the combination value
     */
    public BigInteger combination(int n, int r) {
        return factorial(n).divide(factorial(r).multiply(factorial(n - r)));
    }

    /**
     * Finds the number of permutations of selecting r items from n items
     *
     * @param n the total number of items
     * @param r the number of items chosen
     * @return the permutation value as BigInteger
     */
    public BigInteger permutation(int n, int r) {
        return factorial(n).divide(factorial(n - r));
    }

    /**
     * Gets the probability of a binomial distribution
     *
     * @param n the number of trials
     * @param k the number of successes
     * @param p the probability of success
     * @return the binomial distribution probability
     */
    public double binomialDistribution(int n, int k, double p) {
        BigInteger comb = combination(n, k);
        double probability = Math.pow(p, k) * Math.pow(1 - p, n - k);
        return comb.doubleValue() * probability;
    }

    /**
     * Computes the probability of a negative binomial distribution
     *
     * @param y the total number of trials
     * @param r the number of successes
     * @param p the probability of success
     * @return the negative binomial distribution probability
     */
    public double negativeBinomialDistribution(int y, int r, double p) {
        BigInteger comb = combination(y - 1, r - 1);
        double probability = Math.pow(p, r) * Math.pow(1 - p, y - r);
        return comb.doubleValue() * probability;
    }

    /**
     * Calculates the joint probability of two independent events
     *
     * @param pA the probability of event A
     * @param pBGivenA the conditional probability of event B given A
     * @return the joint probability of A and B
     */
    public double jointProbability(double pA, double pBGivenA) {
        return pA * pBGivenA;
    }

    /**
     * Calculates the conditional probability of event A given event B
     *
     * @param pA the probability of event A
     * @param pB the probability of event B
     * @return the conditional probability of A given B
     */
    public double conditionalProbability(double pA, double pB) {
        double pAIntersectionB = independentIntersection(pA, pB);
        return pAIntersectionB / pB;
    }

    /**
     * Finds the intersection probability of two independent events
     *
     * @param pA the probability of event A
     * @param pB the probability of event B
     * @return the intersection probability of A and B
     */
    public double independentIntersection(double pA, double pB) {
        return pA * pB;
    }

    /**
     * Calculates the intersection probability of two dependent events, A and B,
     * where the probability of B depends on A
     *
     * @param pA the probability of event A
     * @param pGivenA the conditional probability of event B given that A has occurred
     * @return the intersection probability of A and B
     */
    public double dependentIntersection(double pA, double pGivenA) {
        return pA * pGivenA;
    }
    
    /**
     * Gets the union probability of two events
     *
     * @param pA the probability of event A
     * @param pB the probability of event B
     * @param pIntersection the probability of both events occurring together
     * @param isExclusive whether the events are mutually exclusive
     * @return the union probability of events A and B
     */
    public double union(double pA, double pB, double pIntersection, boolean isExclusive) {
        if (isExclusive) {
            return pA + pB;
        } else {
            return pA + pB - pIntersection;
        }
    }

    /**
     * Determines if two events are independent based on their intersection probability
     *
     * @param pA the probability of event A
     * @param pB the probability of event B
     * @param pIntersection the observed probability of A and B occurring together
     * @return true if events A and B are independent, false otherwise
     */
    public boolean areIndependent(double pA, double pB, double pIntersection) {
        double expectedIntersection = independentIntersection(pA, pB);
        double epsilon = 0.000000001;
        return Math.abs(expectedIntersection - pIntersection) < epsilon;
    }

    /**
     * Determines if two events are dependent based on their intersection probability
     *
     * @param pA the probability of event A
     * @param pB the probability of event B
     * @param pIntersection the observed probability of A and B occurring together
     * @return true if events A and B are dependent, false if they are independent
     */
    public boolean areDependent(double pA, double pB, double pIntersection) {
        return !areIndependent(pA, pB, pIntersection);
    }

    /**
     * Gets the union of two lists of strings
     *
     * @param list1 the first list of strings
     * @param list2 the second list of strings
     * @return a list containing the union of the two input lists
     */
    public ArrayList<String> unionDaysOfWeek(ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> result = new ArrayList<>(list1);
        for (String item : list2) {
            if (!result.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Finds the intersection of two lists of strings, containing only common elements
     *
     * @param list1 the first list of strings
     * @param list2 the second list of strings
     * @return a list containing the intersection of the two input lists
     */
    public ArrayList<String> intersectionDaysOfWeek(ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> result = new ArrayList<>();
        for (String item : list1) {
            if (list2.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Gets the complement of a subset relative to a full set
     *
     * @param fullSet the full set of strings
     * @param subset the subset of strings
     * @return a list containing elements in the full set that are not in the subset
     */
    public ArrayList<String> complementDaysOfWeek(ArrayList<String> fullSet, ArrayList<String> subset) {
        ArrayList<String> result = new ArrayList<>(fullSet);
        result.removeAll(subset);
        return result;
    }

    /**
     * Calculates the sample variance of a list of integers
     *
     * @param listOfNumbers the list of numbers
     * @return the sample variance of the numbers
     */
    public double variance(ArrayList<Integer> listOfNumbers) {
        double mean = mean(listOfNumbers);
        double sumOfSquaredDifferences = 0;
        for (int number : listOfNumbers) {
            sumOfSquaredDifferences += Math.pow(number - mean, 2);
        }
        return sumOfSquaredDifferences / (listOfNumbers.size() - 1);
    }
   
    /**
     * Calculates the probability of the union
     *
     * @param listOfNumbers the list of occurrence counts for each event
     * @return the probability of the union of events
     */
    public double probabilityOfUnion(ArrayList<Integer> listOfNumbers) {
        int totalOccurrences = 0;
        for (int number : listOfNumbers) {
            totalOccurrences += number;
        }
        double probabilityOfUnion = 0.0;
        for (int number : listOfNumbers) {
            double probability = (double) number / totalOccurrences;
            probabilityOfUnion += probability;
        }
        return probabilityOfUnion;
    }

    /**
     * Calculates the expected value given a list of values and their probabilities
     *
     * @param values the list of values
     * @param probabilities the list of corresponding probabilities
     * @return the expected value
     */
    public double expectedValue(ArrayList<Integer> values, ArrayList<Double> probabilities) {
        double expectedValue = 0;
        for (int i = 0; i < values.size(); i++) {
            expectedValue += values.get(i) * probabilities.get(i);
        }
        return expectedValue;
    }

    /**
     * Finds the variance of a random variable given its values and probabilities
     *
     * @param values the list of values
     * @param probabilities the list of corresponding probabilities
     * @return the variance of the random variable
     */
    public double varianceForRandomVariable(ArrayList<Integer> values, ArrayList<Double> probabilities) {
        double mean = expectedValue(values, probabilities);
        double variance = 0;
        for (int i = 0; i < values.size(); i++) {
            double difference = values.get(i) - mean;
            variance += Math.pow(difference, 2) * probabilities.get(i);
        }
        return variance;
    }    

    /**
     * Gets the union probability of two events with a specified intersection probability
     *
     * @param pA the probability of event A
     * @param pB the probability of event B
     * @param pAIntersectionB the probability of A and B occurring together
     * @return the union probability of A and B
     */
    public double unionProbability(double pA, double pB, double pAIntersectionB) {
        return pA + pB - pAIntersectionB;
    }

    /**
     * Finds the total probability from an array of conditional probabilities and event probabilities
     *
     * @param conditionalProbabilities the array of conditional probabilities for each event
     * @param eventProbabilities the array of event probabilities
     * @return the total probability of all events
     */
    public double totalProbability(double[] conditionalProbabilities, double[] eventProbabilities) {
        double totalProbability = 0.0;
        for (int i = 0; i < conditionalProbabilities.length; i++) {
            totalProbability += conditionalProbabilities[i] * eventProbabilities[i];
        }
        return totalProbability;
    }
    
    /**
     * Uses Bayes theorem to calculate a conditional probability for a specified event.
     *
     * @param j the index of the conditional probability of interest
     * @param conditionalProbabilities the array of conditional probabilities
     * @param eventProbabilities the array of event probabilities
     * @return the conditional probability for the specified event
     */
    public double bayesTheorem(int j, double[] conditionalProbabilities, double[] eventProbabilities) {
        double numerator = conditionalProbabilities[j] * eventProbabilities[j];
        double denominator = 0.0;
        for (int i = 0; i < conditionalProbabilities.length; i++) {
            denominator += conditionalProbabilities[i] * eventProbabilities[i];
        }
        return numerator / denominator;
    }
}
