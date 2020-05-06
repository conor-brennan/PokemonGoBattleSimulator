package model.pokemonTests;

import model.pokemon.CMove;
import model.pokemon.Move;
import model.pokemon.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CMoveTest {
    private Move move;
    private ArrayList<String> firew = new ArrayList<>(Arrays.asList("water", "ground", "rock"));
    private ArrayList<String> firer = new ArrayList<>(Arrays.asList("fire", "grass", "fairy"));
    private ArrayList<String> firei = new ArrayList<>(Collections.singletonList(""));
    private Type fire = new Type("fire", firew, firer, firei);


    @BeforeEach
    void runBefore() {
        move = new CMove("fire move",12,3,fire);
    }

    @Test
    void testCreateBlank() {
        CMove testMove = new CMove();
    }
}
