package model.battle;

import model.pokemon.*;
import model.exceptions.NullMoveException;
import model.exceptions.NullTypeException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Battle {
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private ArrayList<Pokemon> pokemons;

    private static int MAX_SIZE = 2;

    public Battle(Pokemon p1, Pokemon p2) {
        pokemon1 = p1;
        pokemon2 = p2;
        pokemons = new ArrayList<>();
    }

    public Battle() {
        pokemon1 = null;
        pokemon2 = null;
        pokemons = new ArrayList<>();
    }

    // EFFECTS: returns first pokemon
    public Pokemon getPokemon1() {
        return pokemon1;
    }

    // EFFECTS: returns second pokemon
    public Pokemon getPokemon2() {
        return pokemon2;
    }

    // MODIFIES: this
    // EFFECTS: set first pokemon to be newP
    public void setPokemon1(Pokemon newP) {
        pokemon1 = newP;
    }

    // MODIFIES: this
    // EFFECTS: set second pokemon to be newP
    public void setPokemon2(Pokemon newP) {
        pokemon2 = newP;
    }

    // TODO: Test and comment for these
    public void addPokemon(Pokemon pokemon) {
        if (pokemons.size() <= MAX_SIZE) { //&& !pokemons.contains(pokemon)) {
            pokemons.add(pokemon);
        }
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    // EFFECTS: Creates two DamageCalculators, one to check p1 vs p2 and the other for p2 vs p1, and is used to
    //          calculate the time it takes them to knock each other out. This is used to determine the victor
    //          (lowest time), and the appropriate method is called
    public String battle() {
        DamageCalculator battle1 = new DamageCalculator(pokemon1, pokemon2);
        DamageCalculator battle2 = new DamageCalculator(pokemon2, pokemon1);
        int p1Time = battle1.timeToWin();
        int p2Time = battle2.timeToWin();
        if (p1Time < p2Time) {
            return win(pokemon1, p1Time);
        } else if (p2Time < p1Time) {
            return win(pokemon2, p2Time);
        } else {
            return tie(p1Time);
        }
    }

    // EFFECTS: Outputs a victory message with the results of the battle and
    //          returns the name of the winning Pokemon
    public String win(Pokemon pokemon, int winningTime) {
        System.out.println(pokemon.getName() + " won in " + winningTime + "s!");
        return pokemon.getName() + " won in " + winningTime + "s!";
    }

    // EFFECTS: Outputs a message indicating the game was tied after a given time and
    //          returns "tie"
    public String tie(int time) {
        System.out.println("The battle was tied after " + time + "s!");
        return "The battle was tied after " + time + "s!";
    }






    /*
    // REQUIRES: two pokemon with no empty fields
    // EFFECTS: determines winner based on quickest time to faint the other pokemon
    public String battle(Pokemon p1, Pokemon p2) {
        double timeP1 = 0;
        double timeP2 = 0;
        try {
            timeP1 = timeToWin(p1, p2);
            timeP2 = timeToWin(p2, p1);
        } catch (NullMoveException e) {
            return "Pokemon needs move";
        }
        if (timeP1 > timeP2) {
            return "p2";
        } else if (timeP1 < timeP2) {
            return "p1";
        } else {
            return "tie";
        }
    }

    // REQUIRES: two pokemon with no empty fields
    // EFFECTS: determines how long p1 will take to beat p2 using cheaper moves to get rid of shields,
    //          then stronger moves to finish once shields are depleted
    public double timeToWin(Pokemon p1, Pokemon p2) throws NullMoveException {
        int resetHp = p2.getHp();
        int time = 0;
        while (p2.getHp() > 0) {
            if (p2.getShields() > 0) {
                time = time + shieldDamageStep(p1, p2);
            } else if (p2.getShields() == 0) {
                time = time + noShieldDamageStep(p1, p2);
            }

        }
        p2.setHp(resetHp);
        return time;
    }

    // REQUIRES: two pokemon with no empty fields, and p2 to have >= 1 shield(s)
    // MODIFIES: hp & shields of p2, energy and time of p1
    // EFFECTS: returns time taken by p1 doing dmg to p2 each step and reduces hp of p2 by appropriate amount
    //          if charge move is used, reduce shields of p2 by 1
    public int shieldDamageStep(Pokemon p1, Pokemon p2) throws NullMoveException {
        CMove cmove = cheaperMove(p1);
        QMove qmove = p1.getQuickMove();
        if (p1.getEnergy() + cmove.getEnergy() >= 0) {
            int dmg = damageChargeMove(p1, p2, cmove);
            p2.setHp(p2.getHp() - dmg);
            p1.setEnergy(p1.getEnergy() + cmove.getEnergy());
            p2.setShields(p2.getShields() - 1);
            return 0;
        } else {
            int dmg = damageQuickMove(p1, p2);
            p2.setHp(p2.getHp() - dmg);
            p1.setEnergy(p1.getEnergy() + qmove.getEnergy());
            return qmove.getDuration();
        }
    }

    // REQUIRES: p2 to have 0 shields
    // MODIFIES: hp of p2, energy & time of p1
    // EFFECTS: returns time taken by p1 doing dmg to p2 each step and reduces hp of p2 by appropriate amount
    public int noShieldDamageStep(Pokemon p1, Pokemon p2) throws NullMoveException {
        CMove cmove = strongerMove(p1, p2);
        QMove qmove = p1.getQuickMove();
        if (p1.getEnergy() + cmove.getEnergy() >= 0) {
            int dmg = damageChargeMove(p1, p2, cmove);
            p2.setHp(p2.getHp() - dmg);
            p1.setEnergy(p1.getEnergy() + cmove.getEnergy());
            return 0;
        } else {
            int dmg = damageQuickMove(p1, p2);
            p2.setHp(p2.getHp() - dmg);
            p1.setEnergy(p1.getEnergy() + qmove.getEnergy());
            return qmove.getDuration();
        }
    }

    // REQUIRES: Pokemon with 2 charge moves with non-zero costs
    // EFFECTS: returns the charge move that has the lowest energy cost
    public CMove cheaperMove(Pokemon p) {
        CMove move1 = p.getChargeMove1();
        CMove move2 = p.getChargeMove2();
        if (move1.getEnergy() > move2.getEnergy()) {
            return move1;
        } else {
            return move2;
        }
    }

    // REQUIRES: p1 to have 2 charge moves
    // EFFECTS: returns charge move that does more dmg
    public CMove strongerMove(Pokemon p1, Pokemon p2) throws NullMoveException {
        int charge1 = damageChargeMove(p1, p2, p1.getChargeMove1());
        int charge2 = damageChargeMove(p1, p2, p1.getChargeMove2());
        if (charge1 > charge2) {
            return p1.getChargeMove1();
        } else {
            return p1.getChargeMove2();
        }
    }

    // EFFECTS: returns dmg done by charge move
    public int damageChargeMove(Pokemon p1, Pokemon p2, CMove c) throws NullMoveException {
        int result;
        double temp;
        if (c == null) {
            throw new NullMoveException();
        }
        try {
            temp = 0.5 * c.getDamage() * p1.getAttack() / p2.getDefence() * isCStab(p1, c) * chargeEffect(p1, p2, c);
        } catch (NullTypeException e) {
            e.printStackTrace();
            temp = 0.5 * c.getDamage() * p1.getAttack() / p2.getDefence() * chargeEffect(p1, p2, c);
        }
        result = (int) Math.floor(temp) + 1;
        return result;
    }

    // EFFECTS: returns 1.2 if charge move is stab (SameTypeAttackBonus), else returns 1
    public double isCStab(Pokemon p, CMove c) throws NullTypeException {
        Type type1 = p.getType1();
        Type type2 = p.getType2();
        Type moveType = c.getType();
        if (type1 == null || type2 == null) {
            throw new NullTypeException();
        }
        if (moveType.getName().equals(type1.getName()) || moveType.getName().equals(type2.getName())) {
            return 1.2;
        } else {
            return 1;
        }
    }

    // EFFECTS: returns effectiveness multiplier for incoming charge move
    public double chargeEffect(Pokemon p1, Pokemon p2, CMove c) {
        double result;
        Type moveType = c.getType();
        Type p2Type1 = p2.getType1();
        Type p2Type2 = p2.getType2();
        result = p2Type1.typeEffect(moveType) * p2Type2.typeEffect(moveType);
        return result;
    }

    // EFFECTS: returns quick move damage done on p2 by p1
    public int damageQuickMove(Pokemon p1, Pokemon p2) throws NullMoveException {
        int result;
        QMove move = p1.getQuickMove();
        double temp;
        if (move == null) {
            throw new NullMoveException();
        }
        try {
            temp = 0.5 * move.getDamage() * p1.getAttack() / p2.getDefence() * isQStab(p1) * quickEffect(p1, p2);
        } catch (NullTypeException e) {
            e.printStackTrace();
            temp = 0.5 * move.getDamage() * p1.getAttack() / p2.getDefence() * quickEffect(p1, p2);
        }
        result = (int) Math.floor(temp) + 1;
        return result;
    }

    // REQUIRES: Pokemon with 1 or 2 types (no more no less)
    // EFFECTS: returns 1.2 multiplier if STAB, else returns 1
    public double isQStab(Pokemon p) throws NullTypeException {
        Type type1 = p.getType1();
        Type type2 = p.getType2();
        QMove move = p.getQuickMove();
        Type moveType = move.getType();
        if (type1 == null || type2 == null) {
            throw new NullTypeException();
        }
        if (moveType.getName().equals(type1.getName()) || moveType.getName().equals(type2.getName())) {
            return 1.2;
        } else {
            return 1;
        }
    }

    // EFFECTS: returns effectiveness multiplier for incoming quick move
    public double quickEffect(Pokemon p1, Pokemon p2) {
        double result = 1;
        QMove move = p1.getQuickMove();
        Type moveType = move.getType();
        Type p2Type1 = p2.getType1();
        Type p2Type2 = p2.getType2();
        result = result * p2Type1.typeEffect(moveType) * p2Type2.typeEffect(moveType);
        return result;
    }

    public void removePokemon(Pokemon pokemon) {
        if (!pokemons.contains(pokemon)) {
            return;
        }
        pokemons.remove(pokemon);
        pokemon.removeFromBattle();
    }

    public void addPokemon(Pokemon pokemon) {
        if (pokemons.contains(pokemon)) {
            return;
        } else {
            pokemons.add(pokemon);
            pokemon.addToBattle(this);
        }
    }*/
}
