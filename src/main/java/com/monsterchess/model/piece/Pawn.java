
package com.monsterchess.model.piece;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.*;

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
		if (!oneMove.isOnBoard()) {
			return moves;
		}

		if (gameState.isEmpty(oneMove)) {
			if (player == Player.WHITE && oneMove.getRank() == 7) {
				moves.add(new BasicMoveWithPromotion(this, currentPosition, oneMove, Type.QUEEN));
			} else if (player == Player.BLACK && oneMove.getRank() == 0) {
				moves.add(new BasicMoveWithPromotion(this, currentPosition, oneMove, Type.QUEEN));
			} else {
				// A regular (non promoting, non capture) move
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
		}

		// Try to capture
		if (currentPosition.getFile() > 0) {
			Square leftCaptureSquare = new Square(moveRank, currentPosition.getFile()-1);
			if (!gameState.isEmpty(leftCaptureSquare) && gameState.getPiece(leftCaptureSquare).getPlayer() != player) {
				Piece capturedPiece = gameState.getPiece(leftCaptureSquare);

				if (player == Player.WHITE && leftCaptureSquare.getRank() == 7) {
					moves.add(new CaptureWithPromotion(this, capturedPiece, currentPosition, leftCaptureSquare, Type.QUEEN));
				} else if (player == Player.BLACK && leftCaptureSquare.getRank() == 0) {
					moves.add(new CaptureWithPromotion(this, capturedPiece, currentPosition, leftCaptureSquare, Type.QUEEN));
				} else {
					// A capture move without a promotion
					Capture captureLeft = new Capture(this, capturedPiece, currentPosition, leftCaptureSquare);
					moves.add(captureLeft);
				}
			}
		}
		if (currentPosition.getFile() < 7) {
			// TODO check for a capture with promotion

			Square rightCaptureSquare = new Square(moveRank, currentPosition.getFile()+1);
			if (!gameState.isEmpty(rightCaptureSquare) && gameState.getPiece(rightCaptureSquare).getPlayer() != player) {
				Piece capturedPiece = gameState.getPiece(rightCaptureSquare);

				if (player == Player.WHITE && rightCaptureSquare.getRank() == 7) {
					moves.add(new CaptureWithPromotion(this, capturedPiece, currentPosition, rightCaptureSquare, Type.QUEEN));
				} else if (player == Player.BLACK && rightCaptureSquare.getRank() == 0) {
					moves.add(new CaptureWithPromotion(this, capturedPiece, currentPosition, rightCaptureSquare, Type.QUEEN));
				} else {
					Capture captureLeft = new Capture(this, gameState.getPiece(rightCaptureSquare), currentPosition, rightCaptureSquare);
					moves.add(captureLeft);
				}
			}
		}

		// TODO add En Passant

		return moves;
	}

	public Pawn(Player player) {
		super(Type.PAWN, "P", player);
	}
}
