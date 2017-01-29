package com.monsterchess.model;

import com.monsterchess.model.event.ChessEvent;
import com.monsterchess.model.event.ChessEventListener;
import com.monsterchess.model.event.MoveEvent;
import com.monsterchess.model.move.Move;
import com.monsterchess.player.GamePlayer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class GameEngine {

	public List<Move> getLegalMoves() {
		return legalMoves;
	}

	public void newGame() {
		currentState = GameState.createInitialState();
		notifyListeners(null);
	}

	public GameState getCurrentState() {
		return currentState;
	}

	public void addListener(ChessEventListener listener) {
		listeners.add(listener);
	}

	public void playGame() {
		while (true) {
			System.out.println(currentState.getTurnCycle());

			cacheLegalMoves();

			// TODO - evaluate Check/Checkmate/Stalemate, possibly end the game
			if (legalMoves.isEmpty()) {
				System.out.println("Game Over (no legal moves remain)");
				break;
			}

			GamePlayer player = currentState.getPlayerToMove() == Player.WHITE
					? whitePlayer
					: blackPlayer;
			Move move = player.getMove(this, currentState.getPlayerToMove());
			System.out.println(player.getName() + " chose: " + move);

			// TODO validate the move
			makeMove(move);
			notifyListeners(new MoveEvent(move, currentState.getMoveNumber()));
		}
	}

	public GameEngine(GamePlayer whitePlayer, GamePlayer blackPlayer) {
		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;

		listeners = new LinkedList<>();

		newGame();
	}

	private void makeMove(Move move) {
		currentState = currentState.makeMove(move);
	}

	private void cacheLegalMoves() {
		List<Move> newLegalMoves = new LinkedList<>();

		List<Move> possibleMoves = currentState.getThreatenedMoves();

		// White's first move in the cycle
		if (currentState.getMoveNumber() % 3 == 0) {
			for (Move move : possibleMoves) {
				boolean everyMoveResultsInCheck = true; // If EVERY one of White's next moves puts White in Check

				GameState afterFirstMove = currentState.makeMove(move);
				List<Move> whiteSecondMoves = currentState.getThreatenedMoves(Player.WHITE)
						.collect(Collectors.toList());
				for (Move whiteSecondMove : whiteSecondMoves) {
					GameState afterSecondMove = afterFirstMove.makeMove(whiteSecondMove);
					boolean whiteIsInCheck = afterSecondMove.isThreatened(afterSecondMove.getWhiteKing());

					// If this move doesn't immediately put White in check it is legal
					if (!whiteIsInCheck) {
						everyMoveResultsInCheck = false;
						break;
					}
				}

				if (!everyMoveResultsInCheck) {
					newLegalMoves.add(move);
				} else {
					System.out.println("[CHECK] " + move + " would put give White no safe second move.");
				}
			}
		}

		// White's second move in the cycle
		if (currentState.getMoveNumber() % 3 == 1) {
			for (Move move : possibleMoves) {
				GameState afterWhiteMoves = currentState.makeMove(move);
				boolean whiteIsInCheck = afterWhiteMoves.isThreatened(afterWhiteMoves.getWhiteKing());

				// If this move doesn't immediately put White in check it is legal
				if (!whiteIsInCheck) {
					newLegalMoves.add(move);
				} else {
					System.out.println("[CHECK] " + move + " would put White in check.");
				}
			}
		}

		// Black's move
		else {
			for (Move blackMove : currentState.getThreatenedMoves()) {
				GameState afterBlackMove = currentState.makeMove(blackMove);

				boolean blackInCheck = afterBlackMove.isThreatened(afterBlackMove.getBlackKing());
				if (blackInCheck) {
					System.out.println("[CHECK] " + blackMove + " would put Black in Check (immediately)");
				} else {
					boolean someWhiteResponseGivesCheck = false;

					/* TODO Unlike other tests for Check, this must ONLY consider legal first moves for White.
					White is not allowed to put himself in unresolved check on his first move and then end the game there.
					Or IS he? The legality of this needs to be considered */
					List<Move> whiteResponses = afterBlackMove.getThreatenedMoves(Player.WHITE).collect(Collectors.toList());
					for (Move whiteResponse : whiteResponses) {
						GameState afterWhiteResponse = afterBlackMove.makeMove(whiteResponse);
						boolean blackInDelayedCheck = afterWhiteResponse.isThreatened(afterWhiteResponse.getBlackKing());
						if (blackInDelayedCheck) {
							someWhiteResponseGivesCheck = true;
							System.out.println("[CHECK] " + blackMove + " would put Black in Check (via White playing " + whiteResponse + ")");
							break;
						}
					}

					if (!someWhiteResponseGivesCheck) {
						newLegalMoves.add(blackMove);
					}
				}
			}
		}

		legalMoves = newLegalMoves;
	}

	private void notifyListeners(ChessEvent event) {
		for (ChessEventListener listener : listeners) {
			listener.processEvent(event);
		}
	}

	private GameState currentState;
	private List<Move> legalMoves;
	private List<ChessEventListener> listeners;
	private GamePlayer whitePlayer;
	private GamePlayer blackPlayer;
}
