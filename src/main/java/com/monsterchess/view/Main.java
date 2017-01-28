
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.player.RandomAI;

/**
 *
 */
public class Main {

	public static void main(String[] args) {
		GameEngine engine = new GameEngine(new RandomAI(), new RandomAI());
		new GameWindow(engine);

		engine.playGame();
	}
}
