package model.battleTests;

import model.battle.Battle;
import model.exceptions.NullMoveException;
import model.exceptions.NullTypeException;
import model.pokemon.CMove;
import model.pokemon.Pokemon;
import model.pokemon.QMove;
import model.pokemon.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleTest {
    private Battle battleObj;
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
    private QMove q2 = new QMove("quicky2",13,3,3,fire);
    private CMove c1 = new CMove("charge1",25,-20,fire);
    private CMove c2 = new CMove("charge2",26,-22,water);
    private CMove c3 = new CMove("charge3",27,-21,fire);
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Pokemon pokemon3;
    private Pokemon pokemon4;


    @BeforeEach
    void runBefore() {
        pokemon1 = new Pokemon("p1",100,200,123,fire,water,q1,c1,c2,50,1);
        pokemon2 = new Pokemon("p2",1,4,45,grass,water,q2,c2,c3,10,2);
        pokemon3 = new Pokemon("p3",100,200,123,fire,water,q1,c1,c2,50,1);
        pokemon4 = new Pokemon("p3",100,200,123,fire,water,q1,null,c2,50,1);
        battleObj = new Battle(pokemon1, pokemon2);
    }

    @Test
    void testCreateNull() {
        Battle tempBattle = new Battle();
    }

    @Test
    void testGetPokemon() {
        assertEquals(battleObj.getPokemon1(), pokemon1);
        assertEquals(battleObj.getPokemon2(), pokemon2);
    }

    @Test
    void testSetPokemon() {
        battleObj.setPokemon1(pokemon2);
        battleObj.setPokemon2(pokemon1);
        assertEquals(battleObj.getPokemon1(), pokemon2);
        assertEquals(battleObj.getPokemon2(), pokemon1);
    }

    @Test
    void testWin() {
        assertEquals(battleObj.win(pokemon1, 23), "p1");
        assertEquals(battleObj.win(pokemon2, -66), "p2");
    }

    @Test
    void testTie() {
        assertEquals(battleObj.tie(32), "tie");
    }

    @Test
    void testBattle() {

    }


    @Test
    void testBattleSimulatorWins() {
        assertEquals(battleObj.battle(), pokemon1.getName());
        battleObj.setPokemon1(pokemon2);
        battleObj.setPokemon2(pokemon1);
        assertEquals(battleObj.battle(), pokemon1.getName());
    }

    @Test
    void testBattleSimuatorTie() {
        battleObj.setPokemon1(pokemon3);
        battleObj.setPokemon2(pokemon1);
        assertEquals(battleObj.battle(), "tie");
    }

/*

    @Test
    void testQuickEffect() {
        assertEquals(battleObj.quickEffect(pokemon1,pokemon2), 1);
    }

    @Test
    void testIsQStab() {
        try {
            assertEquals(battleObj.isQStab(pokemon1),1.2);
        } catch (NullTypeException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(battleObj.isQStab(pokemon2),1);
        } catch (NullTypeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDamageQuickMove() throws NullMoveException {
        assertEquals(battleObj.damageQuickMove(pokemon1,pokemon2),151);
    }

    @Test
    void testChargeEffect() {
        assertEquals(battleObj.chargeEffect(pokemon1,pokemon2,c1), 1);
        assertEquals(battleObj.chargeEffect(pokemon1,pokemon2,c2), 0.390625);
    }

    @Test
    void testIsCStab() {
        try {
            assertEquals(battleObj.isCStab(pokemon1,c1),1.2);
        } catch (NullTypeException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(battleObj.isCStab(pokemon2,c1),1);
        } catch (NullTypeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDamageChargeMove() {
        try {
            assertEquals(battleObj.damageChargeMove(pokemon1,pokemon2, c1),376);
        } catch (NullMoveException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testStrongerMove() throws NullMoveException {
        assertEquals(battleObj.strongerMove(pokemon1,pokemon2),pokemon1.getChargeMove1());
        pokemon1.setChargeMove2(c3);
        assertEquals(battleObj.strongerMove(pokemon1,pokemon2),pokemon1.getChargeMove2());
    }

    @Test
    void testCheaperMove() {
        assertEquals(battleObj.cheaperMove(pokemon1),pokemon1.getChargeMove1());
        assertEquals(battleObj.cheaperMove(pokemon2),pokemon2.getChargeMove2());
    }

    @Test
    void testNoShieldDamageStep() throws NullMoveException {
        int hp = pokemon2.getHp() - battleObj.damageQuickMove(pokemon1,pokemon2);
        int energy = pokemon1.getEnergy() + q1.getEnergy();
        assertEquals(battleObj.noShieldDamageStep(pokemon1,pokemon2),1);
        assertEquals(pokemon2.getHp(), hp);
        assertEquals(pokemon1.getEnergy(),energy);
        pokemon1.setEnergy(100);
        pokemon2.setHp(1000);
        CMove move = battleObj.strongerMove(pokemon1,pokemon2);
        hp = 1000 - battleObj.damageChargeMove(pokemon1,pokemon2,move);
        energy = 100 + move.getEnergy();
        assertEquals(battleObj.noShieldDamageStep(pokemon1,pokemon2),0);
        assertEquals(pokemon2.getHp(),hp);
        assertEquals(pokemon1.getEnergy(),energy);
    }

    @Test
    void testShieldDamageStep() throws NullMoveException {
        int hp = pokemon2.getHp() - battleObj.damageQuickMove(pokemon1,pokemon2);
        int energy = pokemon1.getEnergy() + q1.getEnergy();
        assertEquals(battleObj.shieldDamageStep(pokemon1,pokemon2),1);
        assertEquals(pokemon2.getHp(), hp);
        assertEquals(pokemon1.getEnergy(),energy);
        pokemon1.setEnergy(100);
        pokemon2.setHp(1000);
        int shields = pokemon2.getShields();
        CMove move = battleObj.cheaperMove(pokemon1);
        hp = 1000 - battleObj.damageChargeMove(pokemon1,pokemon2,move);
        energy = 100 + move.getEnergy();
        assertEquals(battleObj.shieldDamageStep(pokemon1,pokemon2),0);
        assertEquals(pokemon2.getHp(),hp);
        assertEquals(pokemon1.getEnergy(),energy);
        assertEquals(pokemon2.getShields(),shields - 1);
    }

    @Test
    void testTimeToWin_NoException() {
        int hp = pokemon2.getHp();
        assertEquals(pokemon2.getHp(), hp);
        try {
            assertEquals(battleObj.timeToWin(pokemon1,pokemon2),1);
            System.out.println("pass");
        } catch (NullMoveException e) {
            System.out.println("fail");
        }
        try {
            assertEquals(battleObj.timeToWin(pokemon2,pokemon1),219);
            System.out.println("pass");
        } catch (NullMoveException e) {
            System.out.println("fail");
        }
        try {
            assertEquals(battleObj.timeToWin(pokemon1,pokemon1),38);
            System.out.println("pass");
        } catch (NullMoveException e) {
            System.out.println("fail");
        }
        assertEquals(pokemon2.getHp(), hp);
    }

    @Test
    void testTimeToWin_ExpectedException() {
        try {
            assertEquals(battleObj.timeToWin(pokemon4,pokemon4),38);
            System.out.println("fail");
        } catch (NullMoveException e) {
            System.out.println("fail");
        }
    }*/
}
