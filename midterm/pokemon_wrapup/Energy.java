package pokemon_wrapup;
/**
 * The Energy class stores all the principles for Energy cards. It inherits the principles of a card
 * from the Card class
 */
public class Energy extends Card {
    public Energy(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return getName();
    }
    
}
