/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.model;

import com.monsterchess.model.event.ChessEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class GameEngine {

	public void newGame() {
		currentState = GameState.createInitialState();
		notifyListeners();
	}

	public GameState getCurrentState() {
		return currentState;
	}

	public void addListener(ChessEventListener listener) {
		listeners.add(listener);
	}

	public GameEngine() {
		listeners = new LinkedList<>();

		newGame();
	}

	private void notifyListeners() {
		for (ChessEventListener listener : listeners)
			listener.processEvent(null);
	}

	private GameState currentState;
	private List<ChessEventListener> listeners;
}
