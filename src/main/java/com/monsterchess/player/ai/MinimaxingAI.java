package com.monsterchess.player.ai;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class MinimaxingAI<GameStateType, MoveType> {

	protected abstract GameStateType makeMove(GameStateType gameState, MoveType move);

	protected abstract List<MoveType> getLegalMoves(GameStateType gameState);

	protected abstract boolean isTerminal(GameStateType gameState);

	protected abstract double evaluate(GameStateType gameState);

	protected abstract boolean shouldMaximize(GameStateType gameState);

	public List<MoveEvaluation<MoveType>> evaluateMoves(GameStateType gameState) {
		List<MoveEvaluation<MoveType>> moveEvaluations = new LinkedList<>();

		List<MoveType> legalMoves = getLegalMoves(gameState);
		for (MoveType move : legalMoves) {
			GameStateType afterMove = makeMove(gameState, move);
			double moveValue = evaluate(afterMove, searchDepth-1);
			moveEvaluations.add(new MoveEvaluation<>(move, moveValue));
		}

		// Order the results based on maximizing/minimizing priority
		if (shouldMaximize(gameState)) {
			Collections.sort(moveEvaluations, (e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));
		} else {
			Collections.sort(moveEvaluations, (e1, e2) -> Double.compare(e1.getValue(), e2.getValue()));
		}

		return moveEvaluations;
	}

	public MinimaxingAI(int searchDepth) {
		this.searchDepth = searchDepth;
	}

	protected double evaluate(GameStateType gameState, int searchDepth) {
		if (searchDepth == 0 || isTerminal(gameState)) {
			return evaluate(gameState);
		}

		List<MoveType> legalMoves = getLegalMoves(gameState);
		boolean maximixing = shouldMaximize(gameState);

		if (maximixing) {
			double maxValue = -Double.MAX_VALUE;

			for (MoveType move : legalMoves) {
				GameStateType stateAfterMove = makeMove(gameState, move);
				double moveValue = evaluate(stateAfterMove, searchDepth - 1);

				maxValue = Math.max(moveValue, maxValue);
			}

			return maxValue;
		} else {
			double minValue = Double.MAX_VALUE;

			for (MoveType move : legalMoves) {
				GameStateType stateAfterMove = makeMove(gameState, move);
				double moveValue = evaluate(stateAfterMove, searchDepth - 1);

				minValue = Math.min(moveValue, minValue);
			}

			return minValue;
		}
	}

	private int searchDepth;
}
