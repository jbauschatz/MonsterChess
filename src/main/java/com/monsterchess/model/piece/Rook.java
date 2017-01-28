
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
public class Rook extends Piece {

	public List<Move> getThreatenedMoves(Square currentPosition, GameState gameState) {
		List<Move> moves = new LinkedList<>();

		// Orthogonal moves
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, Square::getLeft, gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, Square::getRight, gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, Square::getUp, gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, Square::getDown, gameState);

		return moves;
	}

	public Rook(Player player) {
		super("R", player);
	}

}
