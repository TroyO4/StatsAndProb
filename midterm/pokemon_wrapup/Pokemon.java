package pokemon_wrapup;
import java.util.ArrayList;

/**
 * The Pokemon class stores all of the principles for the pokemon in the game. It inherits the principles of a card
 * from the card class
 */
public class Pokemon extends Card {
    private int hp;
    private int attackDamage;
    private int energyRequired;
    private boolean isActive;
    private ArrayList<Energy> attachedEnergy;
    private int currentEnergy;

    /**
     * Creates a new pokemon card with specified name, health, attack damage, and energy requirement.
     *
     * @param name the name of the pokemon
     * @param hp the health of the pokemon
     * @param attackDamage the damage of the pokemons attacks
     * @param energyRequired the number of energy cards that must be attatched to perform an attack
     */
    public Pokemon(String name, int hp, int attackDamage, int energyRequired) {
        super(name);
        this.hp = hp;
        this.attackDamage = attackDamage;
        this.energyRequired = energyRequired;
        this.isActive = false;
        this.attachedEnergy = new ArrayList<>();
        this.currentEnergy = 0;
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
     * @param hp the health of the pokemon
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Heals the pokemon
     *
     * @param amount the amount to heal the pokemon
     */
    public void heal(int amount) {
        hp += amount;
    }

     /**
     * Looks for what pokemon is active
     *
     * @return true if the pokemon is active, false if not
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Makes chosen pokemon active
     *
     * @param active the new active pokemon
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Reduces the health of the pokemon by whatever amount the attack damage was
     *
     * @param damage the amount of damage dealt to the pokemon
     */
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) hp = 0;
    }

    /**
     * Returns the attack damage of the pokemon.
     *
     * @return attack damage of the pokemon
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Returns the amount of energy that must be attatched for the pokemon to attack
     *
     * @return energy required to attack
     */
    public int getEnergyRequired() {
        return energyRequired;
    }
    
    /**
     * Removes all previously attatched energy from the pokemon
     */
    public void clearEnergy() {
        attachedEnergy.clear();
    }

     /**
     * Returns the current number of energy cards attached to the pokemon
     *
     * @return the current energy attached to pokemon
     */
    public int getCurrentEnergy() {
        return currentEnergy;
    }

    /**
     * Attaches an energy card to a pokemon
     *
     * @param energy the energy card being attached
     */
    public void attachEnergy(Energy energy) {
        attachedEnergy.add(energy);
        System.out.println("Attached " + energy.getName() + " to " + getName() + "\n");
    }
   
    /**
     * Returns the number of energy cards attached to the pokemon.
     *
     * @return the total number of attached energy cards
     */
    public int getTotalEnergy() {
        return attachedEnergy.size();
    }

    /**
     * Checking to see if the pokemon has enough energy to attack
     *
     * @return true if the pokemon has enough energy to attack, false otherwise
     */
    public boolean hasSufficientEnergy() {
        return attachedEnergy.size() >= energyRequired;
    }

    /**
     * Returns the name of the pokemon as a string
     *
     * @return name of the pokemon
     */
    @Override
    public String toString() {
        return getName();
    }
}
