
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.player.GreedyPlayer;
import com.monsterchess.player.MinMaxPlayer;

import javax.swing.*;

/**
 *
 */
public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException
				| InstantiationException
				| UnsupportedLookAndFeelException
				| IllegalAccessException e) {
			e.printStackTrace();
		}

		GameEngine engine = new GameEngine();

		GameWindow window = new GameWindow(engine);

		engine.setWhitePlayer(new UserInputPlayer(engine, window));
		engine.setBlackPlayer(new MinMaxPlayer(5));

		engine.playGame();
	}
}
