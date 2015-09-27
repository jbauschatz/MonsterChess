
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
public class Bishop extends Piece {

	public List<Move> getThreatenedMoves() {
		List<Move> moves = new LinkedList<>();

		addMovesUntilBlocking(moves, s -> s.getUp().getLeft());
		addMovesUntilBlocking(moves, s -> s.getUp().getRight());
		addMovesUntilBlocking(moves, s -> s.getDown().getLeft());
		addMovesUntilBlocking(moves, s -> s.getDown().getRight());

		return moves;
	}

	public Bishop(MonsterChess game, Player player, Square startingPosition) {
		super("B", game, player, startingPosition);
	}

}
