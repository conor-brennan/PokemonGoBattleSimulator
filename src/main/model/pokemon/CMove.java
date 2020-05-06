package model.pokemon;

public class CMove extends Move {
    private String name;
    private int damage;
    private int energy;
    private Type type;

    public CMove(String n, int dmg, int e, Type t) {
        super(n, dmg, e, t);
        name = n;
        damage = dmg;
        energy = e;
        type = t;
    }

    public CMove() {
        super();
    }
}
