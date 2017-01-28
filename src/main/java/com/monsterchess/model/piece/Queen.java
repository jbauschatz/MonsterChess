
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
public class Queen extends Piece {

	public List<Move> getThreatenedMoves(Square currentPosition, GameState gameState) {
		List<Move> moves = new LinkedList<>();

		// Orthogonal moves
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, Square::getLeft, gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, Square::getRight, gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, Square::getUp, gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, Square::getDown, gameState);

		// Diagonal moves
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, s -> s.getUp().getLeft(), gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, s -> s.getUp().getRight(), gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, s -> s.getDown().getLeft(), gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, s -> s.getDown().getRight(), gameState);

		return moves;
	}

	public Queen(Player player) {
		super("Q", player);
	}

}
