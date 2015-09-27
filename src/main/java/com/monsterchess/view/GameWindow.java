
package com.monsterchess.view;

import com.monsterchess.model.MonsterChess;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GameWindow extends JFrame {

	public GameWindow(MonsterChess game) {
		this.game = game;

		BoardView boardView = new BoardView(game);
		add(boardView);

		setSize(new Dimension(800, 800));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private MonsterChess game;
}
