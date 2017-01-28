
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
public class Knight extends Piece {

	public List<Move> getThreatenedMoves(Square currentPosition, GameState gameState) {
		List<Move> moves = new LinkedList<>();

		tryMoveOrCapture(moves, currentPosition, currentPosition.getUp().getUp().getRight(), gameState);
		tryMoveOrCapture(moves, currentPosition, currentPosition.getUp().getUp().getLeft(), gameState);
		tryMoveOrCapture(moves, currentPosition, currentPosition.getUp().getRight().getRight(), gameState);
		tryMoveOrCapture(moves, currentPosition, currentPosition.getUp().getLeft().getLeft(), gameState);

		tryMoveOrCapture(moves, currentPosition, currentPosition.getDown().getDown().getRight(), gameState);
		tryMoveOrCapture(moves, currentPosition, currentPosition.getDown().getDown().getLeft(), gameState);
		tryMoveOrCapture(moves, currentPosition, currentPosition.getDown().getRight().getRight(), gameState);
		tryMoveOrCapture(moves, currentPosition, currentPosition.getDown().getLeft().getLeft(), gameState);

		return moves;
	}

	public Knight(Player player) {
		super("N", player);
	}
}
