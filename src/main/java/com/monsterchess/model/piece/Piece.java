
package com.monsterchess.model.piece;

import com.monsterchess.model.Player;
import com.monsterchess.model.MonsterChess;
import com.monsterchess.model.move.BasicMove;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.Capture;
import com.monsterchess.model.move.Move;

import java.util.List;
import java.util.function.Function;

/**
 *
 */
public abstract class Piece {

	public abstract List<Move> getThreatenedMoves();

	public String getShorthand() {
		return shorthand;
	}

	public Player getPlayer() {
		return player;
	}

	public Square getSquare() {
		return square;
	}

	public void setSquare(Square square) {
		this.square = square;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public Piece(String shorthand, MonsterChess game, Player player, Square startingPosition) {
		this.shorthand = shorthand;
		this.game = game;
		this.player = player;
		this.square = startingPosition;
	}

	protected void addMovesUntilBlocking(List<Move> moves, Function<Square, Square> nextSquare) {
		for (Square current = nextSquare.apply(square); current.isOnBoard(); current = nextSquare.apply(current)) {
			if (game.isEmpty(current)) {
				moves.add(new BasicMove(this, square, current));
			} else {
				Piece blockingPiece = game.getPiece(current);
				if (blockingPiece.getPlayer() != player)
					moves.add(new Capture(this, blockingPiece, square, current));
				break;
			}
		}
	}

	protected void addMoveOrCapture(Square moveSquare, List<Move> moves) {
		if (!moveSquare.isOnBoard())
			return;

		if (game.isEmpty(moveSquare))
			moves.add(new BasicMove(this, square, moveSquare));
		else if (game.getPiece(moveSquare).getPlayer() != player)
			moves.add(new Capture(this, game.getPiece(moveSquare), square, moveSquare));
	}

	protected final Player player;
	protected final MonsterChess game;
	protected final String shorthand;

	protected Square square;
	protected boolean hasMoved;
}
