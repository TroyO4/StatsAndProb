package pokmon;
public class Pokemon extends Card {
    private int hp;

    public Pokemon(String name, int hp) {
        super(name);
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
