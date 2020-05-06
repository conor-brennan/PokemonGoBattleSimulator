package model.pokemonTests;

import model.pokemon.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class TypeTest {
    Type fire;
    Type grass;
    Type electric;
    Type ground;
    Type poison;
    ArrayList<String> firew;

    @BeforeEach
    void runBefore() {
        firew = new ArrayList<>(Arrays.asList("water", "ground", "rock"));
        ArrayList<String> grassw = new ArrayList<>(Arrays.asList("fire", "flying"));
        ArrayList<String> grassr = new ArrayList<>(Arrays.asList("electric", "water"));
        ArrayList<String> electricw = new ArrayList<>(Collections.singletonList("ground"));
        ArrayList<String> groundw = new ArrayList<>(Arrays.asList("grass", "water"));
        ArrayList<String> groundr = new ArrayList<>(Arrays.asList("poison", "rock"));
        ArrayList<String> groundi = new ArrayList<>(Collections.singletonList("electric"));
        ArrayList<String> poisonw = new ArrayList<>(Collections.singletonList("ground"));
        ArrayList<String> poisonr = new ArrayList<>(Arrays.asList("fighting", "bug"));

        fire = new Type("fire", firew, null, null);
        grass = new Type("grass",grassw, grassr, null);
        electric = new Type("electric", electricw, null, null);
        ground = new Type("ground", groundw, groundr, groundi);
        poison = new Type("poison", poisonw, poisonr, null);
    }

    @Test
    void testCreate() {
        Type t1 = new Type();
        Type t2 = new Type("",null,null,null);
    }

    @Test
    void testSetName() {
        fire.setName("not fire");
        assertEquals(fire.getName(), "not fire");
    }

    @Test
    void testSetLists() {
        fire.setImmunities(grass.getImmunities());
        fire.setResistances(grass.getResistances());
        fire.setWeaknesses(grass.getWeaknesses());
        assertEquals(fire.getWeaknesses(),grass.getWeaknesses());
        assertEquals(fire.getImmunities(),grass.getImmunities());
        assertEquals(fire.getResistances(),grass.getResistances());
    }

    @Test
    void testAddAndRemoveWeakness() {
        ArrayList<String> original = ground.getWeaknesses();
        ArrayList<String> updated = new ArrayList<>(Arrays.asList("water", "ice"));
        ground.addWeakness("grass");
        ground.addWeakness("rock");
        ground.addWeakness("electric");
        assertEquals(ground.getWeaknesses(), original);
        ground.addWeakness("ice");
        ground.removeWeakness("grass");
        assertEquals(ground.getWeaknesses(), updated);
    }

    @Test
    void testAddAndRemoveResistances() {
        ArrayList<String> original = ground.getResistances();
        ArrayList<String> updated = new ArrayList<>(Arrays.asList("poison", "ghost"));
        ground.addResistance("poison");
        ground.addResistance("electric");
        ground.addResistance("grass");
        assertEquals(ground.getResistances(), original);
        ground.addResistance("ghost");
        ground.removeResistance("rock");
        assertEquals(ground.getResistances(), updated);
    }

    @Test
    void testAddAndRemoveImmunities() {
        ArrayList<String> original = ground.getImmunities();
        ArrayList<String> updated = new ArrayList<>(Arrays.asList("ghost", "crab"));
        ground.addImmunity("electric");
        ground.addImmunity("poison");
        ground.addImmunity("grass");
        assertEquals(ground.getImmunities(), original);
        ground.addImmunity("ghost");
        ground.addImmunity("crab");
        ground.removeImmunity("electric");
        assertEquals(ground.getImmunities(), updated);
    }

    @Test
    void testTypeEffect() {
        assertEquals(ground.typeEffect(poison), 0.625);
        assertEquals(ground.typeEffect(electric), 0.625 * 0.625);
        assertEquals(ground.typeEffect(grass), 1.6);
        assertEquals(ground.typeEffect(fire), 1);
    }

    @Test
    void testEqualsAndHash() {
        assertTrue(fire.equals(fire));
        assertFalse(poison.equals(3));
        assertFalse(fire.equals(poison));
        Type fire2 = new Type("fire", firew, null, null);
        assertTrue(fire.equals(fire2));
        assertEquals(fire.hashCode(), 144873285);
    }
}
