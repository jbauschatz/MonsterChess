
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
		if (square.getRank() > 0) {
			Square down = new Square(square.getRank() - 1, square.getFile());
			addMoveOrCapture(down, moves);

			// Down and left
			if (square.getFile() > 0) {
				Square downLeft = new Square(square.getRank() - 1, square.getFile() - 1);
				addMoveOrCapture(downLeft, moves);
			}
			// Down and right
			if (square.getFile() < 7) {
				Square downRight = new Square(square.getRank() - 1, square.getFile() + 1);
				addMoveOrCapture(downRight, moves);
			}
		}
		// Try to move up
		if (square.getRank() < 7) {
			Square down = new Square(square.getRank() + 1, square.getFile());
			addMoveOrCapture(down, moves);

			// Up and left
			if (square.getFile() > 0) {
				Square upLeft = new Square(square.getRank() + 1, square.getFile() - 1);
				addMoveOrCapture(upLeft, moves);
			}
			// Up and right
			if (square.getFile() < 7) {
				Square upRight = new Square(square.getRank() + 1, square.getFile() + 1);
				addMoveOrCapture(upRight, moves);
			}
		}

		// Directly left
		if (square.getFile() > 0) {
			Square left = new Square(square.getRank(), square.getFile() - 1);
			addMoveOrCapture(left, moves);
		}

		// Directly right
		if (square.getFile() < 7) {
			Square right = new Square(square.getRank(), square.getFile() + 1);
			addMoveOrCapture(right, moves);
		}

		return moves;
	}

	public King(MonsterChess game, Player player, Square startingPosition) {
		super("K", game, player, startingPosition);
	}
}
