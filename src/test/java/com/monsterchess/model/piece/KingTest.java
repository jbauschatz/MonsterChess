/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.model.piece;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.BasicMove;
import com.monsterchess.model.move.Move;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 *
 */
public class KingTest {

	@Test
	public void getLegalMoves_leftBlocked() {
		GameState state = new GameState();

		Square e4 = new Square('e', 4);
		Piece king = new King(Player.WHITE);
		state.addPiece(king, e4);

		Square d4 = new Square('d', 4);
		Piece friendlyPawn = new Pawn(Player.WHITE);
		state.addPiece(friendlyPawn, d4);

		// The method under test
		List<Move> kingMoves = king.getThreatenedMoves(e4, state);

		Move illegalMove = new BasicMove(king, e4, d4);
		assertThat("King should not be able to move left onto a friendly piece",
				kingMoves,
				not(contains(illegalMove)));
	}

	@Test
	public void getLegalMoves_rightBlocked() {
		GameState state = new GameState();

		Square d4 = new Square('d', 4);
		Piece king = new King(Player.WHITE);
		state.addPiece(king, d4);

		Square e4 = new Square('e', 4);
		Piece friendlyPawn = new Pawn(Player.WHITE);
		state.addPiece(friendlyPawn, e4);

		// The method under test
		List<Move> kingMoves = king.getThreatenedMoves(d4, state);

		Move illegalMove = new BasicMove(king, d4, e4);
		assertThat("King should not be able to move right onto a friendly piece",
				kingMoves,
				not(contains(illegalMove)));
	}

	@Test
	public void getLegalMoves_allClear() {
		GameState state = new GameState();

		Square e4 = new Square('e', 4);
		Piece king = new King(Player.WHITE);
		state.addPiece(king, e4);

		// The method under test
		List<Move> kingMoves = king.getThreatenedMoves(e4, state);

		List<Move> expectedMoves = Arrays.asList(
				new BasicMove(king, e4, new Square('d', 5)),
				new BasicMove(king, e4, new Square('e', 5)),
				new BasicMove(king, e4, new Square('f', 5)),
				new BasicMove(king, e4, new Square('d', 4)),
				new BasicMove(king, e4, new Square('f', 4)),
				new BasicMove(king, e4, new Square('d', 3)),
				new BasicMove(king, e4, new Square('e', 3)),
				new BasicMove(king, e4, new Square('f', 3))
		);
		assertThat("King should be able to move to all adjacent squares",
				kingMoves,
				containsInAnyOrder(expectedMoves.toArray()));
	}
}
