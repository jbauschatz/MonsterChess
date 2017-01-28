/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.player.ai;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Piece;

/**
 *
 */
public class MaterialAndPositionalHeuristics implements Heuristics {

	@Override
	public double evaluate(GameState state) {
		// White dead receives the minimum possible value
		if (!state.getPieces(Player.WHITE, Piece.Type.KING).findAny().isPresent()) {
			return -1000;
		}

		// Black dead receives the maximum possible value
		if (!state.getPieces(Player.BLACK, Piece.Type.KING).findAny().isPresent()) {
			return 1000;
		}

		double value = materialValue.evaluate(state);

		// Place a positive value on any White piece advancing
		value += state.getPieces(Player.WHITE)
				.mapToDouble(p -> {
					Square pawnPosition = state.getPosition(p);
					int distanceAdvanced = pawnPosition.getRank() - 1;
					return distanceAdvanced * 1d;
				})
				.sum();

		// Place a small negative positive value on a Black pawn advancing
		value += state.getPieces(Player.BLACK, Piece.Type.PAWN)
				.mapToDouble(p -> {
					Square pawnPosition = state.getPosition(p);
					int distanceAdvanced = 6 - pawnPosition.getRank();
					return distanceAdvanced * -.25d;
				})
				.sum();

		return value;
	}

	public MaterialAndPositionalHeuristics() {
		materialValue = new MaterialValueHeuristics();
	}

	private Heuristics materialValue;

}
