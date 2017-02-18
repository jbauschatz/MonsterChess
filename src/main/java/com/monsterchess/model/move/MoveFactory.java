/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.model.move;

import com.monsterchess.model.GameState;
import com.monsterchess.model.RankAndFile;
import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Piece;

/**
 *
 */
public class MoveFactory {

	public static Move buildMove(Square from, Square to, GameState gameState) {
		Piece movingPiece = gameState.getPiece(from);
		if (movingPiece == null)
			throw new IllegalArgumentException("Invalid move: no piece present on " + from);

		Piece targetPiece = gameState.getPiece(to);

		if (targetPiece == null) {
			// Check for a promotion move
			if (RankAndFile.isPromotionRank(to.getRank(), movingPiece.getPlayer()))
				return new BasicMoveWithPromotion(movingPiece, from, to, Piece.Type.QUEEN);

			return new BasicMove(movingPiece, from, to);
		} else {
			if (targetPiece.getPlayer() == movingPiece.getPlayer())
				throw new IllegalArgumentException("Invalid move: cannot capture piece of same color");

			// Check for a promoting capture
			if (RankAndFile.isPromotionRank(to.getRank(), movingPiece.getPlayer()))
				return new CaptureWithPromotion(movingPiece, targetPiece, from, to, Piece.Type.QUEEN);

			return new Capture(movingPiece, targetPiece, from, to);
		}
	}
}
