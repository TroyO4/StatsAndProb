package pokemon;

/**
 * The RareCandyTrainer class creates tbe rare candies. It inherits the principles of a card from the Card class
 */
public class RareCandyTrainer extends Card {

    /**
     * Creates a new RareCandyTrainer card
     *
     * @param name the name of the card
     */
    public RareCandyTrainer(String name) {
        super(name);
    }

    /**
     * Creates a new RareCandyTrainer card and names it "Rare Candy Trainer".
     */
    public RareCandyTrainer() {
        super("Rare Candy Trainer");
    }
}
