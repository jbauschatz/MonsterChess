
package com.monsterchess.view;

import com.monsterchess.model.MonsterChess;

/**
 *
 */
public class Main {

	public static void main(String[] args) {
		MonsterChess game = new MonsterChess();
		new GameWindow(game);
	}
}
