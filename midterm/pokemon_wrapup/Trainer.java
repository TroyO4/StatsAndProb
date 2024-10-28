package pokemon_wrapup;
/**
 * The Trainer class creates a Trainer card for the game. It inherits the principles of a card from the Card class
 */
public class Trainer extends Card {
    private String name;
    
    /**
     * Creates a new trainer card
     *
     * @param name the name of the trainer card
     */
    public Trainer(String name) {
        super(name);
        this.name = name;
    }

    /**
     * Gets the name of the trainer card.
     *
     * @return the name of the trainer card
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name of the trainer card as a string.
     *
     * @return the name of the trainer card
     */
    @Override
    public String toString() {
        return name;
    }
}
