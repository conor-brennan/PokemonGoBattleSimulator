package model.pokemonTests;

import model.battle.*;
import model.pokemon.*;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BattlePokemonTest {
    BattlePokemon battlePokemon;
    private ArrayList<String> firew = new ArrayList<>(Arrays.asList("water", "ground", "rock"));
    private ArrayList<String> firer = new ArrayList<>(Arrays.asList("fire", "grass", "fairy"));
    private ArrayList<String> firei = new ArrayList<>(Collections.singletonList(""));
    private ArrayList<String> grassw = new ArrayList<>(Arrays.asList("fire", "ice", "flying"));
    private ArrayList<String> grassr = new ArrayList<>(Arrays.asList("grass", "water", "electric"));
    private ArrayList<String> grassi = new ArrayList<>(Collections.singletonList(""));
    private ArrayList<String> waterw = new ArrayList<>(Arrays.asList("grass", "electric"));
    private ArrayList<String> waterr = new ArrayList<>(Arrays.asList("water", "fire", "steel"));
    private ArrayList<String> wateri = new ArrayList<>(Collections.singletonList(""));
    private Type fire = new Type("fire", firew, firer, firei);
    private Type water = new Type("water", waterw, waterr, wateri);
    private Type grass = new Type("grass", grassw, grassr, grassi);
    private QMove q1 = new QMove("quicky",10,1,1,fire);
    private QMove q2 = new QMove("quicky2",13,22,3,grass);
    private CMove c1 = new CMove("charge1",25,-20,fire);
    private CMove c2 = new CMove("charge2",26,-22,water);
    private CMove c3 = new CMove("charge3",27,-23,fire);
    private Pokemon pokemon1;
    private Pokemon pokemon2;



    @BeforeEach
    void runBefore() {
        pokemon1 = new Pokemon("p1", 100, 200, 123, fire, water, q1, c1, c2, 50, 1);
        pokemon2 = new Pokemon("p2", 40, 4, 45, grass, water, q2, c3, c2, 60, 2);
        battlePokemon = new BattlePokemon(pokemon1);
    }

    @Test
    void testCreation() {
        assertEquals(battlePokemon.getName(),pokemon1.getName());
        assertEquals(battlePokemon.getAttack(),pokemon1.getAttack());
        assertEquals(battlePokemon.getDefence(),pokemon1.getDefence());
        assertEquals(battlePokemon.getStamina(),pokemon1.getStamina());
        assertEquals(battlePokemon.getHp(),pokemon1.getHp());
        assertEquals(battlePokemon.getType1(),pokemon1.getType1());
        assertEquals(battlePokemon.getType2(),pokemon1.getType2());
        assertEquals(battlePokemon.getQuickMove(),pokemon1.getQuickMove());
        assertEquals(battlePokemon.getChargeMove1(),pokemon1.getChargeMove1());
        assertEquals(battlePokemon.getChargeMove2(),pokemon1.getChargeMove2());
        assertEquals(battlePokemon.getEnergy(),pokemon1.getEnergy());
        assertEquals(battlePokemon.getLevel(),pokemon1.getLevel());
        assertEquals(battlePokemon.getShields(),pokemon1.getShields());
        assertFalse(pokemon1.equals(battlePokemon));

    }

    @Test
    void testShieldUse() {
        int shields = battlePokemon.getShields();
        assertEquals(battlePokemon.getShields(), shields);
        battlePokemon.shieldUse();
        assertEquals(battlePokemon.getShields(), shields - 1);
    }

    @Test
    void testTakeDamage() {
        int hp = battlePokemon.getHp();
        int dmg = 5;
        assertEquals(battlePokemon.getHp(), hp);
        battlePokemon.takeDamage(dmg);
        assertEquals(battlePokemon.getHp(), hp - dmg);
    }

    @Test
    void testEnergyUpdate() {
        int energy = battlePokemon.getEnergy();
        int e = 5;
        assertEquals(battlePokemon.getEnergy(), energy);
        battlePokemon.energyUpdate(e);
        assertEquals(battlePokemon.getEnergy(), energy + e);
    }

}
