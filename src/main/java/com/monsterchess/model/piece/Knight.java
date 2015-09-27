
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
public class Knight extends Piece {

	public List<Move> getThreatenedMoves() {
		List<Move> moves = new LinkedList<>();

		addMoveOrCapture(square.getUp().getUp().getRight(), moves);
		addMoveOrCapture(square.getUp().getUp().getLeft(), moves);
		addMoveOrCapture(square.getUp().getRight().getRight(), moves);
		addMoveOrCapture(square.getUp().getLeft().getLeft(), moves);

		addMoveOrCapture(square.getDown().getDown().getRight(), moves);
		addMoveOrCapture(square.getDown().getDown().getLeft(), moves);
		addMoveOrCapture(square.getDown().getRight().getRight(), moves);
		addMoveOrCapture(square.getDown().getLeft().getLeft(), moves);

		return moves;
	}

	public Knight(MonsterChess game, Player player, Square startingPosition) {
		super("N", game, player, startingPosition);
	}
}
