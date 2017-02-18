
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.Square;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GameWindow extends JFrame {

	public void addSquareClickListener(SquareClickListener listener) {
		boardView.addSquareClickListener(listener);
	}

	public void highlightSquare(Square square) {
		boardView.highlightSquare(square);
	}

	public void clearHighlights() {
		boardView.clearHighlights();
	}

	public GameWindow(GameEngine engine) {
		setLayout(new BorderLayout());

		boardView = new BoardView(engine);
		add(boardView, BorderLayout.CENTER);

		GameHistory history = new GameHistory(engine);
		add(history, BorderLayout.EAST);

		setSize(new Dimension(1000, 800));

		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private BoardView boardView;
}
