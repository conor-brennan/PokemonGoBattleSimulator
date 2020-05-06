package model.pokemon;

public class QMove extends Move {
    private String name;
    private int damage;
    private int energy;
    private int duration;
    private Type type;

    public QMove(String n, int dmg, int e, int dur, Type t) {
        super(n, dmg, e, dur,t);
        name = n;
        damage = dmg;
        energy = e;
        duration = dur;
        type = t;
    }

    public QMove() {
        name = "";
        damage = 0;
        energy = 0;
        duration = 0;
        type = null;
    }
    
    // MODIFIES: this
    // EFFECTS: sets duration of move to = dur
    public void setDuration(int dur) {
        duration = dur;
    }

    // EFFECTS: returns duration of move (each 1 duration = 0.5 seconds)
    public int getDuration() {
        return duration;
    }
}
