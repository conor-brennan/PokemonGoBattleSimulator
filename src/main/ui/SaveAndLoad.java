package ui;

import model.*;
import model.pokemon.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;



public class SaveAndLoad implements Save, Load {
    private HashMap<String, Type> types;
    private HashMap<String, QMove> qmoves;
    private HashMap<String, CMove> cmoves;
    private HashMap<String, Pokemon> pokemons;

    public SaveAndLoad() throws IOException {
        types = new HashMap<>();
        qmoves = new HashMap<>();
        cmoves = new HashMap<>();
        pokemons = new HashMap<>();
        load();
    }

    public HashMap<String, Type> getTypes() {
        return types;
    }

    public HashMap<String, Pokemon> getPokemons() {
        return pokemons;
    }

    public HashMap<String, CMove> getCmoves() {
        return cmoves;
    }

    public HashMap<String, QMove> getQmoves() {
        return qmoves;
    }

    @Override
    public void load() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputtext.txt"));
        PrintWriter writer = new PrintWriter("outputtext.txt","UTF-8");
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpaces(line);
            if (partsOfLine.get(0).equals("type")) {
                typeResult(partsOfLine.get(1), partsOfLine.get(2), partsOfLine.get(3), partsOfLine.get(4));
            } else if (partsOfLine.get(0).equals("qmove")) {
                qmoveResult(partsOfLine.get(1), partsOfLine.get(2),
                        partsOfLine.get(3),partsOfLine.get(4),partsOfLine.get(5));
            } else if (partsOfLine.get(0).equals("cmove")) {
                cmoveResult(partsOfLine.get(1), partsOfLine.get(2), partsOfLine.get(3), partsOfLine.get(4));
            } else if (partsOfLine.get(0).equals("pokemon")) {
                pokemonResult(line);
            }
            writer.println(line);
        }
        writer.close();
    }

    private ArrayList<String> splitOnSpaces(String line) {
        String[] splits = line.split("  ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    private ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    void typeResult(String name, String weak, String resist, String imm) {
        if (!types.containsKey(name)) {
            Type type = new Type();
            type.setName(name);
            type.setWeaknesses(splitOnSpace(weak));
            type.setResistances(splitOnSpace(resist));
            type.setImmunities(splitOnSpace(imm));
            types.put(name, type);
            System.out.println(name + " was successfully added to types!");
        }
    }

    private void qmoveResult(String name, String damage, String energy, String duration, String type) {
        if (!qmoves.containsKey(name) && types.containsKey(type)) {
            QMove qmove = new QMove();
            qmove.setName(name);
            qmove.setDamage(makeInt(damage));
            qmove.setEnergy(makeInt(energy));
            qmove.setDuration(makeInt(duration));
            Type moveType = types.get(type);
            qmove.setType(moveType);
            qmoves.put(name, qmove);
            System.out.println(name + " was successfully added to QMoves!");
        }
    }

    private void cmoveResult(String name, String damage, String energy, String type) {
        if (!cmoves.containsKey(name) && types.containsKey(type)) {
            CMove cmove = new CMove();
            cmove.setName(name);
            cmove.setDamage(makeInt(damage));
            cmove.setEnergy(makeInt(energy));
            Type moveType = types.get(type);
            cmove.setType(moveType);
            cmoves.put(name, cmove);
            System.out.println(name + " was successfully added to CMoves!");
        }
    }

    private void pokemonResult(String line) {
        ArrayList<String> partsOfLine = splitOnSpaces(line);
        if (!pokemons.containsKey(partsOfLine.get(1)) && types.containsKey(partsOfLine.get(5))
                && types.containsKey(partsOfLine.get(6)) && qmoves.containsKey(partsOfLine.get(7))
                && cmoves.containsKey(partsOfLine.get(8)) && cmoves.containsKey(partsOfLine.get(9))) {

            Pokemon pokemon = pokemonSetter(partsOfLine.get(1), partsOfLine.get(2), partsOfLine.get(3),
                    partsOfLine.get(4), partsOfLine.get(5), partsOfLine.get(6), partsOfLine.get(7), partsOfLine.get(8),
                    partsOfLine.get(9));

            pokemons.put(partsOfLine.get(1), pokemon);
            System.out.println(partsOfLine.get(1) + " was successfully added to Pokemon!");
        }
    }

    private Pokemon pokemonSetter(String name, String att, String def, String sta, String type1, String type2,
                                  String qmove, String cmove1, String cmove2) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(name);
        pokemon.setAttack(makeInt(att));
        pokemon.setDefence(makeInt(def));
        pokemon.setStamina(makeInt(sta));
        pokemon.setType1(types.get(type1));
        pokemon.setType2(types.get(type2));
        pokemon.setQuickMove(qmoves.get(qmove));
        pokemon.setChargeMove1(cmoves.get(cmove1));
        pokemon.setChargeMove2(cmoves.get(cmove2));
        pokemon.setEnergy(0);
        pokemon.setLevel(20);
        pokemon.setShields(2);
        pokemon.setHp((int) Math.floor(makeInt(sta) * pokemon.cpMultiplier(20)));
        return pokemon;
    }

    private int makeInt(String string) {
        return Integer.parseInt(string);
    }





    @Override
    public void save() throws IOException {
        PrintStream fileWriter = new PrintStream(new File("inputtext.txt"));
        saveType(fileWriter);
        saveQMove(fileWriter);
        saveCMove(fileWriter);
        savePokemon(fileWriter);
    }

    private void saveType(PrintStream fileWriter) throws IOException {
        for (Type t : types.values()) {
            StringBuilder result = new StringBuilder("type  " + t.getName() + "  ");
            for (String w : t.getWeaknesses()) {
                result.append(w).append(" ");
            }
            result.append(" ");
            for (String r : t.getResistances()) {
                result.append(r).append(" ");
            }

            result.append(" ");
            for (String i : t.getImmunities()) {
                result.append(i).append(" ");
            }
            result.deleteCharAt(result.length() - 1);
            fileWriter.println(result);
        }
    }

    private void saveQMove(PrintStream fileWriter) throws IOException {
        for (QMove q : qmoves.values()) {
            StringBuilder result = new StringBuilder("qmove  " + q.getName() + "  ");
            result.append(q.getDamage() + "  ");
            result.append(q.getEnergy() + "  ");
            result.append(q.getDuration() + "  ");
            result.append(q.getType().getName());
            fileWriter.println(result);
        }
    }

    private void saveCMove(PrintStream fileWriter) throws IOException {
        for (CMove c : cmoves.values()) {
            StringBuilder result = new StringBuilder("cmove  " + c.getName() + "  ");
            result.append(c.getDamage() + "  ");
            result.append(c.getEnergy() + "  ");
            result.append(c.getType().getName());
            fileWriter.println(result);
        }
    }

    private void savePokemon(PrintStream fileWriter) throws IOException {
        for (Pokemon p : pokemons.values()) {
            StringBuilder result = new StringBuilder("pokemon  " + p.getName() + "  ");
            result.append(p.getAttack() + "  " + p.getDefence() + "  " + p.getStamina() + "  ");
            result.append(p.getType1().getName() + "  " + p.getType2().getName() + "  " + p.getQuickMove().getName());
            result.append("  " + p.getChargeMove1().getName() + "  " + p.getChargeMove2().getName());
            fileWriter.println(result);
        }
    }
}
