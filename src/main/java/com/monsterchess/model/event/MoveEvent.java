
package com.monsterchess.model.event;

import com.monsterchess.model.move.Move;

/**
 *
 */
public class MoveEvent implements ChessEvent {

	public Move getMove() {
		return move;
	}

	public int getMoveNumber() {
		return moveNumber;
	}

	public MoveEvent(Move move, int moveNumber) {
		this.move = move;
		this.moveNumber = moveNumber;
	}

	private Move move;
	private int moveNumber;
}
