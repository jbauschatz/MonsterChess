
package com.monsterchess.model.piece;

import com.monsterchess.model.MonsterChess;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.Move;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class King extends Piece {

	@Override
	public List<Move> getThreatenedMoves() {
		List<Move> moves = new LinkedList<>();

		// Try to move down
		Square down = square.getDown();
		if (square.isOnBoard()) {
			addMoveOrCapture(down, moves);

			// Down and left
			addMoveOrCapture(down.getLeft(), moves);

			// Down and right
			addMoveOrCapture(down.getRight(), moves);
		}

		// Try to move up
		Square up = square.getUp();
		if (up.isOnBoard()) {
			addMoveOrCapture(up, moves);

			// Up and left
			addMoveOrCapture(up.getLeft(), moves);

			// Up and right
			addMoveOrCapture(up.getRight(), moves);
		}

		// Directly left
		addMoveOrCapture(square.getLeft(), moves);

		// Directly right
		addMoveOrCapture(square.getRight(), moves);

		return moves;
	}

	public King(MonsterChess game, Player player, Square startingPosition) {
		super("K", game, player, startingPosition);
	}
}
