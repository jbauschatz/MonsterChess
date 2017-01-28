
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.GameState;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GameWindow extends JFrame {

	public GameWindow(GameEngine engine) {
		setLayout(new BorderLayout());

		BoardView boardView = new BoardView(engine);
		add(boardView, BorderLayout.CENTER);

		GameHistory history = new GameHistory(engine);
		add(history, BorderLayout.EAST);

		setSize(new Dimension(1000, 800));

		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
