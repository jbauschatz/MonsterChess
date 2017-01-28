
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;

/**
 *
 */
public class Main {

	public static void main(String[] args) {
		GameEngine engine = new GameEngine();
		new GameWindow(engine);
	}
}
