import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StatsLibrary
{
    public double computeMean(ArrayList<Integer> listOfNumbers)
    {
        int sum = 0;
        for (int singleNumber : listOfNumbers) {
            sum = sum + singleNumber;
        }
        return sum / (double) listOfNumbers.size();
    }

    public double computeMedian(ArrayList<Integer> listOfNumbers)
    {
        Collections.sort(listOfNumbers);
        int size = listOfNumbers.size();
        if (size % 2 == 0) {
            return (listOfNumbers.get(size / 2 - 1) + listOfNumbers.get(size / 2)) / 2.0;
        } else {
            return listOfNumbers.get(size / 2);
        }
    }

    public ArrayList<Integer> computeMode(ArrayList<Integer> listOfNumbers) {
		HashMap<Integer, Integer> frequencyMap = new HashMap<>();
		for (int number : listOfNumbers) {
			frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
		}
	
		int maxFrequency = 0;
		ArrayList<Integer> modes = new ArrayList<>();
		for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
			if (entry.getValue() > maxFrequency) {
				maxFrequency = entry.getValue();
				modes.clear();
				modes.add(entry.getKey());
			} else if (entry.getValue() == maxFrequency) {
				modes.add(entry.getKey());
			}
		}
	
		if (modes.isEmpty()) {
			return new ArrayList<>();
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