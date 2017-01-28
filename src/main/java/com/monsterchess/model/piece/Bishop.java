
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
public class Bishop extends Piece {

	public List<Move> getThreatenedMoves(Square currentPosition, GameState gameState) {
		List<Move> moves = new LinkedList<>();

		tryMoveOrCaptureUntilBlocking(moves, currentPosition, s -> s.getUp().getLeft(), gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, s -> s.getUp().getRight(), gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, s -> s.getDown().getLeft(), gameState);
		tryMoveOrCaptureUntilBlocking(moves, currentPosition, s -> s.getDown().getRight(), gameState);

		return moves;
	}

	public Bishop(Player player) {
		super("B", player);
	}

}
