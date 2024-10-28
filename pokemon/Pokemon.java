package pokemon;
/**
 * The Pokemon class stores all of the principles for the pokemon in the game. It inherits the principles of a card
 * from the card class
 */
public class Pokemon extends Card {
    private int hp;

    /**
     * Creates a new pokemon card with name and health points.
     *
     * @param name the name of the pokemon
     * @param hp the health of the Pokémon
     */
    public Pokemon(String name, int hp) {
        super(name);
        this.hp = hp;
    }

    /**
     * Returns the health of the pokemon.
     *
     * @return the health of the pokemon
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets the health of the pokemon.
     *
     * @param hp the new health value
     */
    public void setHp(int hp) {
        this.hp = hp;
    }
}
