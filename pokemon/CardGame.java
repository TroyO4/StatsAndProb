package pokemon;

import java.util.ArrayList;
import java.util.Random;

public class CardGame {
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private Random rng;

    public CardGame() {
        deck = new ArrayList<>();
        hand = new ArrayList<>();
        rng = new Random();
    }

    public void fillDeck(int pokemonCount) {
        deck.clear();

        for (int i = 0; i < pokemonCount; i++) {
            deck.add(new Charmander());
        }

        for (int i = 0; i < (60 - pokemonCount); i++) {
            deck.add(new Energy());
        }
    }

    public void drawHand() {
        hand.clear();
        for (int i = 0; i < 7; i++) {
            int cardToTakeIndex = rng.nextInt(deck.size());
            hand.add(deck.get(cardToTakeIndex));
            deck.remove(cardToTakeIndex);
        }
    }

    public boolean hasPokemon() {
        for (Card singleCard : hand) {
            if (singleCard instanceof Pokemon) {
                return true;
            }
        }
        return false;
    }

    public boolean simulateOneGame(int pokemonCount) {
        fillDeck(pokemonCount);
        drawHand();
        return hasPokemon();
    }

    public double simulate(int runs, int pokemonCount) {
        int successCount = 0;
        for (int i = 0; i < runs; i++) {
            if (simulateOneGame(pokemonCount)) {
                successCount++;
            }
        }
        return (double) successCount / runs * 100;
    }

    public void runSimulations(int runs) {
        for (int pokemonCount = 1; pokemonCount <= 60; pokemonCount++) {
            double successRate = simulate(runs, pokemonCount);
            System.out.printf("With %d Pokémon in the deck, the success rate is: %.2f%%\n", pokemonCount, successRate);
        }
    }

    public static void main(String[] args) {
        CardGame game = new CardGame();
        int simulations = 10000;

        game.runSimulations(simulations);
    }
}
