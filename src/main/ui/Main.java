package ui;

import model.battle.Battle;
import model.exceptions.NullException;
import model.pokemon.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.util.*;

public class Main extends JFrame implements ActionListener {
    private Scanner scanner;
    private SaveAndLoad saveAndLoad;
    private InteractionWindow window;

    private HashMap<String, model.pokemon.Type> types;
    private HashMap<String, QMove> qmoves;
    private HashMap<String, CMove> cmoves;
    private HashMap<String, Pokemon> pokemons;

    private JLabel labelTitle;
    private JLabel labelName1;
    private JLabel labelName2;
    private JLabel labelResult;

    private GridBagConstraints labelTitleG;
    private GridBagConstraints labelName1G;
    private GridBagConstraints labelName2G;
    private GridBagConstraints labelResultG;

    private JTextField textbox1;
    private JTextField textbox2;

    private GridBagConstraints textbox1G;
    private GridBagConstraints textbox2G;

    private JButton button1;
    private JButton button2;
    private JButton buttonResult;

    private GridBagConstraints button1G;
    private GridBagConstraints button2G;
    private GridBagConstraints buttonResultG;

    public static final Color WEBWORK_GREEN = new Color(136,255,136);
    public static final int WIDTH = 1000;
    public  static final int HEIGHT = 400;

    private Main() throws IOException {
        super("Pokemon Simulator!");

        scanner = new Scanner(System.in);
        saveAndLoad = new SaveAndLoad();
        //window = new InteractionWindow();

        initializeCollections();
        initializeGraphics();

        inputLoop();
    }

    private void initializeCollections() {
        types = saveAndLoad.getTypes();
        qmoves = saveAndLoad.getQmoves();
        cmoves = saveAndLoad.getCmoves();
        pokemons = saveAndLoad.getPokemons();
    }

    private void initializeGraphics() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new GridBagLayout());
        createAll();
        addAll();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void createlabelName1() {
        labelName1G = new GridBagConstraints();
        labelName1 = new JLabel("Pokemon 1");
        labelName1G.gridx = 5;
        labelName1G.gridy = 5;
    }

    public void createlabelName2() {
        labelName2G = new GridBagConstraints();
        labelName2 = new JLabel("Pokemon 2");
        labelName2G.gridx = 11;
        labelName2G.gridy = 5;
    }

    public void createlabelTitle() {
        labelTitleG = new GridBagConstraints();
        labelTitle = new JLabel("Pokemon Showdown Simulator");
        labelTitleG.gridx = 8;
        labelTitleG.gridy = 0;
    }

    public void createlabelNameResult() {
        labelResultG = new GridBagConstraints();
        labelResult = new JLabel("Awaiting input...");
        labelResultG.gridx = 8;
        labelResultG.gridy = 8;
    }

    public void createTextBox1() {
        textbox1G = new GridBagConstraints();
        textbox1 = new JTextField(8);
        textbox1G.gridx = 0;
        textbox1G.gridy = 5;
    }

    public void createTextBox2() {
        textbox2G = new GridBagConstraints();
        textbox2 = new JTextField(8);
        textbox2G.gridx = 14;
        textbox2G.gridy = 5;
    }

    public void createButton1() {
        button1G = new GridBagConstraints();
        button1 = new JButton("Submit1");
        button1.setActionCommand("button1");
        button1.addActionListener(this);
        button1G.gridx = 0;
        button1G.gridy = 8;
    }

    public void createButton2() {
        button2G = new GridBagConstraints();
        button2 = new JButton("Submit2");
        button2.setActionCommand("button2");
        button2.addActionListener(this);
        button2G.gridx = 14;
        button2G.gridy = 8;
    }

    public void createButtonResult() {
        buttonResultG = new GridBagConstraints();
        buttonResult = new JButton("SubmitResult");
        buttonResult.setActionCommand("buttonResult");
        buttonResult.addActionListener(this);
        buttonResultG.gridx = 8;
        buttonResultG.gridy = 11;
    }

    public void createAll() {
        createButton1();
        createButton2();
        createButtonResult();
        createlabelName1();
        createlabelName2();
        createlabelNameResult();
        createlabelTitle();
        createTextBox1();
        createTextBox2();
    }

    public void addAll() {

        add(labelTitle, labelTitleG);
        add(textbox1, textbox1G);
        add(labelName1, labelName1G);
        add(labelName2, labelName2G);
        add(textbox2, textbox2G);
        add(button1, button1G);
        add(labelResult, labelResultG);
        add(button2, button2G);
        add(buttonResult, buttonResultG);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("button1")) {
            pressButton1();
        } else if (e.getActionCommand().equals("button2")) {
            pressButton2();
        } else if (e.getActionCommand().equals("buttonResult")) {
            pressButtonResult();
        }
    }

    private void pressButton1() {
        if (pokemons.containsKey(textbox1.getText())) {
            labelName1.setText(textbox1.getText());
        } else {
            labelName1.setText("Unable to find that Pokemon. Try another");
        }
    }

    private void pressButton2() {
        if (pokemons.containsKey(textbox2.getText())) {
            labelName2.setText(textbox2.getText());
        } else {
            labelName2.setText("Unable to find that Pokemon. Try another");
        }
    }

    private void pressButtonResult() {
        pressButton1();
        pressButton2();
        if (pokemons.containsKey(textbox1.getText()) && pokemons.containsKey(textbox2.getText())) {
            Pokemon p1 = pokemons.get(textbox1.getText());
            Pokemon p2 = pokemons.get(textbox2.getText());
            Battle battle = new Battle(p1, p2);
            labelResult.setText(battle.battle());
        } else {
            labelResult.setText("Please ensure both Pokemon are valid entries");
        }
    }

    private void inputLoop() throws IOException {
        String in;

        while (true) {
            displayOptions();
            in = scanner.nextLine();
            if (in.equals("X")) {
                saveAndLoad.save();
                break;
            }

            String result = processOperation(in);
            System.out.println(result);
        }
    }

    private void displayOptions() {
        System.out.println("What would you like to do?");
        System.out.println("\tBattle!");
        System.out.println("\tX");
    }



    // ripped straight from space invaders
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }


    private String processOperation(String in) throws IOException {
        switch (in) {
            case "battle":
                try {
                    return processBattle();
                } catch (NullException e) {
                    e.printStackTrace();
                    return "Error occurred, try again";
                }
            case "":
                return "";
            default:
                return "Invalid Category";
        }
    }

    private String processBattle() throws IOException, NullException {
        System.out.println("Enter the name of the first pokemon");
        String first = scanner.nextLine();
        System.out.println("Enter the name of the second Pokemon");
        String second = scanner.nextLine();
        if (pokemons.containsKey(first) && pokemons.containsKey(second)) {
            Battle battle = new Battle(pokemons.get(first), pokemons.get(second));
            return battle.battle();
        } else {
            return "At least one of the Pokemon could not be found";
        }
    }


    public static void main(String[] args) throws IOException {
        Main main = new Main();
    }
}