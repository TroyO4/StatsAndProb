package pokemon_wrapup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The PokemonGameWrapUp class allows the user to play against a computer apponent in a turn based
 * pokemon card game
 */
public class PokemonGameWrapUp {
    final private ArrayList<Card> deck;
    final private ArrayList<Card> hand;
    final private ArrayList<Card> prizes;
    private Pokemon activePokemon;
    private Pokemon opponentActivePokemon;
    private int playerPrizesTaken;
    private int opponentPrizesTaken;
    final private boolean opponentHasBenchPokemon = true;
    private int opponentActiveHp;
    final private ArrayList<Card> opponentHand;
    final private Scanner scanner;
    private boolean energyAttachedThisTurn;
    final private boolean isPlayerTurn; 

    /**
     * Creates a new PokemonGameWrapUp game
     */
    public PokemonGameWrapUp() {
        deck = new ArrayList<>();
        hand = new ArrayList<>();
        prizes = new ArrayList<>();
        opponentHand = new ArrayList<>();
        scanner = new Scanner(System.in);
        this.isPlayerTurn = true;
    }

    /**
     * Fills the deck with a mix of pokemon, Energy, and Trainer cards. Then the method shuffles the deck
     */
    public void fillDeck() {
        deck.clear();

        for (int i = 0; i < 6; i++) {
            deck.add(new Charmander());
            deck.add(new Pikachu());
        }

        for (int i = 0; i < 5; i++) {
            deck.add(new Bulbasaur());
        }

        for (int i = 0; i < 5; i++) {
            deck.add(new FireEnergy());
            deck.add(new WaterEnergy());
            deck.add(new GrassEnergy());
            deck.add(new ElectricEnergy());
            deck.add(new PsychicEnergy());
        }
        
        for (int i = 0; i < 6; i++) {
            deck.add(new Trainer("Draw 2 Cards"));
            deck.add(new Trainer("Heal 20 HP"));
            deck.add(new Trainer("Switch Pokemon"));
        }

        Collections.shuffle(deck);
    }

    /**
     * Both players draw 7 cards
     * Exits if either player cannot draw a full hand.
     */
    public void drawHand() {
        hand.clear();
        opponentHand.clear();

        for (int i = 0; i < 7; i++) {
            if (deck.isEmpty()) {
                System.out.println("\nYour deck is empty! You lose!\n");
                System.exit(0);
            }
            hand.add(deck.remove(0));
        }

        for (int i = 0; i < 7; i++) {
            if (deck.isEmpty()) {
                System.out.println("\nOpponent's deck is empty! Opponent loses!\n");
                System.exit(0);
            }
            opponentHand.add(deck.remove(0));
        }

        if (!hasActivePokemon()) {
            System.out.println("\nYou have no pokemon in your hand. Please start over.");
            System.exit(0);
        }

        if (!opponentHasActivePokemon()) {
            System.out.println("\nOpponent has no pokemon in their hand. Please start over.");
            System.exit(0);
        }
    }

    /**
     * Returns if the active player is the user or the opponent
     *
     * @return "player" if it's the player's turn, "opponent" if it is the opponents turn
     */
    public String getActivePlayer() {
        return isPlayerTurn ? "player" : "opponent";
    }

    /**
     * Makes sure that the player's hand has at least one pokemon.
     *
     * @return true if there is a pokemon in hand, false if not
     */
    private boolean hasActivePokemon() {
        return hand.stream().anyMatch(card -> card instanceof Pokemon);
    }

    /**
     * Make sure the opponent's also has at least one pokemon in it
     *
     * @return true if the opponent has a pokemon in hand, false otherwise
     */
    private boolean opponentHasActivePokemon() {
        return opponentHand.stream().anyMatch(card -> card instanceof Pokemon);
    }

    /**
     * Draws a card from the users deck and endds the game if the deck is empty.
     */
    public void drawCard() {
        if (!deck.isEmpty()) {
            hand.add(deck.remove(0));
            System.out.println("\nYou drew a card.\n");
            System.out.println("Hand: " + hand + "\n");
        } else {
            System.out.println("\nYour deck is empty! You lose!\n");
            System.exit(0);
        }
    }

    /**
     * Draws two cards from the deck when the trainer card is selected
     */
    public void drawTwoCards() {
        int cardsToDraw = Math.min(3, deck.size());
        for (int i = 0; i < cardsToDraw; i++) {
            if (!deck.isEmpty()) {
                hand.add(deck.remove(0));
            }
        }
        System.out.println("\nYou drew 2 cards.");
        System.out.println("Your hand is now: " + hand);
    }

