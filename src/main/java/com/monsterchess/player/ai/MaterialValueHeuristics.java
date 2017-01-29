package com.monsterchess.player.ai;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.piece.Piece;

import java.util.HashMap;

/**
 *
 */
public class MaterialValueHeuristics implements Heuristics {
	public double evaluate(GameState state) {
		// White dead receives the minimum possible value
		if (!state.getPieces(Player.WHITE, Piece.Type.KING).findAny().isPresent()) {
			return -1000;
		}

		// Black dead receives the maximum possible value
		if (!state.getPieces(Player.BLACK, Piece.Type.KING).findAny().isPresent()) {
			return 1000;
		}

		// Total the value of all pieces on the board
		double total = 0d;
		for (Piece piece : state.getPieces()) {
			if (piece.getPlayer() == Player.WHITE) {
				total += whitePieceValues.get(piece.getType());
			} else {
				total += blackPieceValues.get(piece.getType());
			}
		}
		return total;
	}

	public MaterialValueHeuristics() {
		whitePieceValues = new HashMap<Piece.Type, Double>(){{
			put(Piece.Type.KING, 1000d);
			put(Piece.Type.QUEEN, 24d);
			put(Piece.Type.ROOK, 18d);
			put(Piece.Type.BISHOP, 15d);
			put(Piece.Type.KNIGHT, 15d);
			put(Piece.Type.PAWN, 13d);
		}};
		blackPieceValues = new HashMap<Piece.Type, Double>(){{
			put(Piece.Type.KING, -1000d);
			put(Piece.Type.QUEEN, -9d);
			put(Piece.Type.ROOK, -5d);
			put(Piece.Type.BISHOP, -3d);
			put(Piece.Type.KNIGHT, -3d);
			put(Piece.Type.PAWN, -1d);
		}};
	}

	private HashMap<Piece.Type, Double> whitePieceValues;
	private HashMap<Piece.Type, Double> blackPieceValues;
}
