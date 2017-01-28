
package com.monsterchess.model.piece;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.Move;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class King extends Piece {

	public List<Move> getThreatenedMoves(Square currentPosition, GameState gameState) {
		LinkedList<Move> moves = new LinkedList<>();

		// Directly down
		Square down = currentPosition.getDown();
		tryMoveOrCapture(moves, currentPosition, down, gameState);

		// Diagonally down
		tryMoveOrCapture(moves, currentPosition, down.getLeft(), gameState);
		tryMoveOrCapture(moves, currentPosition, down.getRight(), gameState);

		// Directly up
		Square up = currentPosition.getUp();
		tryMoveOrCapture(moves, currentPosition, up, gameState);

		// Diagonally up
		tryMoveOrCapture(moves, currentPosition, up.getLeft(), gameState);
		tryMoveOrCapture(moves, currentPosition, up.getRight(), gameState);

		// Directly left
		tryMoveOrCapture(moves, currentPosition, currentPosition.getLeft(), gameState);

		// Directly right
		tryMoveOrCapture(moves, currentPosition, currentPosition.getRight(), gameState);

		return moves;
	}

	public King(Player player) {
		super(Type.KING, "K", player);
	}
}
