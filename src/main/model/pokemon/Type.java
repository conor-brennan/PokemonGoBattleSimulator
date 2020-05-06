package model.pokemon;

import java.util.ArrayList;

public class Type {
    private String name;
    private ArrayList<String> weaknesses;
    private ArrayList<String> resistances;
    private ArrayList<String> immunities;

    public Type(String n, ArrayList<String> w, ArrayList<String> r, ArrayList<String> i) {
        name = n;
        weaknesses = w;
        resistances = r;
        immunities = i;
    }

    // EFFECTS: creates new blank type (usually to be modified later)
    public Type() {
        name = "";
        weaknesses = null;
        resistances = null;
        immunities = null;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getWeaknesses() {
        return weaknesses;
    }

    public ArrayList<String> getResistances() {
        return resistances;
    }

    public ArrayList<String> getImmunities() {
        return immunities;
    }

    public void setName(String n) {
        name = n;
    }

    public void setImmunities(ArrayList<String> imm) {
        immunities = imm;
    }

    public void setResistances(ArrayList<String> res) {
        resistances = res;
    }

    public void setWeaknesses(ArrayList<String> weak) {
        weaknesses = weak;
    }

    // MODIFIES: this
    // EFFECTS: adds type to weaknesses if not present in weaknesses, resistances, or immunities
    public void addWeakness(String type) {
        if (!weaknesses.contains(type) && !resistances.contains(type) && !immunities.contains(type)) {
            weaknesses.add(type);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes type from weaknesses
    public void removeWeakness(String type) {
        weaknesses.remove(type);
    }

    // MODIFIES: this
    // EFFECTS: adds type to resistances if not present in weaknesses, resistances, or immunities
    public void addResistance(String type) {
        if (!weaknesses.contains(type) && !resistances.contains(type) && !immunities.contains(type)) {
            resistances.add(type);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes type from resistance
    public void removeResistance(String type) {
        resistances.remove(type);
    }

    // MODIFIES: this
    // EFFECTS: adds type to immunities if not present in weaknesses, resistances, or immunities
    public void addImmunity(String type) {
        if (!weaknesses.contains(type) && !resistances.contains(type) && !immunities.contains(type)) {
            immunities.add(type);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes type from resistance
    public void removeImmunity(String type) {
        immunities.remove(type);
    }

    // EFFECTS: returns multiplier for given attacking and defending types
    public double typeEffect(Type att) {
        if (weaknesses.contains(att.getName())) {
            return 1.6;
        } else if (resistances.contains(att.getName())) {
            return 0.625;
        } else if (immunities.contains(att.getName())) {
            return 0.625 * 0.625;
        } else {
            return 1;
        }
    }

    // EFFECTS: looks at names, lists of weaknesses, lists of resistances, and lists of immunities to determine if
    //          two Types are equal. Returns true if they match, otherwise false.
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Type other = (Type) obj;

        return this.name.equals(other.name) && this.weaknesses == other.weaknesses
                && this.resistances == other.resistances && this.immunities == other.immunities;
    }

    // EFFECTS: Outputs a hashcode for this Type based on name and type match-ups
    @Override
    public int hashCode() {
        String type = name + weaknesses + resistances + immunities;
        return type.hashCode();
    }
}
