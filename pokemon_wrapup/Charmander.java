package pokemon_wrapup;
/**
 * The Charmander class creates the Charmander pokemon card. It inherits the principles of a pokemon from the Pokemon class.
 */
public class Charmander extends Pokemon {
    /**
     * Creates a new Charmander card with 70 health, 50 attack damage, and it requires 2 energy to attack
     */
    public Charmander() {
        super("Charmander", 70, 50, 2);
    }
}
