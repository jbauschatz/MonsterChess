package com.monsterchess.player;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.move.Move;
import com.monsterchess.player.ai.Heuristics;
import com.monsterchess.player.ai.MaterialAndPositionalHeuristics;
import com.monsterchess.player.ai.MinimaxingAI;
import com.monsterchess.player.ai.MoveEvaluation;

import java.util.List;

/**
 *
 */
public class MinMaxPlayer implements GamePlayer {

	private class MonsterChessMinMax extends MinimaxingAI<GameState, Move> {

		public MonsterChessMinMax(int searchDepth) {
			super(searchDepth);
		}

		@Override
		protected GameState makeMove(GameState gameState, Move move) {
			return gameState.makeMove(move);
		}

		@Override
		protected List<Move> getLegalMoves(GameState gameState) {
			return gameState.getThreatenedMoves();
		}

		@Override
		protected boolean isTerminal(GameState gameState) {
			return false; // TODO not implemented
		}

		@Override
		protected double evaluate(GameState gameState) {
			return heuristics.evaluate(gameState);
		}

		@Override
		protected boolean shouldMaximize(GameState gameState) {
			return gameState.getPlayerToMove() == Player.WHITE;
		}
	}

	@Override
	public String getName() {
		return "Min-Maxing AI";
	}

	@Override
	public Move getMove(GameEngine engine, Player player) {
		List<MoveEvaluation<Move>> moveEvaluations = monsterChessMinMax.evaluateMoves(engine.getCurrentState());
		return moveEvaluations.get(0).getMove();
	}

	public MinMaxPlayer(int searchDepth) {
		heuristics = new MaterialAndPositionalHeuristics();
		monsterChessMinMax = new MonsterChessMinMax(searchDepth);
	}

	private Heuristics heuristics;
	private MonsterChessMinMax monsterChessMinMax;
}
