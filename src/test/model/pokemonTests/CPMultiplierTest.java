package model.pokemonTests;

import model.pokemon.CPMultiplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CPMultiplierTest {
    private CPMultiplier cpm;


    @BeforeEach
    void runBefore() {
        cpm = new CPMultiplier();
    }

    @Test
    void testGetMultiplier() {
        assertEquals(cpm.getMultiplier(21), 0.4530599591);
        assertEquals(cpm.getMultiplier(0), 0.094);
        assertEquals(cpm.getMultiplier(44), 0.64065295);
        assertEquals(cpm.getMultiplier(78), 0.7903);
    }
}
