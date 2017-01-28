/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.player.ai;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.move.Move;
import com.monsterchess.player.GamePlayer;

/**
 *
 */
public class GreedyAI implements GamePlayer {
	@Override
	public String getName() {
		return "Greedy AI";
	}

	@Override
	public Move getMove(GameEngine engine, Player player) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		GameState currentState = engine.getCurrentState();
		double currentValue = heuristics.evaluate(currentState);
		System.out.println("   [" + getName() + "] heuristics evaluate current position at " + currentValue);

		boolean maximizing = player == Player.WHITE;
		double maxValue = -1000d;
		Move maxMove = null;
		double minValue = 1000d;
		Move minMove = null;

		for (Move move : engine.getLegalMoves(player)) {
			GameState stateAfterMove = currentState.makeMove(move);
			double value = heuristics.evaluate(stateAfterMove);
			if (value >= maxValue) {
				maxValue = value;
				maxMove = move;
			}
			if (value <= minValue) {
				minValue = value;
				minMove = move;
			}
		}

		if (maximizing) {
			System.out.println("   [" + getName() + "] selects " + maxMove + " for its maximal value " + maxValue);
			return maxMove;
		} else {
			System.out.println("   [" + getName() + "] selects " + minMove + " for its minimal value " + minValue);
			return minMove;
		}
	}

	public GreedyAI() {
		heuristics = new MaterialAndPositionalHeuristics();
	}

	private Heuristics heuristics;
}
