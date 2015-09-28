
package com.monsterchess.model.piece;

import com.monsterchess.model.Player;
import com.monsterchess.model.MonsterChess;
import com.monsterchess.model.move.BasicMove;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.Capture;
import com.monsterchess.model.move.Move;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Pawn extends Piece {

	public List<Move> getThreatenedMoves() {
		List<Move> moves = new LinkedList<>();

		int moveRank = player == Player.WHITE ? square.getRank() + 1 : square.getRank() - 1;

		// Try to move one space
		Square oneMove = new Square(moveRank, square.getFile());
		if (game.isEmpty(oneMove)) {
			moves.add(new BasicMove(this, square, oneMove));

			// Try to move two spaces
			if (!hasMoved) {
				int doubleMoveRank = player == Player.WHITE ? square.getRank() + 2 : square.getRank() - 2;
				Square doubleMove = new Square(doubleMoveRank, square.getFile());
				if (game.isEmpty(doubleMove))
					moves.add(new BasicMove(this, square, doubleMove));
			}
		}

		// Try to capture
		if (square.getFile() > 0) {
			Square leftCaptureSquare = new Square(moveRank, square.getFile()-1);
			if (!game.isEmpty(leftCaptureSquare) && game.getPiece(leftCaptureSquare).getPlayer() != player) {
				Capture captureLeft = new Capture(this, game.getPiece(leftCaptureSquare), square, leftCaptureSquare);
				moves.add(captureLeft);
			}
		}
		if (square.getFile() < 7) {
			Square rightCaptureSquare = new Square(moveRank, square.getFile()+1);
			if (!game.isEmpty(rightCaptureSquare) && game.getPiece(rightCaptureSquare).getPlayer() != player) {
				Capture captureLeft = new Capture(this, game.getPiece(rightCaptureSquare), square, rightCaptureSquare);
				moves.add(captureLeft);
			}
		}

		// TODO add En Passant

		return moves;
	}

	public Pawn(MonsterChess game, Player player, Square square) {
		super("P", game, player, square);
	}
}
