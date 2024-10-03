package montyhall;
import java.util.Random;

public class MontyHall {
    public static void main(String[] args) {
        int trials = 10000;
        int stayWins = 0;
        int switchWins = 0;
        
        stayWins = playGameNoSwitch(trials);
        switchWins = playGameWithSwitch(trials);

        System.out.println("Wins by staying: " + stayWins + " out of " + trials);
        System.out.println("Wins by switching: " + switchWins + " out of " + trials);
        System.out.println("Win percentage by staying: " + (stayWins * 100 / trials) + "%");
        System.out.println("Win percentage by switching: " + (switchWins * 100 / trials) + "%");
    }

    public static int playGameNoSwitch(int trials) {
        int wins = 0;
        Random rand = new Random();

        for (int i = 0; i < trials; i++) {
            int goodPrize = rand.nextInt(3);
            int contestantChoice = rand.nextInt(3);

            if (contestantChoice == goodPrize) {
                wins++;
            }
        }
        return wins;
    }

    public static int playGameWithSwitch(int trials) {
        int wins = 0;
        Random rand = new Random();

        for (int i = 0; i < trials; i++) {
            int goodPrize = rand.nextInt(3);
            int contestantChoice = rand.nextInt(3);

            int hostReveal = rand.nextInt(3);
            while (hostReveal == contestantChoice || hostReveal == goodPrize) {
                hostReveal = rand.nextInt(3);
            }

            int switchChoice = rand.nextInt(3);
            while (switchChoice == contestantChoice || switchChoice == hostReveal) {
                switchChoice = rand.nextInt(3);
            }

            if (switchChoice == goodPrize) {
                wins++;
            }
        }
        return wins;
    }
}