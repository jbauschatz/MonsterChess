
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
public class Queen extends Piece {

	public List<Move> getThreatenedMoves() {
		List<Move> moves = new LinkedList<>();

		// Orthogonal moves
		addMovesUntilBlocking(moves, Square::getLeft);
		addMovesUntilBlocking(moves, Square::getRight);
		addMovesUntilBlocking(moves, Square::getUp);
		addMovesUntilBlocking(moves, Square::getDown);

		// Diagonal moves
		addMovesUntilBlocking(moves, s -> s.getUp().getLeft());
		addMovesUntilBlocking(moves, s -> s.getUp().getRight());
		addMovesUntilBlocking(moves, s -> s.getDown().getLeft());
		addMovesUntilBlocking(moves, s -> s.getDown().getRight());

		return moves;
	}

	public Queen(MonsterChess game, Player player, Square startingPosition) {
		super("Q", game, player, startingPosition);
	}

}
