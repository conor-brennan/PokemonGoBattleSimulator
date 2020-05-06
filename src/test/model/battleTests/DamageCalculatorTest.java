package model.battleTests;

import model.battle.*;
import model.pokemon.*;
import java.util.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DamageCalculatorTest {
    private DamageCalculator damageCalculator;
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
    private Pokemon pokemon3;
    private Pokemon pokemon4;


    @BeforeEach
    void runBefore() {
        pokemon1 = new Pokemon("p1", 100, 200, 123, fire, water, q1, c1, c2, 50, 1);
        pokemon2 = new Pokemon("p2", 40, 4, 45, grass, water, q2, c3, c2, 60, 2);
        pokemon3 = new Pokemon("p3", 100, 90, 123, fire, water, q1, c1, c2, 50, 1);
        pokemon4 = new Pokemon("p3", 100, 200, 123, fire, fire, q1, null, c2, 50, 1);
        damageCalculator = new DamageCalculator(pokemon1, pokemon2);
    }

    @Test
    void testCreation() {
        BattlePokemon tester = new BattlePokemon(pokemon1);
        assertFalse(pokemon1.equals(damageCalculator.getAttacker()));
        assertFalse(pokemon2.equals(damageCalculator.getDefender()));
        assertEquals(tester.getClass(), damageCalculator.getAttacker().getClass());
        assertEquals(tester.getClass(), damageCalculator.getDefender().getClass());
    }

    @Test
    void testMoveEffect() {
        assertEquals(damageCalculator.moveEffect(q2),1);
        DamageCalculator tester = new DamageCalculator(pokemon1, pokemon4);
        assertEquals(tester.moveEffect(c2), 1.6 * 1.6, 0.000001);
    }

    @Test
    void testIsStab() {
        assertEquals(damageCalculator.isStab(q1),1.2);
        assertEquals(damageCalculator.isStab(q2), 1);
        DamageCalculator tester = new DamageCalculator(pokemon4, pokemon3);
        assertEquals(damageCalculator.isStab(q1), 1.2);
    }

    @Test
    void testStrongerMove() {
        assertEquals(damageCalculator.strongerMove(),c1);
        DamageCalculator tester = new DamageCalculator(pokemon2, pokemon4);
        assertEquals(tester.strongerMove(),c2);
    }

    @Test
    void testCheaperMove() {
        assertEquals(damageCalculator.cheaperMove(),c1);
        DamageCalculator tester = new DamageCalculator(pokemon2, pokemon3);
        assertEquals(tester.cheaperMove(),c2);
    }

    @Test
    void testDamageMove() {
        assertEquals(damageCalculator.damageMove(q1), 151);
    }

    @Test
    void testDoAttack() {
        int energy = damageCalculator.getAttacker().getEnergy();
        int hp = damageCalculator.getDefender().getHp();
        assertEquals(damageCalculator.getAttacker().getEnergy(), energy);
        assertEquals(damageCalculator.getDefender().getHp(), hp);
        damageCalculator.doAttack(10, 5);
        assertEquals(damageCalculator.getAttacker().getEnergy(), energy + 5);
        assertEquals(damageCalculator.getDefender().getHp(), hp - 10);
    }

    @Test
    void testNoShieldDamageStep() {
        assertEquals(damageCalculator.noShieldDamageStep(),q1.getDuration());
        damageCalculator.getAttacker().energyUpdate(100);
        assertEquals(damageCalculator.noShieldDamageStep(),DamageCalculator.CHARGE_MOVE_DURATION);
    }

    @Test
    void testShieldDamageStep() {
        assertEquals(damageCalculator.shieldDamageStep(),q1.getDuration());
        damageCalculator.getAttacker().energyUpdate(100);
        assertEquals(damageCalculator.shieldDamageStep(),DamageCalculator.CHARGE_MOVE_DURATION);
    }

    @Test
    void testTimeToWin() {
        damageCalculator = new DamageCalculator(pokemon2, pokemon3);
        assertEquals(damageCalculator.timeToWin(),24);
    }

}
