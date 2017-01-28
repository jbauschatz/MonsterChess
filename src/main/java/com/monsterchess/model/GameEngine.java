/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.model;

import com.monsterchess.model.event.ChessEvent;
import com.monsterchess.model.event.ChessEventListener;
import com.monsterchess.model.event.MoveEvent;
import com.monsterchess.model.move.Move;
import com.monsterchess.player.GamePlayer;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class GameEngine {

	public List<Move> getLegalMoves(Player player) {
		if (player != currentState.getPlayerToMove()) {
			return new LinkedList<>();
		}

		// TODO validate each of these based on the rules of Check, etc
		return currentState.getThreatenedMoves();
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
			// TODO - Calculate and cache legal moves for the current player

			// TODO - evaluate Check or Checkmate

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

	private void notifyListeners(ChessEvent event) {
		for (ChessEventListener listener : listeners)
			listener.processEvent(event);
	}

	private GameState currentState;
	private List<ChessEventListener> listeners;
	private GamePlayer whitePlayer;
	private GamePlayer blackPlayer;
}
