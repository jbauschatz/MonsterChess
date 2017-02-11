/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.model.move;

import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.piece.King;
import com.monsterchess.model.piece.Pawn;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class BasicMoveTest {

	@Test
	public void equals_differentPiece() {
		Move moveA = new BasicMove(
				new King(Player.WHITE),
				new Square('e', 4),
				new Square('e', 5));
		Move moveB = new BasicMove(
				new Pawn(Player.WHITE),
				new Square('e', 4),
				new Square('e', 5));

		assertThat("Moves with different pieces should not be equal",
				moveA.equals(moveB),
				is(equalTo(false)));
	}

	@Test
	public void equals_differentSource() {
		Move moveA = new BasicMove(
				new King(Player.WHITE),
				new Square('d', 3),
				new Square('e', 5));
		Move moveB = new BasicMove(
				new King(Player.WHITE),
				new Square('e', 4),
				new Square('e', 5));

		assertThat("Moves with different source squares should not be equal",
				moveA.equals(moveB),
				is(equalTo(false)));
	}

	@Test
	public void equals_differentDestination() {
		Move moveA = new BasicMove(
				new King(Player.WHITE),
				new Square('e', 4),
				new Square('g', 7));
		Move moveB = new BasicMove(
				new King(Player.WHITE),
				new Square('e', 4),
				new Square('e', 5));

		assertThat("Moves with different destination squares should not be equal",
				moveA.equals(moveB),
				is(equalTo(false)));
	}

	@Test
	public void equals_equal() {
		Move moveA = new BasicMove(
				new King(Player.WHITE),
				new Square('e', 4),
				new Square('e', 5));
		Move moveB = new BasicMove(
				new King(Player.WHITE),
				new Square('e', 4),
				new Square('e', 5));

		assertThat("Identical moves should be equal",
				moveA.equals(moveB),
				is(equalTo(false)));
	}
}
