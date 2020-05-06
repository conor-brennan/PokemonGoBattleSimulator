package model.pokemon;

public abstract class Move {
    private String name;
    private int damage;
    private int energy;
    private Type type;

    public Move(String n, int dmg, int e, int dur, Type t) {
        name = n;
        damage = dmg;
        energy = e;
        type = t;
    }

    Move(String n, int dmg, int e, Type t) {
        name = n;
        damage = dmg;
        energy = e;
        type = t;
    }

    Move() {
        name = "";
        damage = 0;
        energy = 0;
        type = null;
    }

    // EFFECTS: returns raw dmg of move
    public int getDamage() {
        return damage;
    }

    // EFFECTS: returns name of move
    public String getName() {
        return name;
    }


    // EFFECTS: returns type of move
    public Type getType() {
        return type;
    }

    // EFFECTS: returns effect of move on total energy
    public int getEnergy() {
        return energy;
    }

    // MODIFIES: this
    // EFFECTS: changes raw damage of move to = dmg
    public void setDamage(int dmg) {
        damage = dmg;
    }

    // MODIFIES: this
    // EFFECTS: changes name to = n
    public void setName(String n) {
        name = n;
    }

    // MODIFIES: this
    // EFFECTS: changes type of move to = t
    public void setType(Type t) {
        type = t;
    }

    // MODIFIES: this
    // EFFECTS: changes cost of move to = c
    public void setEnergy(int e) {
        energy = e;
    }
}
