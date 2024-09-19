import java.util.ArrayList;

public class StatsLibrary
{
    public double computeMean(ArrayList<Integer> listOfNumbers)
    {
        int sum = 0;
        for (int singleNumber : listOfNumbers) {
            sum += singleNumber;
        }
        return sum / (double) listOfNumbers.size();
    }

    public double computeMedian(ArrayList<Integer> listOfNumbers)
    {
        // Note: Sorting is still necessary for median
        int[] sortedArray = listOfNumbers.stream().mapToInt(i -> i).toArray();
        java.util.Arrays.sort(sortedArray);
        int size = sortedArray.length;
        if (size % 2 == 0) {
            return (sortedArray[size / 2 - 1] + sortedArray[size / 2]) / 2.0;
        } else {
            return sortedArray[size / 2];
        }
    }

    public ArrayList<Integer> computeMode(ArrayList<Integer> listOfNumbers)
    {
        // Find the range of the numbers
        int maxNum = Integer.MIN_VALUE;
        int minNum = Integer.MAX_VALUE;

        for (int number : listOfNumbers) {
            if (number > maxNum) maxNum = number;
            if (number < minNum) minNum = number;
        }

        // Create a frequency array
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

    public double computeStandardDeviation(ArrayList<Integer> listOfNumbers)
    {
        double mean = computeMean(listOfNumbers);
        double sum = 0;
        for (int number : listOfNumbers) {
            sum += Math.pow(number - mean, 2);
        }
        return Math.sqrt(sum / listOfNumbers.size());
    }
}
