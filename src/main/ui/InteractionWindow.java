package ui;

import javax.swing.*;
import java.awt.*;

public class InteractionWindow extends JPanel {

    public static final Color WEBWORK_GREEN = new Color(136,255,136);
    public static final int WIDTH = 800;
    public  static final int HEIGHT = 600;

    public InteractionWindow() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(WEBWORK_GREEN);
    }


}
