package pl.enigmatic.edu.squares;

import pl.enigmatic.edu.squares.squares.GamePanel;
import pl.enigmatic.edu.squares.squares.components.MainButtonPanel;
import pl.enigmatic.edu.squares.util.FramePositionUtil;

import javax.swing.JFrame;
import java.awt.BorderLayout;


public class MainApp extends JFrame {

	private static final long serialVersionUID = -2870682807345883257L;

	private GamePanel gamePanel = new GamePanel();

	public MainApp() {
		super();
		setResizable(false);
		FramePositionUtil.positionFrame(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(new MainButtonPanel(gamePanel), BorderLayout.SOUTH);

		gamePanel.setSize(this.getSize());
		add(gamePanel, BorderLayout.CENTER);
		gamePanel.newGame();
	}

	public static void main(final String[] args) {
		final MainApp m = new MainApp();
		m.setVisible(true);
	}
}
