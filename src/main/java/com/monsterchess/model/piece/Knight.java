
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
		return new LinkedList<>();
	}

	public Knight(Player player) {
		super("N", player);
	}
}
