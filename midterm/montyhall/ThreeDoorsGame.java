/*
 * Troy O'Connor
 * Probability and Applied Stats
 * Answers to A and B at the bottom of the code
 */
package montyhall;
import java.util.Random;

/**
 * ThreeDoorsGame simulates a game show where the contestant is asked to choose
 * one out of three closed doors. Behind one door is a prize, while the other two
 * doors are hiding a dud. In the game ths host reveals a dud behind one of the doors
 * and asks the contestant if they want to keep their initial choice or switch doors.
 * The program calculates the odds on if the contestant keeps their initial choice or switch.
 */
public class ThreeDoorsGame {
    
    /**
     * Main method shows the results and percentage of switching and staying
     * 
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("\nWins by staying: " + playGameNoSwitch(10000) + " out of 10,000");
        System.out.println("Wins by switching: " + playGameWithSwitch(10000) + " out of 10,000");
        System.out.println("Win percentage by staying: " + (playGameNoSwitch(10000) * 100 / 10000) + "%");
        System.out.println("Win percentage by switching: " + (playGameWithSwitch(10000) * 100 / 10000) + "%\n");
    }

    /**
     * Calculates the results for if the contestant doesnt switch choices
     * 
     * @param trials the number of times the game is played
     * @return the number of wins the contestant gets from staying with initial choice
     */
    public static int playGameNoSwitch(int trials) {
        int wins = 0;
        Random rand = new Random();

        for (int i = 0; i < trials; i++) {
            //randomly place prize behind a door
            int goodPrize = rand.nextInt(3);
            int contestantChoice = rand.nextInt(3);
            if (contestantChoice == goodPrize) {
                wins++;
            }
        }
        return wins;
    }

    /**
     * Calculates the results for if the contestant does switch doors from their first choice
     * 
     * @param trials the numberof times the game is played
     * @return the number of wins the contestant gets from switching doors
     */
    public static int playGameWithSwitch(int trials) {
        int wins = 0;
        Random rand = new Random();

        for (int i = 0; i < trials; i++) {
            int goodPrize = rand.nextInt(3);
            int contestantChoice = rand.nextInt(3);
            int hostReveal = rand.nextInt(3);

            //Makes sure the door that is revealed by the host is not the prize or contestants door
            while (hostReveal == contestantChoice || hostReveal == goodPrize) {
                hostReveal = rand.nextInt(3);
            }

            //Find the right door to switch to
            int switchChoice = 3 - contestantChoice - hostReveal;
            if (switchChoice == goodPrize) {
                wins++;
            }
        }
        return wins;
    }
}

/*
 * Answers 2.20 A and B
 * 
 * A) If the contestant has no idea which curtains hide the various prizes and selects a 
 * curtain at random, assign reasonable probabilities to the simple events and calculate the 
 * probability that the contestant selects the curtain hiding the nice prize.
 * 
 * Answer:     A) P(G)= 1/3, P(D1)= 1/3, P(D2)= 1/3
 *                The probability that the contestant selects the curtain hiding the good prize
 *                at random is 1/3.
 * 
 * B) Before showing the contestant what was behind the curtain initially chosen, the game show 
 * host  would open one of the curtains and show the contestant one of the duds (he could always
 * do this because he knew the curtain hiding the good prize). He then offered the contestant the 
 * option of changing from the curtain initially selected to the other remaining unopened curtain. 
 * Which strategy maximizes the contestant’s probability of winning the good prize: stay with the 
 * initial choice or switch to the other curtain? In answering the following sequence of questions, 
 * you will discover that, perhaps surprisingly, this question can be answered by considering only 
 * the sample space above and using the probabilities that you assigned to answer part (a).

i. If the contestant choses to stay with her initial choice, she wins the good prize if and only if 
she initially chose curtain G. If she stays with her initial choice, what is the probability that 
she wins the good prize?

    Answer: if the contestant stays with their intial choice they would have a 1/3 change of winning.

ii. If the host shows her one of the duds and she switches to the other unopened curtain, what will 
be the result if she had initially selected G?

    Answer: If she had originally selected G and then she switches then she will have a dud and lose

iii. Answer the question in part (ii) if she had initially selected one of the duds.

    Answer: If she had originally selected a dud and then switches then she will have switched to the prize.

iv. If the contestant switches from her initial choice (as the result of being shown one of
the duds), what is the probability that the contestant wins the good prize?

    Answer: The contestant had a 2/3 chance of choosing one of the duds right off the bat, and if she did originally 
    pull the dud than she switches, she would win. So her probability of winning by switching is 2/3

v. Which strategy maximizes the contestant’s probability of winning the good prize: stay
with the initial choice or switch to the other curtain?

    Answer: The probability of winning if the contestant decides to stay with the first choice is 1/3,
    but if she changes the probability is 2/3. So the strategy that maximizes the contestants chance of winning is 
    to switch curtains.
 */