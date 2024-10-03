package pokmon;

import java.util.ArrayList;
import java.util.Random;

public class CardGame {
    private ArrayList<Card> deck;
    private ArrayList<Card> hand;
    private Random rng;

    public CardGame() {
        deck = new ArrayList<Card>();
        hand = new ArrayList<Card>();
        rng = new Random();
    }

    public void fillDeck() {
        deck.clear();
        for (int i = 0; i < 45; i++) {
            deck.add(new Energy());
        }
        for (int i = 0; i < 10; i++) {
            deck.add(new Charmander());
        }
        for (int i = 0; i < 5; i++) {
            deck.add(new Trainer("Potion"));
        }
    }

    
    public void drawHand() {
        hand.clear(); 
        for (int i = 0; i < 7; i++) {
            int cardIndex = rng.nextInt(deck.size());
            hand.add(deck.get(cardIndex));
            deck.remove(cardIndex);
        }
    }

    public boolean hasPokemon() {
        for (Card card : hand) {
            if (card instanceof Pokemon) { 
                return true;
            }
        }
        return false; 
    }

    public boolean simulateOneGame() {
        fillDeck();
        drawHand();
        return hasPokemon();
    }

    public double simulate(int runs) {
        int countSuccess = 0;

        for (int i = 0; i < runs; i++) {
            if (simulateOneGame()) {
                countSuccess++;
            }
        }

        return (double) countSuccess / runs * 100;
    }

    public static void main(String[] args) {
        CardGame game = new CardGame();
        int simulations = 10000;
        double successRate = game.simulate(simulations);
        
        System.out.println("Success rate of drawing at least one Pokémon: " + successRate + "%");
    }
}
