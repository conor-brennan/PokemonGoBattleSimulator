package model.pokemonTests;

import model.exceptions.NullMoveException;
import model.pokemon.CMove;
import model.pokemon.Pokemon;
import model.pokemon.QMove;
import model.pokemon.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {
    private Type fire = new Type("fire",null,null,null);
    private Type water = new Type("water",null,null,null);
    private Type grass = new Type("grass",null,null,null);
    private QMove q1 = new QMove("quicky",10,1,1,fire);
    private QMove q2 = new QMove("quicky2",13,3,3,water);
    private CMove c1 = new CMove("charge1",25,-20,fire);
    private CMove c2 = new CMove("charge2",26,-22,water);
    private CMove c3 = new CMove("charge3",27,-21,grass);
    private Pokemon pokemon1;
    private Pokemon pokemon2;

    @BeforeEach
    void runBefore() {
        pokemon1 = new Pokemon("azu",1,3,5,fire,water, q1, c1, c2, 23, 2);
        pokemon2 = new Pokemon("sdf",2,3,5,fire,water, q1, c1, c2, 23, 2);
    }

    @Test
    void testCreateBlank() {
        Pokemon tempPoke = new Pokemon();
        Pokemon tempPoke2 = new Pokemon("",0,0,0,null,null,null,null,null,0,0);
    }

    @Test
    void testGetName() {
        assertEquals(pokemon1.getName(), "azu");
    }

    @Test
    void testGetAttack() {
        assertEquals(pokemon1.getAttack(), 1);
    }

    @Test
    void testGetDefence() {
        assertEquals(pokemon1.getDefence(), 3);
    }

    @Test
    void testGetStamina() {
        assertEquals(pokemon1.getStamina(), 5);
    }

    @Test
    void testGetLevel() {
        assertEquals(pokemon1.getLevel(), 23);
    }

    @Test
    void testCPMultiplier() {
        assertEquals(pokemon1.cpMultiplier(44), 0.64065295);
    }

    @Test
    void testGetHp() {
        int hp = (int) Math.floor(pokemon1.cpMultiplier(pokemon1.getLevel()) * pokemon1.getStamina());
        assertEquals(pokemon1.getHp(), hp);
    }

    @Test
    void testGetType1() {
        assertEquals(pokemon1.getType1(), fire);
    }

    @Test
    void testGetType2() {
        assertEquals(pokemon1.getType2(), water);
    }

    @Test
    void testGetQMove() {
        assertEquals(pokemon1.getQuickMove(), q1);
    }

    @Test
    void testGetCMove1() {
        assertEquals(pokemon1.getChargeMove1(), c1);
    }

    @Test
    void testGetCMove2() {
        assertEquals(pokemon1.getChargeMove2(), c2);
    }

    @Test
    void testGetEnergy() {
        assertEquals(pokemon1.getEnergy(), 0);
    }

    @Test
    void testGetShields() {
        assertEquals(pokemon1.getShields(), 2);
    }

    @Test
    void testSetName() {
        pokemon1.setName("charm");
        assertEquals(pokemon1.getName(), "charm");
    }

    @Test
    void testSetAttack() throws NullMoveException {
        pokemon1.setAttack(1045);
        assertEquals(pokemon1.getAttack(), 1045);
    }

    @Test
    void testSetDefence() throws NullMoveException {
        pokemon1.setDefence(1045);
        assertEquals(pokemon1.getDefence(), 1045);
    }

    @Test
    void testSetStamina() throws NullMoveException {
        pokemon1.setStamina(1045);
        assertEquals(pokemon1.getStamina(), 1045);
    }

    @Test
    void testSetType1() {
        pokemon1.setType1(grass);
        assertEquals(pokemon1.getType1(), grass);
    }

    @Test
    void testSetType2() {
        pokemon1.setType2(grass);
        assertEquals(pokemon1.getType2(), grass);
    }

    @Test
    void testSetHp() throws NullMoveException {
        pokemon1.setHp(1045);
        assertEquals(pokemon1.getHp(), 1045);
    }

    @Test
    void testSetQMove() {
        pokemon1.setQuickMove(q2);
        assertEquals(pokemon1.getQuickMove(), q2);
    }

    @Test
    void testSetCMove1() {
        pokemon1.setChargeMove1(c3);
        assertEquals(pokemon1.getChargeMove1(), c3);
    }

    @Test
    void testSetCMove2() {
        pokemon1.setChargeMove2(c1);
        assertEquals(pokemon1.getChargeMove1(), c1);
    }

    @Test
    void testSetEnergy() {
        pokemon1.setEnergy(102);
        assertEquals(pokemon1.getEnergy(),102);
    }

    @Test
    void testSetLevel() throws NullMoveException {
        pokemon1.setLevel(12);
        assertEquals(pokemon1.getLevel(),12);
    }

    @Test
    void testSetShields() throws NullMoveException {
        pokemon1.setShields(1);
        assertEquals(pokemon1.getShields(),1);
    }

    @Test
    void testEqualsandHash() {
        assertTrue(pokemon1.equals(pokemon1));
        assertFalse(pokemon1.equals(fire));
        assertFalse(pokemon1.equals(pokemon2));
        Pokemon test1 = new Pokemon("azu",1,3,5,fire,water, q2, c3, c2, 23, 2);
        Pokemon test2 = new Pokemon("azu",1,3,5,fire,water, q1, c1, c2, 23, 2);
        assertTrue(pokemon1.equals(test1));
        assertTrue(pokemon1.equals(test2));
        assertEquals(pokemon1.hashCode(),1194352965);
    }

}
