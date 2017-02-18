package com.monsterchess.gamestate;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.RankAndFile;
import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Piece;

/**
 * Provides a GameStateSerializer in Forsyth–Edwards Notation
 *
 * https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation
 *
 */
public class GameStateSerializer_FEN implements GameStateSerializer<String> {

	@Override
	public String from(GameState gameState) {

		StringBuilder sb = new StringBuilder();

		appendPlacement(gameState,sb);
		sb.append(" ");
		appendNextMove(gameState,sb);
		sb.append(" ");
		appendCastlingAbility(gameState,sb);
		sb.append(" ");
		appendEnPassantTarget(gameState,sb);
		sb.append(" ");
		appendHalfmoveClock(gameState,sb);
		sb.append(" ");
		appendFullmoveNumber(gameState,sb);

		return sb.toString();
	}

	private void appendFullmoveNumber(GameState gameState, StringBuilder sb) {
		sb.append(gameState.getMoveNumber()); //Not yet implemented
	}

	private void appendHalfmoveClock(GameState gameState, StringBuilder sb) {
		sb.append("0"); //Not yet implemented
	}

	private void appendEnPassantTarget(GameState gameState, StringBuilder sb) {
		sb.append("-"); //Not yet implemented
	}

	private void appendCastlingAbility(GameState gameState, StringBuilder sb) {
		sb.append("-"); //Not yet implemented
	}

	private void appendNextMove(GameState gameState, StringBuilder sb) {
		sb.append(gameState.getPlayerToMove() == Player.WHITE ? "w" : "b");
	}

	private static void appendPlacement(GameState gameState, StringBuilder sb) {
		final int[] counter = new int[1];
		counter[0] = 0;

		RankAndFile.startTopLeft((rank, file) -> {
			Piece piece = gameState.getPiece(new Square(rank,file));
			if(piece == null) {
				counter[0] = counter[0] + 1;
			} else {
				if(counter[0] > 0) {
					sb.append(Integer.toString(counter[0]));
					counter[0] = 0;
				}
				sb.append(piece.getShorthandFEN());
			}
			if(file == 7) {
				if(counter[0] > 0) {
					sb.append(Integer.toString(counter[0]));
					counter[0] = 0;
				}
				if(rank != 0) {
					sb.append("/");
				}
				counter[0] = 0;
			}
		});
	}

	@Override
	public GameState from(String s) {
		return null;
	}
}
