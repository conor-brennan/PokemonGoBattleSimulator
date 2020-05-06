package model.pokemon;

import model.battle.Battle;

import java.lang.Math;

public class Pokemon {

    public static final CPMultiplier cpm = new CPMultiplier();

    protected String name;
    protected int attack;
    protected int defence;
    protected int stamina;
    protected int hp;
    protected Type type1;
    protected Type type2;
    protected QMove quickMove;
    protected CMove chargeMove1;
    protected CMove chargeMove2;
    protected int energy;
    protected int level;
    protected int shields;

    public Pokemon(String n, int a, int d, int s, Type t1, Type t2, QMove q, CMove c1, CMove c2, int l, int sh) {
        name = n;
        attack = a;
        defence = d;
        stamina = s;
        type1 = t1;
        type2 = t2;
        quickMove = q;
        chargeMove1 = c1;
        chargeMove2 = c2;
        energy = 0;
        level = l;
        shields = sh;
        hp = (int) Math.floor(stamina * cpMultiplier(level));
    }

    public Pokemon() {
        name = "";
        attack = 0;
        defence = 0;
        stamina = 0;
        type1 = null;
        type2 = null;
        quickMove = null;
        chargeMove1 = null;
        chargeMove2 = null;
        energy = 0;
        level = 0;
        shields = 0;
        hp = 0;
    }

    // MODIFIES: this
    // EFFECTS: sets name of pkmn to = newName
    public void setName(String newName) {
        name = newName;
    }

    // REQUIRES: int >= 0
    // MODIFIES: this
    // EFFECTS: sets hp of pkmn to = h
    public void setHp(int h) {
        hp = h;
    }

    // REQUIRES: int > 0
    // MODIFIES: this
    // EFFECTS: sets attack to new value
    public void setAttack(int attack) {
        this.attack = attack;
    }

    // REQUIRES: int > 0
    // MODIFIES: this
    // EFFECTS: sets defence to new value
    public void setDefence(int defence) {
        this.defence = defence;
    }

    // REQUIRES: int >= 0
    // MODIFIES: this
    // EFFECTS: sets stamina to new value
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    // MODIFIES: this
    // EFFECTS: sets Type1 to new type
    public void setType1(Type type1) {
        this.type1 = type1;
    }

    // MODIFIES: this
    // EFFECTS: sets Type2 to new Type
    public void setType2(Type type2) {
        this.type2 = type2;
    }

    // MODIFIES: this
    // EFFECTS: sets qmove of pkmn to = move
    public void setQuickMove(QMove move) {
        quickMove = move;
    }

    // MODIFIES: this
    // EFFECTS: sets cmove1 of pkmn to = move
    public void setChargeMove1(CMove move) {
        chargeMove1 = move;
    }

    // MODIFIES: this
    // EFFECTS: sets cmove2 of pkmn to = move
    public void setChargeMove2(CMove move) {
        chargeMove2 = move;
    }

    // REQUIRES: int >= 0
    // MODIFIES: this
    // EFFECTS: sets energy of pkmn to = e
    public void setEnergy(int e) {
        energy = e;
    }

    // REQUIRES: 78 >= int >= 0
    // MODIFIES: this
    // EFFECTS: sets level of pkmn to = lvl
    public void setLevel(int lvl) {
        level = lvl;
    }

    // REQUIRES: 2 >= int >=0
    // MODIFIES: this
    // EFFECTS: sets # of shields a pkmn has left to = n
    public void setShields(int n) {
        shields = n;
    }

    // EFFECTS: returns name of pkmn
    public String getName() {
        return name;
    }

    // EFFECTS: returns att stat of pkmn
    public int getAttack() {
        return attack;
    }

    // EFFECTS: returns def stat of pkmn
    public int getDefence() {
        return defence;
    }

    // EFFECTS: returns stamina stat of pkmn
    public int getStamina() {
        return stamina;
    }

    // EFFECTS: returns remaining hp of pokemon - NOTE FOR FUTURE CONOR: MAKE THIS MAX HP AND HAVE
    //                                                                   A FLUCTUATING CURRENT HP STAT
    //                                                                   BUT THIS IS FUNCTIONAL FOR NOW
    public int getHp() {
        return hp;
    }

    // EFFECTS: returns primary type of pkmn
    public Type getType1() {
        return type1;
    }

    // EFFECTS: returns secondary type of pkmn
    public Type getType2() {
        return type2;
    }

    // EFFECTS: returns qmove of pkmn
    public QMove getQuickMove() {
        return quickMove;
    }

    // EFFECTS: returns cmove1 of pkmn
    public CMove getChargeMove1() {
        return chargeMove1;
    }

    // EFFECTS: returns cmove2 of pkmn
    public CMove getChargeMove2() {
        return chargeMove2;
    }

    // EFFECTS: returns current energy of pkmn
    public int getEnergy() {
        return energy;
    }

    // EFFECTS: returns lvl of pkmn (actual in-game level is (this.level / 2) + 1 )
    public int getLevel() {
        return level;
    }

    // EFFECTS: returns # of shields left for pkmn
    public int getShields() {
        return shields;
    }

    // EFFECTS: returns cp multiplier for a given level (used for stat calculations)
    //                                                  (wont really be used much til i implement IVs and max CP's)8
    public double cpMultiplier(int lvl) {
        return cpm.getMultiplier(lvl);
    }

    // EFFECTS: outputs true only if obj is a Pokemon with the same name, base stats and type as this, else false.
    //          Does not depend on HP, moves, shields, level, etc.
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pokemon other = (Pokemon) obj;

        return this.name.equals(other.name) && this.attack == other.attack && this.defence == other.defence
                && this.stamina == other.stamina && this.type1 == other.type1 && this.type2 == other.type2;
    }

    // EFFECTS: Outputs a hashcode for this Pokemon based on name, base stats, and types.
    @Override
    public int hashCode() {
        String pokemon = name + attack + defence + stamina + type1 + type2;
        return pokemon.hashCode();
    }

    /*public void addToBattle(Battle battle) {
        if (this.battle == battle) {
            return;
        } else if (battle != null) {
            //this.battle.removePokemon(this);
        }
        this.battle = battle;
        battle.addPokemon(this);
    }

    public void removeFromBattle() {
        battle = null;
    }*/
}
