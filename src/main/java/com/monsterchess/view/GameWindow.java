
package com.monsterchess.view;

import com.monsterchess.model.MonsterChess;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GameWindow extends JFrame {

	public GameWindow(MonsterChess game) {
		setLayout(new BorderLayout());

		BoardView boardView = new BoardView(game);
		add(boardView, BorderLayout.CENTER);

		GameHistory history = new GameHistory(game);
		add(history, BorderLayout.EAST);

		setSize(new Dimension(1000, 800));

		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
