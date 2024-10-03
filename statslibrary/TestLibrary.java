package statslibrary;
import java.util.ArrayList;

public class TestLibrary
{
    public static void main(String[] args)
    {
        StatsLibrary tester = new StatsLibrary();
        ArrayList<Integer> listOfNumbers = new ArrayList<>();
        
        for (int i = 0; i < 100; i++)
        {
            listOfNumbers.add(i);
        }
        
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
        
        System.out.println();
        System.out.println("Mean: " + tester.computeMean(listOfNumbers));
        System.out.println("Median: " + tester.computeMedian(listOfNumbers));
        
        ArrayList<Integer> modes = tester.computeMode(listOfNumbers);
        System.out.println("Mode: " + modes);
      
        
        System.out.println("Standard Deviation: " + tester.computeStandardDeviation(listOfNumbers));
        System.out.println();
    }
}