    /**
     * Heals the pokemon currently in use
     *
     * @param amount the amount of health to heal
     */
    public void healActivePokemon(int amount) {
        if (activePokemon != null) {
            activePokemon.heal(amount);
            System.out.println("\nHealed " + activePokemon.getName() + " by " + amount + " HP.");
            System.out.println("Current HP: " + activePokemon.getHp() + "\n");
        } else {
            System.out.println("\nNo active pokemon to heal.\n");
        }
    }

    /**
     * Switches the users active pokemon with a selected pokemon from the hand.
     */
    public void switchActivePokemon() {
        System.out.println("\nChoose a pokemon to switch to:");
    
        // Shows the user the pokemon in hand their hand that are available to switch to
        ArrayList<Pokemon> pokemonInHand = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i) instanceof Pokemon) {
                pokemonInHand.add((Pokemon) hand.get(i));
                System.out.println((pokemonInHand.size()) + ": " + hand.get(i).getName());
            }
        }
    
        if (pokemonInHand.isEmpty()) {
            System.out.println("\nYou have no pokemon in your hand to switch to.\n");
            return;
        }
    
        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < pokemonInHand.size()) {
            Pokemon newActivePokemon = pokemonInHand.get(choice);
            hand.remove(newActivePokemon);
            hand.add(activePokemon);
            activePokemon = newActivePokemon;
            System.out.println("You switched to: " + activePokemon.getName() + "\n");
        } else {
            System.out.println("\nInvalid choice. Please select a valid pokemon.\n");
        }
    }

    /**
     * Lets the player use a Trainer card from their hand
     */
    public void useTrainerCard() {
        System.out.println("Choose a Trainer card to use:");
        ArrayList<Trainer> trainerCards = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i) instanceof Trainer) {
                trainerCards.add((Trainer) hand.get(i));
                System.out.println((trainerCards.size()) + ": " + hand.get(i).getName());
            }
        }
    
        // Check if there are no Trainer cards in your hand
        if (trainerCards.isEmpty()) {
            System.out.println("You have no Trainer cards in your hand.");
            return;
        }
    
        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < trainerCards.size()) {
            Trainer selectedTrainer = trainerCards.get(choice);
            // gets rid of the trainer card after using it
            hand.remove(selectedTrainer);
    
            switch (selectedTrainer.getName()) {
                case "Draw 2 Cards":
                    drawTwoCards(); 
                    return;
    
                case "Heal 20 HP":
                    healActivePokemon(20);
                    return;
    
                case "Switch Pokemon":
                    switchActivePokemon();
                    return;
    
                default:
                    System.out.println("\nTrainer card not recognized.\n");
                    return;
            }
        } else {
            System.out.println("Invalid choice. Please select a valid Trainer card.");
        }
    }

    /**
     * Deals a number of additional cards from deck.
     *
     * @param numberOfCards the number of cards to draw
     */
    public void drawAdditionalCards(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            if (!deck.isEmpty()) {
                hand.add(deck.remove(0));
            } else {
                System.out.println("\nYour deck is empty! Cannot draw more cards.");
                break;
            }
        }
    }
    
    /**
     * Attaches an energy card to the active pokemon
     */
    public void attachEnergy() {
        if (activePokemon == null) {
            System.out.println("\nYou must have an active pokemon to attach energy.\n");
            return;
        }

        Card energyCard = hand.stream().filter(card -> card instanceof Energy).findFirst().orElse(null);
        if (energyCard != null) {
            hand.remove(energyCard);
            activePokemon.attachEnergy((Energy) energyCard);
        } else {
            System.out.println("\nNo Energy card available in hand.\n");
        }
    }

    /**
     * Attaches an energy card to the opponents pokemon
     */
    public void opponentAttachEnergy() {
        if (opponentActivePokemon == null) {
            System.out.println("\nOpponent must have an active pokemon to attach energy.\n");
            return;
        }

        Card energyCard = opponentHand.stream()
            .filter(card -> card instanceof Energy)
            .findFirst()
            .orElse(null);

        if (energyCard != null) {
            opponentHand.remove(energyCard);
            opponentActivePokemon.attachEnergy((Energy) energyCard);
        } else {
            System.out.println("\nOpponent has no Energy card to attach.\n");
        }
    }

    /**
     * Allows the user to attack the opponents pokemon. The opponents pokemons health goes down by the amount of damage taken from attack.
     * The user claims a prize if the opopnents pokemon is knocked out
     */
    public void attack() {
        if (activePokemon.hasSufficientEnergy()) {
            System.out.println("\nAttacking the opponent...");

            int damageDealt = activePokemon.getAttackDamage();
            opponentActiveHp -= damageDealt;
    
            if (opponentActiveHp < 0) {
                opponentActiveHp = 0;
            }
    
            System.out.println("You dealt " + damageDealt + " damage to opponent's " + opponentActivePokemon + ". Opponent's HP: " + opponentActiveHp);
    
            if (opponentActiveHp <= 0) {
                claimPrize();
                if (playerPrizesTaken==6) {
                    return;
                }
                replaceOpponentActivePokemon();
            }
        } else {
            System.out.println("Not enough energy to attack.");
        }
    }
    
    /**
     * Gives the user a prize card and adds it to the player's hand.
     */
    public void claimPrize() {
        if (!prizes.isEmpty()) {
            Card prizeCard = prizes.remove(0);
            hand.add(prizeCard);
            playerPrizesTaken++;
            System.out.println("You took a prize card: " + prizeCard.getName());
        }
    }

    /**
     * Gives the opponent a prize card if they knock out the users pokemon
     */
    public void claimOpponentPrize() {
        if (!deck.isEmpty()) {
            Card prizeCard = deck.remove(0);
            opponentHand.add(prizeCard);
            opponentPrizesTaken++;
            System.out.println("Opponent took a prize card.");
        }
    }

    /**
     * Checks to see if anyone has won the game yet. You win by taking all 6 prize cards.
     *
     * @return true if the game is over, false if it is not
     */
    public boolean checkGameState() {
        if (playerPrizesTaken == 6) {
            System.out.println("\nCongratulations! You won by taking all 6 of your prize cards!\n");
            return true;
        }
        
        if (opponentPrizesTaken == 6) {
            System.out.println("\nYou lost! Your opponent took all 6 prize cards.\n");
            return true;
        }
        
        if (!opponentHasBenchPokemon) {
            System.out.println("\nCongratulations! You won! Your opponent has no benched pokemon left.\n");
            return true;
        }
        
        return false;
    }

    /**
     * This method starts the game, going back and forth between the user and opponents turns checking to see if anyone
     * has won yet
     */
    public void startGame() {
        setupGame();
        setActivePokemon();
        while (true) {
            performPlayerTurn();
            if (checkGameState()) break;
            performOpponentTurn();
            if (checkGameState()) break;
        }
    }

    /**
     * Chooses the players active pokemon(first pokemon in their hand)
     * If there are no pokemon in the hand it displays a message saying that there is no pokemon to play.
     */
    private void setActivePokemon() {
        for (Card card : hand) {
            if (card instanceof Pokemon) {
                activePokemon = (Pokemon) card;
                System.out.println("\nYour Hand: " + hand);
                System.out.println("Your active pokemon is: " + activePokemon.getName());
                hand.remove(card);
                return;
            }
        }
        System.out.println("You do not have any pokemon in your hand to play!");
    }

    /**
     * Gets the game ready by filling and shuffling the deck, drawing hands, and setting active pokemon
     * Draws 6 cards as prize cards
     */
    public void setupGame() {
        fillDeck();
        drawHand();
        activePokemon = (Pokemon) hand.stream().filter(card -> card instanceof Pokemon).findFirst().orElse(null);
        opponentActivePokemon = (Pokemon) opponentHand.stream().filter(card -> card instanceof Pokemon).findFirst().orElse(null);
        if (activePokemon != null) {
            activePokemon.setActive(true);
        }
        if (opponentActivePokemon != null) {
            opponentActivePokemon.setActive(true);
            opponentActiveHp = opponentActivePokemon.getHp();
        }
        for (int i = 0; i < 6; i++) {
            prizes.add(deck.remove(0));
        }
    }

    private boolean isFirstTurn = true;

    /**
     * Performs the player's turn, lets the user attach energy, attack, use a Trainer card, or end their turn.
     */
    public void performPlayerTurn() {
        System.out.println("\n--- Your Turn ---");

        if (!isFirstTurn) {
            drawCard();
        }

        isFirstTurn = false;
        energyAttachedThisTurn = false;

        if (activePokemon != null) {
            System.out.println("Energy attached to " + activePokemon.getName() + ": " + activePokemon.getTotalEnergy());
        }

        while (true) {
            try {
                System.out.println("Choose an action: (1) Attach Energy, (2) Attack, (3) Use Trainer Card, (4) End Turn");
                int choice = scanner.nextInt(); 

                switch (choice) {
                    case 1:
                        if (!energyAttachedThisTurn) {
                            attachEnergy();
                            energyAttachedThisTurn = true;
                        } else {
                            System.out.println("\nYou can only attach one Energy card per turn.");
                        }
                        break;
                    case 2:
                        attack();
                        return;
                    case 3:
                        if (hasTrainerCardInHand()) {
                            useTrainerCard();
                        } else {
                            System.out.println("You have no Trainer cards in your hand. Please choose another action.");
                        }
                        return;
                    case 4:
                        System.out.println("Ending your turn.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please choose a valid action.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number");
                scanner.nextLine();
            }
        }
    }

    /**
     * Looks for a trainer card in the users hand
     *
     * @return true if there is a Trainer card in the hand, false otherwise
     */
    private boolean hasTrainerCardInHand() {
        return hand.stream().anyMatch(card -> card instanceof Trainer);
    }

    /**
     * Performs the opponents turn by drawing a card, attaching energy, and attacking
     */
    public void performOpponentTurn() {
        if (!isFirstTurn) {
            opponentDrawCard();
        }

        isFirstTurn = false;
        System.out.println("\n--- Opponent's Turn ---");
        System.out.println("Opponent drew a card.");

        // Opponent attaches energy before attacking
        opponentAttachEnergy();

        if (opponentActivePokemon != null) {
            System.out.println("Energy attached to " + opponentActivePokemon.getName() + ": " + opponentActivePokemon.getTotalEnergy());
        }

        // Check if opponent's pokemon has enough energy to attack
        if (opponentActivePokemon.hasSufficientEnergy()) {
            System.out.println("Opponent's " + opponentActivePokemon.getName() + " attacks!");

            if (activePokemon != null) {
                activePokemon.takeDamage(opponentActivePokemon.getAttackDamage());
                System.out.println("Your pokemon now has " + activePokemon.getHp() + " HP remaining.");
                if (activePokemon.getHp() <= 0) {
                    System.out.println("Your pokemon is knocked out!");
                    replaceActivePokemon();
                    claimOpponentPrize();

                    if (checkGameState()) return;
                }
            }
        } else {
            System.out.println("Opponent's " + opponentActivePokemon.getName() + " does not have enough energy to attack.");
        }
    }
    
    /**
     * Draws a card from the opponents deck
     * If the deck is empty the opponent loses
     */
    private void opponentDrawCard() {
        if (!deck.isEmpty()) {
            Card drawnCard = deck.remove(0);
            opponentHand.add(drawnCard);
        } else {
            System.out.println("\nOpponent's deck is empty! Opponent loses!");
            System.exit(0);
        }
    }

    /**
     * Replaces the opponent's active pokemon with another pokemon from their hand, if available. If no replacement 
     * is available a message is shown that the opponent has no pokemon left to play.
     */
    private void replaceOpponentActivePokemon() {
        // Clear the energy from the knocked-out pokemon
        if (opponentActivePokemon != null) {
            opponentActivePokemon.clearEnergy(); // Reset energy when pokemon is knocked out
        }
    
        opponentHand.add(opponentActivePokemon);
        opponentActivePokemon = null;
    
        // Find a new active pokemon from the opponent's hand
        for (Card card : opponentHand) {
            if (card instanceof Pokemon) {
                opponentActivePokemon = (Pokemon) card;
                System.out.println("\nOpponent's pokemon knocked out!");
                System.out.println("Opponent's new active pokemon is: " + opponentActivePokemon.getName());
                opponentHand.remove(card);
                opponentActiveHp = opponentActivePokemon.getHp();
                opponentActivePokemon.setActive(true);
                return;
            }
        }
        System.out.println("Opponent does not have any pokemon left to play!");
    }
    
    /**
     * Draws a card from the opponents deck. Ends the game if the deck is empty
     */
    public void drawAdditionalOpponentCards(int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            if (!deck.isEmpty()) {
                opponentHand.add(deck.remove(0));
            } else {
                System.out.println("\nOpponent's deck is empty! Cannot draw more cards.");
                break;
            }
        }
    }

    /**
     * Heals the opponents pokemon
     *
     * @param amount the amount of health given to the opponents pokemon
     */
    public void healOpponentActivePokemon(int amount) {
        if (opponentActivePokemon != null) {
            opponentActivePokemon.heal(amount);
            System.out.println("Opponent healed " + opponentActivePokemon.getName() + " by " + amount + " HP.");
            System.out.println("Opponent's pokemon HP: " + opponentActivePokemon.getHp());
        } else {
            System.out.println("Opponent has no active pokemon to heal.");
        }
    }

    /**
     * Changes the users active pokemon to a different pokemon from their deck. If the active pokemon is 
     * knocked out a new pokemon is set as active.
     */
   public void replaceActivePokemon() {
    if (activePokemon != null) {
        activePokemon.clearEnergy();
    }
    hand.add(activePokemon);
    activePokemon = null;

    for (Card card : hand) {
        if (card instanceof Pokemon) {
            activePokemon = (Pokemon) card;
            System.out.println("Your new active pokemon is: " + activePokemon.getName());
            hand.remove(card);
            activePokemon.setActive(true);
            return;
        }
    }
    System.out.println("You do not have any pokemon left to play!");
    }

    /**
     * The main method that runs the game
     *
     * @param args
     */
    public static void main(String[] args) {
        PokemonGameWrapUp game = new PokemonGameWrapUp();
        game.startGame();
    }
}
