package monty_hall;
import java.util.Scanner;

/**
 * BirthdayProgram calculates the probability of there being a shared biirthday
 * between people in a class of a size chosen by the user
 */
public class BirthdayProgram {
    private int classSize;
    private final int numberOfRuns = 10000;

    /**
     * Gives the BirthdayProgram a specific number of pople in the class
     * 
     * @param classSize the number of people in the class
     */
    public BirthdayProgram(int classSize) {
        this.classSize = classSize;
    }

    

    /**
     * Checks for any shared birthdays
     * 
     * @return true if there is a shared birthday in the class, otherwise false
     */
    private boolean hasSharedBirthday() {
        int[] birthdays = new int[classSize];
        
        //loop over each student in the class
        for (int i = 0; i < classSize; i++) {
            Student student = new Student();
            int birthday = student.getBirthday();
            
            // Check if new birthday matches any old birthdays
            for (int j = 0; j < i; j++) {
                if (birthdays[j] == birthday) {
                    return true;
                }
            }
            birthdays[i] = birthday;
        }
        return false;
    }

    
    /** 
     * Runs a simulation to calculte the probability of a shared bithday in the class by counting 
     * the number of times a shared birthday occurs over 10,000 runs and calculates the percentage
     * 
     * @return the probability of a shared birthday in a class of that size chosen
     */
    public double runSimulation() {
        int sharedBirthdayCount = 0;

        for (int i = 0; i < numberOfRuns; i++) {
            // Count if there is a shared birthday in this run
            if (hasSharedBirthday()) {
                sharedBirthdayCount++;
            }
        }
        return (double) sharedBirthdayCount / numberOfRuns * 100;
    }

    
    /** 
     * Asks the user to input the size of the class
     * 
     * @return BirthdayProgram with the class size given by the user
     */
    public static BirthdayProgram getUserInput() {
        Scanner scanner = new Scanner(System.in);
        int classSize = 0;
        boolean validInput = false;
    
        // Make sure the user enters a number
        while (!validInput) {
            System.out.print("\nEnter the number of students in the class: ");
            if (scanner.hasNextInt()) {
                classSize = scanner.nextInt();
                validInput = true;
            } else {
                System.out.println("Please enter a number.");
                scanner.next();
            }
        }
        scanner.close();
        return new BirthdayProgram(classSize);
    }

    /**
     * Displays the results
     */
    public void displayProbability() {
        double probability = runSimulation();
        System.out.printf("\nThe probability of a shared birthday in a class of %d students is: %.2f%%\n\n", classSize, probability);
    }

    /**
     * Main method to run the program
     * 
     * @param args
     */
    public static void main(String[] args) {
        BirthdayProgram program = BirthdayProgram.getUserInput();
        program.displayProbability();
    }
}
