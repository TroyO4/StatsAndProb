package pokemon;

import java.util.ArrayList;
import java.util.Random;

/**
 * The PokemonGame1 class 
 */
public class PokemonGame1 {
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private Random rng;

    public PokemonGame1() {
        deck = new ArrayList<>();
        hand = new ArrayList<>();
        rng = new Random();
    }

     /**
     * Puts the proper number of cards into the deck
     * 
     * @param pokemonCount the number of Pokémon cards to add to the deck
     */
    public void fillDeck(int pokemonCount) {
        deck.clear();

        //add the specific amount of pokemon cards
        for (int i = 0; i < pokemonCount; i++) {
            deck.add(new Charmander());
        }
        // Find out howm many slots are left in the deck after adding Pokémon cards
        int remainingCards = 60 - pokemonCount;
        
        // Add half of the remaining cards as Energy cards and the other half as Trainer cards
        for (int i = 0; i < remainingCards / 2; i++) {
            deck.add(new Energy());
        }
        for (int i = 0; i < remainingCards - (remainingCards / 2); i++) {
            deck.add(new Trainer());
        }
    }

    /**
     * Randomly draws 7 cards into the hand from the deck
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
     * Looks to see if the hand drawn has a pokemon in it
     * 
     * @return true if the hand has a pokemon card, false otherwise
     */
    public boolean hasPokemon() {
        for (Card singleCard : hand) {
            // Check if the card is from the Pokémon class
            if (singleCard instanceof Pokemon) {
                return true;
            }
        }
        return false;
    }

    /**
     * Plays a game
     * 
     * @param pokemonCount the number of pokemon cards in the deck
     * @return true if the hand contains a Pokémon card, false otherwise
     */
    public boolean simulateOneGame(int pokemonCount) {
        fillDeck(pokemonCount);
        drawHand();
        return hasPokemon();
    }

    /**
     * Plays multiple games to find the success rate of drawing at least one Pokémon card in each game
     * 
     * @param runs the number of games to be played
     * @param pokemonCount the number of Pokémon cards in the deck for a game
     * @return the success rate as a percentage
     */
    public double simulate(int runs, int pokemonCount) {
        int successCount = 0;

        for (int i = 0; i < runs; i++) {
            //keep track of successful games
            if (simulateOneGame(pokemonCount)) {
                successCount++;
            }
        }
        return (double) successCount / runs * 100;
    }

    /**
     * Plays games with d60 ifferent decks ranging from 1 pokemon card in the deck to 60. This method calculates the 
     * odds of having at least one pokemon card in your initial hand with each of the decks.
     *
     * @param runs the number of games played
     */
    public void runSimulations(int runs) {
        // Loop through the different amounts of Pokémon cards in the deck
        for (int pokemonCount = 1; pokemonCount <= 60; pokemonCount++) {
            double successRate = simulate(runs, pokemonCount);
            System.out.printf("With %d Pokémon in the deck, the success rate is: %.2f%%\n", pokemonCount, successRate);
        }
    }

     /**
     * The main method to run the pokemon game 10,000 times
     *
     * @param args
     */
    public static void main(String[] args) {
        PokemonGame1 game = new PokemonGame1();
        game.runSimulations(10000);
    }
}
