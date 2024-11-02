package stats_library;
import java.util.ArrayList;

/**
 * The TestLibrary class tests the methods from the StatsLibrary class
 */
public class TestLibrary{
    /**
     * Main method to test and demonstrate the functions of StatsLibrary.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args){
        StatsLibrary tester = new StatsLibrary();
        ArrayList<Integer> listOfNumbers = new ArrayList<>();
        ArrayList<String> daysOfWeek = new ArrayList<>();
        ArrayList<String> subsetA = new ArrayList<>();
        ArrayList<Double> probabilities = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        
        for (int i = 0; i < 100; i++){
            listOfNumbers.add(i);
        }
        
        // add extra numbers to listOfNumbers to create duplicates for testing mode
        listOfNumbers.add(2);
        listOfNumbers.add(2);
        listOfNumbers.add(3);
        listOfNumbers.add(4);
        listOfNumbers.add(4);
        listOfNumbers.add(4);
        listOfNumbers.add(5);
        listOfNumbers.add(6);
        listOfNumbers.add(6);
        listOfNumbers.add(7);

        daysOfWeek.add("Monday");
        daysOfWeek.add("Tuesday");
        daysOfWeek.add("Wednesday");
        daysOfWeek.add("Thursday");
        daysOfWeek.add("Friday");
        daysOfWeek.add("Saturday");
        daysOfWeek.add("Sunday");

        // add some days for set operations to subsetA
        subsetA.add("Monday");
        subsetA.add("Wednesday");
        subsetA.add("Friday");

        // add to values and probabilities lists for expected value calculations
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        probabilities.add(0.1);
        probabilities.add(0.3);
        probabilities.add(0.4);
        probabilities.add(0.2);


        // mean and median
        System.out.println();
        System.out.printf("Mean: %.6f%n", tester.mean(listOfNumbers));
        System.out.println("Median: " + tester.median(listOfNumbers));
        
        // mode
        ArrayList<Integer> modes = tester.mode(listOfNumbers);
        System.out.println("Mode: " + modes);
        
        // standard deviation
        System.out.printf("Standard Deviation: %.6f%n", tester.standardDeviation(listOfNumbers));

        // combination and permutation
        int n = 5;
        int r = 3;
        System.out.println("\nCombination (" + n + "C" + r + "): " + tester.combination(n, r));
        System.out.println("Permutation (" + n + "P" + r + "): " + tester.permutation(n, r));
        
        // binomial and negative binomial distributions
        int trials = 10;
        int successes = 3;
        double probabilityOfSuccess = 0.5;
        System.out.printf("Binomial Distribution (n=%d, k=%d, p=%.1f): %.6f%n", trials, successes, probabilityOfSuccess, tester.binomialDistribution(trials, successes, probabilityOfSuccess));
        System.out.printf("Negative Binomial Distribution (n=%d, k=%d, p=%.1f): %.6f%n", trials, successes, probabilityOfSuccess, tester.negativeBinomialDistribution(trials, successes, probabilityOfSuccess));

        // joint, conditional, and intersection calculations
        double pA = 0.7;
        double pB = 0.5;
        double pGivenA = 0.8;
        double pIntersection = tester.independentIntersection(pA, pB);
        System.out.println("\nIndependent Intersection with P(A) = " + pA + " and P(B) = " + pB + ": " + tester.independentIntersection(pA, pB));
        System.out.printf("Joint Probability: P(A ∩ B) using P(A) and P(B|A): %.2f%n", tester.jointProbability(pA, pGivenA));
        System.out.printf("Conditional Probability P(A|B): %.1f%n", tester.conditionalProbability(pA, pB));
        System.out.printf("Dependent Intersection with P(A) = %.1f and P(B|A) = %.1f: %.2f%n", pA, pGivenA, tester.dependentIntersection(pA, pGivenA));
        System.out.println("Exclusive Union with P(A) = " + pA + " and P(B) = " + pB + ": " + tester.union(pA, pB, pIntersection, true));
        System.out.println("Non-Exclusive Union with P(A) = " + pA + " and P(B) = " + pB + ": " + tester.union(pA, pB, pIntersection, false));

        // see if events are independent or dependent
        if (tester.areIndependent(pA, pB, pIntersection)) {
            System.out.println("Events A and B are independent.");
        } else {
            System.out.println("Events A and B are dependent.");
        }

        if (tester.areDependent(pA, pB, pIntersection)) {
            System.out.println("Events A and B are dependent");
        } else {
            System.out.println("Events A and B are independent");
        }

        // set operations: union, intersection, and complement
        ArrayList<String> unionResult = tester.unionDaysOfWeek(subsetA, daysOfWeek);
        ArrayList<String> intersectionResult = tester.intersectionDaysOfWeek(subsetA, daysOfWeek);
        ArrayList<String> complementResult = tester.complementDaysOfWeek(daysOfWeek, subsetA);

       System.out.println("\nSet Programming Assignment:");
        System.out.println("Full Set: " + daysOfWeek);
        System.out.println("Subset A: " + subsetA);
        System.out.println("Union: " + unionResult);
        System.out.println("Intersection: " + intersectionResult);
        System.out.println("Complement of Subset A: " + complementResult);

        // variance calculation and probability of union
        System.out.printf("\nVariance of listOfNUmbers: %.6f%n", tester.variance(listOfNumbers));
        System.out.printf("Probability of Union for listOfNUmbers: %.1f%n", tester.probabilityOfUnion(listOfNumbers));
        
        // expected value and variance 
        double c=5;
        System.out.println("\nValues: " + values);
        System.out.println("Probabilities: " + probabilities);
        System.out.printf("Expected Value E(Y): %.1f%n", tester.expectedValue(values, probabilities));
        System.out.printf("Variance V(Y): %.2f%n", tester.varianceForRandomVariable(values, probabilities));

        // total probability and Bayes theorem
        double[] conditionalProbabilities = {0.7, 0.6, 0.9};
        double[] eventProbabilities = {0.3, 0.4, 0.3};
        int index = 1;
        System.out.printf("\nConditional Probabilities = {0.7, 0.6, 0.9} \nEvent Probabilities = {0.3, 0.4, 0.3} \nP(A): %.2f%n", tester.totalProbability(conditionalProbabilities, eventProbabilities));
        System.out.printf("Bayes Theorem: %.2f%n", tester.bayesTheorem(index, conditionalProbabilities, eventProbabilities));
        System.out.println();
    }

}