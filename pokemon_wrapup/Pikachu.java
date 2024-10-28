package pokemon_wrapup;
/**
 * The Pikachu class creates the pikachu pokemon card. It gets the principles of a pokemon from the Pokemon class
 */
public class Pikachu extends Pokemon {
    /**
     * Creates a new Pikachu card with 60 health, 40 attack damage, and it only requires 1 energy to attack
     */
    public Pikachu() {
        super("Pikachu", 60, 40, 1);
    }
}
