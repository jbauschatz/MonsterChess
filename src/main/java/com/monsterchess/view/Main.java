
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.player.ai.GreedyAI;
import com.monsterchess.player.ai.RandomAI;

/**
 *
 */
public class Main {

	public static void main(String[] args) {
		GameEngine engine = new GameEngine(new GreedyAI(), new GreedyAI());
		new GameWindow(engine);

		engine.playGame();
	}
}
