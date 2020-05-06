package model.pokemon;

import model.battle.Battle;

public class BattlePokemon extends Pokemon {

    public BattlePokemon(Pokemon pokemon) {
        super(pokemon.getName(),pokemon.getAttack(),pokemon.getDefence(),pokemon.getStamina(),pokemon.getType1(),
                pokemon.getType2(),pokemon.getQuickMove(),pokemon.getChargeMove1(),pokemon.getChargeMove2(),
                pokemon.getLevel(),pokemon.getShields());
    }

    // MODIFIES: this
    // EFFECTS: uses a shield, reducing the number available by 1
    public void shieldUse() {
        this.shields = shields - 1;
    }

    // MODIFIES: this
    // EFFECTS: takes damage, resulting in the hp of the BattlePokemon dropping by the given dmg
    public void takeDamage(int dmg) {
        this.hp = hp - dmg;
    }

    // MODIFIES: this
    // EFFECTS: updates the energy of the BattlePokemon according to the energy of the move that was just used
    public void energyUpdate(int e) {
        this.energy = energy + e;
    }

    /*public void addToBattle(Battle battle) {
        if (this.battle == battle) {
            return;
        } else if (battle != null) {
            //this.battle.removePokemon(this);
        }
        this.battle = battle;
        //battle.addPokemon(this); // TODO : implement some relationship
    }

    public void removeFromBattle() {
        battle = null;
    }*/
}
