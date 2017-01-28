
package com.monsterchess.model.piece;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.Move;

import java.util.List;

/**
 *
 */
public abstract class Piece {

	/**
	 * Moves that this piece "threatens" to make
	 *
	 * These moves may not be legal to execute - for example, the rules of Check may prohibit a move
	 * These moves represent the intrinsic movement and capture rules of the piece.
	 */
	public abstract List<Move> getThreatenedMoves(Square currentPosition, GameState gameState);

	public String getShorthand() {
		return shorthand;
	}

	public Player getPlayer() {
		return player;
	}

	protected Piece(String shorthand, Player player) {
		this.shorthand = shorthand;
		this.player = player;
	}

	protected final Player player;
	private final String shorthand;
}
