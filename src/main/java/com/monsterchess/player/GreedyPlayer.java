package com.monsterchess.player;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.move.Move;
import com.monsterchess.player.GamePlayer;
import com.monsterchess.player.ai.Heuristics;
import com.monsterchess.player.ai.MaterialAndPositionalHeuristics;

/**
 *
 */
public class GreedyPlayer implements GamePlayer {
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

		boolean maximizing = player == Player.WHITE;
		double maxValue = -100000d;
		Move maxMove = null;
		double minValue = 100000d;
		Move minMove = null;

		for (Move move : engine.getLegalMoves()) {
			GameState stateAfterMove = currentState.makeMove(move);
			double value = heuristics.evaluate(stateAfterMove);

			// Add some entropy, +/1 1 point
			value += Math.random()*3 - 1.5;

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
			return maxMove;
		} else {
			return minMove;
		}
	}

	public GreedyPlayer() {
		heuristics = new MaterialAndPositionalHeuristics();
	}

	private Heuristics heuristics;
}
