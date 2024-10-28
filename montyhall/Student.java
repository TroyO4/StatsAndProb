package montyhall;
import java.util.Random;

/**
 * The Student class creates a new student and gives them a random birthday in MMDD
 */
public class Student {
    /**
     * Birthday of the a student in MMDD
     */
    private int birthday;

    /**
     * Creates a new student with a random birthday
     */
    public Student() {
        this.birthday = generateRandomBirthday();
    }

    /**
     * Creates a random birthday to be assigned to a student
     * 
     * @return  birthday in MMDD
     */
    private int generateRandomBirthday() {
        Random random = new Random();
        int month = random.nextInt(12) + 1;
    
        // Determine the number of days based on the month
        int day = switch (month) {
            case 2 -> random.nextInt(28) + 1;  // February
            case 4, 6, 9, 11 -> random.nextInt(30) + 1;  // April, June, September, November
            default -> random.nextInt(31) + 1;  // January, March, May, July, August, October, December
        };
        // Birthday in MMDD
        return (month * 100) + day;
    }

    /**
     * Returns the random birthday assigned to a new student
     * 
     * @return birthday
     */
    public int getBirthday() {
        return birthday;
    }
}
