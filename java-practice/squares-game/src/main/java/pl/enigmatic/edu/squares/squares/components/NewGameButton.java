package pl.enigmatic.edu.squares.squares.components;

import pl.enigmatic.edu.squares.squares.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameButton extends JButton implements ActionListener {

    private final GamePanel gamePanel;

    public NewGameButton(GamePanel gamePanel) {
        super("New game");
        this.gamePanel = gamePanel;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        gamePanel.newGame();
    }

}
