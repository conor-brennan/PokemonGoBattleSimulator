package model.pokemonTests;

import model.pokemon.QMove;
import model.pokemon.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QMoveTest {
    private ArrayList<String> firew = new ArrayList<>(Arrays.asList("water", "ground", "rock"));
    private ArrayList<String> firer = new ArrayList<>(Arrays.asList("fire", "grass", "fairy"));
    private ArrayList<String> firei = new ArrayList<>(Collections.singletonList(""));
    private ArrayList<String> grassw = new ArrayList<>(Arrays.asList("fire", "ice", "flying"));
    private ArrayList<String> grassr = new ArrayList<>(Arrays.asList("grass", "water", "electric"));
    private ArrayList<String> grassi = new ArrayList<>(Collections.singletonList(""));
    private Type grass = new Type("grass", grassw, grassr, grassi);
    private Type fire = new Type("fire", firew, firer, firei);
    QMove qMove = new QMove("fire move",22,5,4,fire);

    @BeforeEach
    void runBefore() {
        qMove = new QMove("fire move",22,5,4,fire);
    }


    @Test
    void testCreateBlank() {
        QMove tempQMove = new QMove();
    }

    @Test
    void testDuration() {
        assertEquals(qMove.getDuration(),4);
        qMove.setDuration(23);
        assertEquals(qMove.getDuration(),23);
    }

    @Test
    void testName() {
        assertEquals(qMove.getName(), "fire move");
        qMove.setName("not fire");
        assertEquals(qMove.getName(), "not fire");
    }

    @Test
    void testEnergy() {
        assertEquals(qMove.getEnergy(), 5);
        qMove.setEnergy(45);
        assertEquals(qMove.getEnergy(), 45);
    }

    @Test
    void testDamage() {
        assertEquals(qMove.getDamage(), 22);
        qMove.setDamage(10);
        assertEquals(qMove.getDamage(), 10);
    }

    @Test
    void testType() {
        assertEquals(qMove.getType(), fire);
        qMove.setType(grass);
        assertEquals(qMove.getType(), grass);
    }
}
