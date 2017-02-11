
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.player.GreedyPlayer;
import com.monsterchess.player.MinMaxPlayer;

/**
 *
 */
public class Main {

	public static void main(String[] args) {
		GameEngine engine = new GameEngine(new MinMaxPlayer(4), new MinMaxPlayer(4));
		new GameWindow(engine);

		engine.playGame();
	}
}
