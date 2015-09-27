
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
public class Rook extends Piece {

	public List<Move> getThreatenedMoves() {
		List<Move> moves = new LinkedList<>();

		addMovesUntilBlocking(moves, Square::getLeft);
		addMovesUntilBlocking(moves, Square::getRight);
		addMovesUntilBlocking(moves, Square::getUp);
		addMovesUntilBlocking(moves, Square::getDown);

		return moves;
	}

	public Rook(MonsterChess game, Player player, Square startingPosition) {
		super("R", game, player, startingPosition);
	}

}
