package pokemon;
import java.util.ArrayList;
import java.util.Random;

/**
 * The PokemonGameRareCandies class looks for the odds of bricking with 1-4 rare candies in the deck.
 * Also it produces the odds for having 1,2,3, or 4 rare candies prized
 */
public class PokemonGameRareCandies {
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private ArrayList<Card> prizeCards;
    private Random rng;

    /**
     * Creates a new PokemonRareCandies game with an empty deck, hand, and prize cards.
     */
    public PokemonGameRareCandies() {
        deck = new ArrayList<>();
        hand = new ArrayList<>();
        prizeCards = new ArrayList<>();
        rng = new Random();
    }

    /**
     * Puts the cards into the deck. Pokemon, Rare Candy, and Energy cards combine to 60
     * 
     * @param pokemonCount the number of pokemon cards in the deck
     * @param rareCandyCount the number of rare candies in the deck
     */
    public void fillDeck(int pokemonCount, int rareCandyCount) {
        deck.clear();

        for (int i = 0; i < pokemonCount; i++) {
            deck.add(new Charmander());
        }

        // Add the Rare Candy cards
        for (int i = 0; i < rareCandyCount; i++) {
            deck.add(new RareCandyTrainer("Rare Candy"));
        }

        for (int i = 0; i < (60 - pokemonCount - rareCandyCount); i++) {
            deck.add(new Energy());
        }
    }
    /**
     * 7 random cards drawn from the deck
     */
    public void drawHand() {
        hand.clear();
        for (int i = 0; i < 7; i++) {
            int cardToTakeIndex = rng.nextInt(deck.size());
            hand.add(deck.get(cardToTakeIndex));
            deck.remove(cardToTakeIndex);
        }
    }

    /**
     * Draw 6 prize cards from the deck
     */
    public void drawPrizeCards() {
        prizeCards.clear();
        for (int i = 0; i < 6; i++) {
            int cardToTakeIndex = rng.nextInt(deck.size());
            prizeCards.add(deck.get(cardToTakeIndex));
            deck.remove(cardToTakeIndex);
        }
    }

    /**
     * Check the hand for a pokemon card
     * 
     * @return true if there is a pokemon card in the hand, false otherwise
     */
    public boolean hasPokemon() {
        for (Card singleCard : hand) {
            if (singleCard instanceof Pokemon) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if all the rare candies are in the prize cards. If so the game is bricked
     * @param rareCandyCount the number of rare candy cards in the deck
     * @return true if all of the rare candies are in the prize cards
     */
    public boolean isBricked(int rareCandyCount) {
        int rareCandiesInPrizes = 0;
        for (Card card : prizeCards) {
            if (card instanceof RareCandyTrainer && card.getName().equals("Rare Candy")) {
                // keep track of rare candies in prize cards
                rareCandiesInPrizes++;
            }
        }
        return rareCandiesInPrizes == rareCandyCount;
    }

    /**
     * Plays one game
     * 
     * @param pokemonCount the number of pokemon cards in the deck
     * @param rareCandyCount the number of rare candy cards in the deck
     * @return true if the game is bricked, false otherwise
     */
    public boolean simulateOneGame(int pokemonCount, int rareCandyCount) {
        fillDeck(pokemonCount, rareCandyCount);
        drawHand();

        // Not bricked if there are no pokemon are in the hand
        if (!hasPokemon()) {
            return false;
        }
        drawPrizeCards();
        return isBricked(rareCandyCount);
    }

    /**
     * Plays multiple games to calculate the odds of the deck being bricked
     * 
     * @param runs the number of games played
     * @param pokemonCount the number of pokemon cards in the deck
     * @param rareCandyCount the number of rare candy cards in the deck
     * @return the chance of the deck being bricked in percentage
     */
    public double simulateBrickedOdds(int runs, int pokemonCount, int rareCandyCount) {
        int validGames = 0;
        int brickedGames = 0;

        for (int i = 0; i < runs; i++) {
            boolean bricked = simulateOneGame(pokemonCount, rareCandyCount);
            if (bricked) {
                brickedGames++;
            }
            if (hasPokemon()) {
                validGames++;
            }
        }

        return (double) brickedGames / validGames * 100;
    }

    /**
     * Plays multiple games to calculate the odds of being prized a certain amount of rare candies with
     * with only a certain amount in the deck.
     * 
     * @param runs the number of games played
     * @param pokemonCount the number of pokemon cards in the deck
     * @param rareCandyCount the number of rare candy cards in the deck
     * @param prizedCandies the number of rare candies in the prize cards
     * @return the chance of having the exact number of rare candies prized in percentage
     */
    public double simulatePrizedOdds(int runs, int pokemonCount, int rareCandyCount, int prizedCandies) {
        int validGames = 0;
        int prizedGames = 0;

        for (int i = 0; i < runs; i++) {
            fillDeck(pokemonCount, rareCandyCount);
            drawHand();
            if (hasPokemon()) {
                validGames++;
                drawPrizeCards();
                
                int rareCandiesInPrizes = 0;
                for (Card card : prizeCards) {
                    if (card instanceof RareCandyTrainer && card.getName().equals("Rare Candy")) {
                        rareCandiesInPrizes++;
                    }
                }

                if (rareCandiesInPrizes == prizedCandies) {
                    prizedGames++;
                }
            }
        }

        return (double) prizedGames / validGames * 100;
    }

    /**
     * Plays the games for bricked odds
     * 
     * @param runs the number of games played
     * @param pokemonCount the number of pokemon cards in the deck
     */
    public void runBrickedSimulations(int runs, int pokemonCount) {
        for (int rareCandyCount = 1; rareCandyCount <= 4; rareCandyCount++) {
            double brickedOdds = simulateBrickedOdds(runs, pokemonCount, rareCandyCount);
            System.out.printf("With %d Rare Candy(s) in the deck, the odds of the deck being bricked is: %.2f%%\n", rareCandyCount, brickedOdds);
        }
        System.out.println();
    }

    /**
     * Plays the games for rare candies prized odds
     * 
     * @param runs the number of games played
     * @param pokemonCount the number of pokemon cards in the deck
     */
    public void runPrizedSimulations(int runs, int pokemonCount) {
        for (int rareCandyCount = 1; rareCandyCount <= 4; rareCandyCount++) {
            for (int prizedCandies = 1; prizedCandies <= rareCandyCount; prizedCandies++) {
                double prizedOdds = simulatePrizedOdds(runs, pokemonCount, rareCandyCount, prizedCandies);
                System.out.printf("With %d Rare Candy(s) in the deck, the odds of having exactly %d Rare Candy(s) prized is: %.2f%%\n", rareCandyCount, prizedCandies, prizedOdds);
            }
        }
        System.out.println();
    }

    /**
     * The main method plays the games and displays the results
     * 
     * @param args
     */
    public static void main(String[] args) {
        PokemonGameRareCandies game = new PokemonGameRareCandies();
        game.runBrickedSimulations(10000, 15);
        game.runPrizedSimulations(10000, 15);
    }
}
