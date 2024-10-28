package pokemon_wrapup;
/**
 * The Bulbasaur class creates the Bulbasuar pokemon card. It inherits the principles of a pokemon from the Pokemon class.
 */
public class Bulbasaur extends Pokemon {
    /**
     * Creates a new Bulbasaur card with 50 health, 40 attack damage, and it only requires 1 energy to attack
     */
    public Bulbasaur() {
        super("Bulbasaur", 50, 40, 1);
    }
}
