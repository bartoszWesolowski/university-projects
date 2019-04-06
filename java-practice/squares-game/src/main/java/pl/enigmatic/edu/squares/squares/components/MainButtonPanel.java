package pl.enigmatic.edu.squares.squares.components;

import pl.enigmatic.edu.squares.squares.GamePanel;

import javax.swing.*;

public class MainButtonPanel extends JPanel {

    public MainButtonPanel(GamePanel gamePanel) {
        super();
        add(new NewGameButton(gamePanel));
        add(new ShowStatsButton());
    }
}
