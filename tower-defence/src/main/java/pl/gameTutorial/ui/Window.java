package pl.gameTutorial.ui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

import pl.gameTutorial.main.Game;
import pl.gameTutorial.main.SmartScroller;


public class Window extends Canvas{
	private static final long serialVersionUID = -8499632497636923473L;
	private JFrame frame;
	public final JTextArea infoTextArea = new JTextArea();
	public final GameInfoPanel gameInfoPanel = new GameInfoPanel();
	
	private final int textAreaHeight = 100;
	public Window(int width, int height, String title, Game game) {
		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width, height + textAreaHeight));
		frame.setMinimumSize(new Dimension(width, height + textAreaHeight ));
		frame.setMaximumSize(new Dimension(width, height + textAreaHeight));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		DefaultCaret caret = (DefaultCaret)infoTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//infoTextArea.setText("INFORMATIONS: \n");
		infoTextArea.setEditable(false);
		
		final JScrollPane infoScrollPane = new JScrollPane(infoTextArea);
		infoScrollPane.setPreferredSize(new Dimension(width, 100));
		infoScrollPane.setBounds(0, height - textAreaHeight, width, textAreaHeight);
		infoScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		infoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	
		new SmartScroller(infoScrollPane);
		/*
		 infoScrollPane.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					public void adjustmentValueChanged(AdjustmentEvent e) {
						e.getAdjustable().setValue(
								e.getAdjustable().getMaximum());
					}
				});
		*/
		
		frame.add(infoScrollPane, BorderLayout.SOUTH);
		frame.add(gameInfoPanel, BorderLayout.EAST);
		
		frame.add(game, BorderLayout.CENTER);
		frame.setVisible(true);
		
		//run the Therd in Game object
		//game.start();
	}
}
