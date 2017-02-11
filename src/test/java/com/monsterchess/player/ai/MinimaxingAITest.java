
package com.monsterchess.player.ai;

import com.monsterchess.test.GameTreeNode;
import com.monsterchess.test.TestPlayer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class MinimaxingAITest {

	private class GameTreeMinimaxAI extends MinimaxingAI<GameTreeNode, String> {
		public GameTreeMinimaxAI(int searchDepth) {
			super(searchDepth);
		}

		@Override
		protected GameTreeNode makeMove(GameTreeNode gameState, String move) {
			return gameState.makeMove(move);
		}

		@Override
		protected List<String> getLegalMoves(GameTreeNode gameState) {
			return gameState.getLegalMoves();
		}

		@Override
		protected boolean isTerminal(GameTreeNode gameState) {
			return gameState.isTerminal();
		}

		@Override
		protected double evaluate(GameTreeNode gameState) {
			return gameState.getValue();
		}

		@Override
		protected boolean shouldMaximize(GameTreeNode gameState) {
			return gameState.getPlayer() == TestPlayer.WHITE;
		}
	}

	@Test
	public void evaluate_1MoveAhead_maximizing() {
		MinimaxingAI<GameTreeNode, String> ai = new GameTreeMinimaxAI(1);

		GameTreeNode tree = new GameTreeNode("root", 0, TestPlayer.WHITE,
				new GameTreeNode("A", 1),
				new GameTreeNode("B", 100));

		// Method under test
		List<MoveEvaluation<String>> moveEvaluations = ai.evaluateMoves(tree);

		List<MoveEvaluation<String>> expectedMoves = Arrays.asList(
				new MoveEvaluation<>("B", 100d),
				new MoveEvaluation<>("A", 1d));
		assertThat(moveEvaluations, is(equalTo(expectedMoves)));
	}

	@Test
	public void evaluate_1MoveAhead_minimizing() {
		MinimaxingAI<GameTreeNode, String> ai = new GameTreeMinimaxAI(1);

		GameTreeNode tree = new GameTreeNode("root", 0, TestPlayer.BLACK,
				new GameTreeNode("A", -1),
				new GameTreeNode("B", -100));

		// Method under test
		List<MoveEvaluation<String>> moveEvaluations = ai.evaluateMoves(tree);

		List<MoveEvaluation<String>> expectedMoves = Arrays.asList(
				new MoveEvaluation<>("B", -100d),
				new MoveEvaluation<>("A", -1d));
		assertThat(moveEvaluations, is(equalTo(expectedMoves)));
	}

	@Test
	public void evaluate_2MoveAhead_maximizing() {
		MinimaxingAI<GameTreeNode, String> ai = new GameTreeMinimaxAI(2);

		GameTreeNode tree = new GameTreeNode("root", 0, TestPlayer.WHITE,
				new GameTreeNode("A", 0, TestPlayer.WHITE,
						new GameTreeNode("A-max", 10),
						new GameTreeNode("A-min", -100)),
				new GameTreeNode("B", 5, TestPlayer.WHITE,
						new GameTreeNode("B-max", -1),
						new GameTreeNode("B-min", -10)));

		// Method under test
		List<MoveEvaluation<String>> moveEvaluations = ai.evaluateMoves(tree);

		List<MoveEvaluation<String>> expectedMoves = Arrays.asList(
				new MoveEvaluation<>("A", 10d),
				new MoveEvaluation<>("B", -1d));
		assertThat(moveEvaluations, is(equalTo(expectedMoves)));
	}

	@Test
	public void evaluate_2MoveAhead_minimizing() {
		MinimaxingAI<GameTreeNode, String> ai = new GameTreeMinimaxAI(2);

		GameTreeNode tree = new GameTreeNode("root", 0, TestPlayer.BLACK,
				new GameTreeNode("A", 0, TestPlayer.BLACK,
						new GameTreeNode("A-max", -10),
						new GameTreeNode("A-min", 100)),
				new GameTreeNode("B", -5, TestPlayer.BLACK,
						new GameTreeNode("B-max", 1),
						new GameTreeNode("B-min", 10)));

		// Method under test
		List<MoveEvaluation<String>> moveEvaluations = ai.evaluateMoves(tree);

		List<MoveEvaluation<String>> expectedMoves = Arrays.asList(
				new MoveEvaluation<>("A", -10d),
				new MoveEvaluation<>("B", 1d));
		assertThat(moveEvaluations, is(equalTo(expectedMoves)));
	}

	@Test
	public void evaluate_2MoveAhead_withOpponent_maximizing() {
		MinimaxingAI<GameTreeNode, String> ai = new GameTreeMinimaxAI(2);

		GameTreeNode tree = new GameTreeNode("root", 0, TestPlayer.WHITE,
				new GameTreeNode("A", -10, TestPlayer.BLACK,
						new GameTreeNode("A-max", 5),
						new GameTreeNode("A-min", 10)),
				new GameTreeNode("B", 100, TestPlayer.BLACK,
						new GameTreeNode("B-max", -5),
						new GameTreeNode("B-min", -10)));

		// Method under test
		List<MoveEvaluation<String>> moveEvaluations = ai.evaluateMoves(tree);

		List<MoveEvaluation<String>> expectedMoves = Arrays.asList(
				new MoveEvaluation<>("A", 5d),
				new MoveEvaluation<>("B", -10d));
		assertThat(moveEvaluations, is(equalTo(expectedMoves)));
	}
}
