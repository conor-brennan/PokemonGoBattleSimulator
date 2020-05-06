package model.battle;

import model.pokemon.*;

public class DamageCalculator {
    private BattlePokemon attacker;
    private BattlePokemon defender;

    public static int CHARGE_MOVE_DURATION = 0;

    public DamageCalculator(Pokemon attacker, Pokemon defender) {
        this.attacker = new BattlePokemon(attacker);
        this.defender = new BattlePokemon(defender);
    }

    public BattlePokemon getAttacker() {
        return attacker;
    }

    public BattlePokemon getDefender() {
        return defender;
    }

    // REQUIRES: two pokemon with no empty fields
    // EFFECTS:  determines how long p1 will take to beat p2 by using cheaper moves to get rid of shields,
    //           then stronger moves to finish once shields are depleted
    public int timeToWin() {
        int time = 0;
        while (defender.getHp() > 0) {
            if (defender.getShields() > 0) {
                time += shieldDamageStep();
            } else {
                time += noShieldDamageStep();
            }
        }
        return time;
    }

    // REQUIRES: two pokemon with no empty fields, and p2 to have >= 1 shield(s)
    // MODIFIES: hp & shields of p2, energy and time of p1
    // EFFECTS:  returns time taken by p1 doing dmg to p2 each step and reduces hp of p2 by appropriate amount
    //           if charge move is used, reduce shields of p2 by 1
    // TODO: nullMoveException
    public int shieldDamageStep() {
        CMove cmove = cheaperMove();
        QMove qmove = attacker.getQuickMove();
        if (attacker.getEnergy() + cmove.getEnergy() >= 0) {
            doAttack(damageMove(cmove), cmove.getEnergy());
            defender.shieldUse();
            return CHARGE_MOVE_DURATION;
        } else {
            doAttack(damageMove(qmove), qmove.getEnergy());
            return qmove.getDuration();
        }
    }

    // REQUIRES: two pokemon with no empty fields, and p2 to have >= 1 shield(s)
    // MODIFIES: hp & shields of p2, energy and time of p1
    // EFFECTS:  returns time taken by p1 doing dmg to p2 each step and reduces hp of p2 by appropriate amount
    //           if charge move is used, reduce shields of p2 by 1
    // TODO: nullMoveException
    public int noShieldDamageStep() {
        CMove cmove = strongerMove();
        QMove qmove = attacker.getQuickMove();
        if (attacker.getEnergy() + cmove.getEnergy() >= 0) {
            doAttack(damageMove(cmove), cmove.getEnergy());
            return CHARGE_MOVE_DURATION;
        } else {
            doAttack(damageMove(qmove), qmove.getEnergy());
            return qmove.getDuration();
        }
    }

    // MODIFIES: this
    // EFFECTS: subtracts dmg from hp of pokemon 2 and adds e to energy of pokemon1
    public void doAttack(int dmg, int e) {
        attacker.energyUpdate(e);
        defender.takeDamage(dmg);
    }

    // REQUIRES: non-zero defence
    // EFFECTS: calculates damage done on pokemon2 by pokemon1 for a given move (charge or quick, doesn't matter)
    public int damageMove(Move move) {
        int result;
        double temp = 0.5 * move.getDamage() * attacker.getAttack() / defender.getDefence();
        temp = temp * isStab(move) * moveEffect(move);
        result = (int) Math.floor(temp) + 1;
        return result;
    }

    // EFFECTS: determines which charge move will require less energy to use
    //          (the signs look funny at first because charge moves count as negative energy)
    public CMove cheaperMove() {
        CMove move1 = attacker.getChargeMove1();
        CMove move2 = attacker.getChargeMove2();
        if (move1.getEnergy() > move2.getEnergy()) {
            return move1;
        } else {
            return move2;
        }
    }

    // EFFECTS: determines which charge move will do more damage when un-shielded
    public CMove strongerMove() {
        CMove charge1 = attacker.getChargeMove1();
        CMove charge2 = attacker.getChargeMove2();
        if (damageMove(charge1) > damageMove(charge2)) {
            return charge1;
        } else {
            return charge2;
        }
    }

    // EFFECTS: returns 1.2 for Same Type Attack Bonus (STAB), else 1
    public double isStab(Move move) {
        Type type1 = attacker.getType1();
        Type type2 = attacker.getType2();
        Type moveType = move.getType();
        if (moveType.getName().equals(type1.getName()) || moveType.getName().equals(type2.getName())) {
            return 1.2;
        } else {
            return 1;
        }
    }

    // EFFECTS: determines effectiveness multiplier for a given move based on the type of the move and the two types
    //          of the defending pokemon (pokemon2)
    public double moveEffect(Move move) {
        Type moveType = move.getType();
        Type defenderType1 = defender.getType1();
        Type defenderType2 = defender.getType2();
        double result = defenderType1.typeEffect(moveType) * defenderType2.typeEffect(moveType);
        return result;
    }

}
