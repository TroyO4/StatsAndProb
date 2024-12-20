package pokemon;
/**
 * The Card class stores all of the principles for a card
 */
public class Card {
    /**
     * The name of the card
     */
    private String name;
    
    /**
     * Creates a new card with the specific name.
     * 
     * @param name the name of the card
     */
    public Card(String name) {
        this.name = name;
    }
    
    /**
     * @return the name of the card
     */
    public String getName() {
        return name;
    }
}
