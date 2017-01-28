
package com.monsterchess.model.piece;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.BasicMove;
import com.monsterchess.model.move.Capture;
import com.monsterchess.model.move.Move;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Pawn extends Piece {

	public List<Move> getThreatenedMoves(Square currentPosition, GameState gameState) {
		List<Move> moves = new LinkedList<>();

		int moveRank = player == Player.WHITE
				? currentPosition.getRank() + 1
				: currentPosition.getRank() - 1;

		// Try to move one space
		Square oneMove = new Square(moveRank, currentPosition.getFile());
		if (gameState.isEmpty(oneMove)) {
			moves.add(new BasicMove(this, currentPosition, oneMove));

			boolean hasMoved = player == Player.WHITE
					? currentPosition.getRank() != 1
					: currentPosition.getRank() != 6;

			// Try to move two spaces
			if (!hasMoved) {
				int doubleMoveRank = player == Player.WHITE
						? currentPosition.getRank() + 2
						: currentPosition.getRank() - 2;
				Square doubleMove = new Square(doubleMoveRank, currentPosition.getFile());
				if (gameState.isEmpty(doubleMove))
					moves.add(new BasicMove(this, currentPosition, doubleMove));
			}
		}

		// Try to capture
		if (currentPosition.getFile() > 0) {
			Square leftCaptureSquare = new Square(moveRank, currentPosition.getFile()-1);
			if (!gameState.isEmpty(leftCaptureSquare) && gameState.getPiece(leftCaptureSquare).getPlayer() != player) {
				Capture captureLeft = new Capture(this, gameState.getPiece(leftCaptureSquare), currentPosition, leftCaptureSquare);
				moves.add(captureLeft);
			}
		}
		if (currentPosition.getFile() < 7) {
			Square rightCaptureSquare = new Square(moveRank, currentPosition.getFile()+1);
			if (!gameState.isEmpty(rightCaptureSquare) && gameState.getPiece(rightCaptureSquare).getPlayer() != player) {
				Capture captureLeft = new Capture(this, gameState.getPiece(rightCaptureSquare), currentPosition, rightCaptureSquare);
				moves.add(captureLeft);
			}
		}

		// TODO add En Passant

		return moves;
	}

	public Pawn(Player player) {
		super("P", player);
	}
}
