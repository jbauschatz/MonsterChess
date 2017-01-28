/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class SquareTest {

	@Test
	public void constructor_numeric_a1() {
		Square square = new Square(0,0);

		assertThat("Square(0,0) should be on board",
				square.isOnBoard(),
				is(equalTo(true)));
		assertThat("Square(0,0) should be notated as a1",
				square.toString(),
				is(equalTo("a1")));
		assertThat("Square(0,0) should have rank 0",
				square.getRank(),
				is(equalTo(0)));
		assertThat("Square(0,0) should have file 0",
				square.getFile(),
				is(equalTo(0)));
	}

	@Test
	public void constructor_numeric_h8() {
		Square square = new Square(7, 7);

		assertThat("Square(7, 7) should be on board",
				square.isOnBoard(),
				is(equalTo(true)));
		assertThat("Square(7, 7) should be notated as h8",
				square.toString(),
				is(equalTo("h8")));
		assertThat("Square(7, 7) should have rank 7",
				square.getRank(),
				is(equalTo(7)));
		assertThat("Square(7, 7) should have file 7",
				square.getFile(),
				is(equalTo(7)));
	}

	@Test
	public void constructor_numeric_b5() {
		Square square = new Square(4, 1);

		assertThat("Square(4, 1) should be on board",
				square.isOnBoard(),
				is(equalTo(true)));
		assertThat("Square(4, 1) should be notated as b5",
				square.toString(),
				is(equalTo("b5")));
		assertThat("Square(4, 1) should have rank 4",
				square.getRank(),
				is(equalTo(4)));
		assertThat("Square(4, 1) should have file 1",
				square.getFile(),
				is(equalTo(1)));
	}

	@Test
	public void constructor_notation_a1() {
		Square square = new Square('a', 1);

		assertThat("Square(a1) should be on board",
				square.isOnBoard(),
				is(equalTo(true)));
		assertThat("Square(a1) should be notated as a1",
				square.toString(),
				is(equalTo("a1")));
		assertThat("Square(a1) should have rank 0",
				square.getRank(),
				is(equalTo(0)));
		assertThat("Square(a1) should have file 0",
				square.getFile(),
				is(equalTo(0)));
	}

	@Test
	public void constructor_notation_h8() {
		Square square = new Square('h', 8);

		assertThat("Square(h8) should be on board",
				square.isOnBoard(),
				is(equalTo(true)));
		assertThat("Square(h8) should be notated as h8",
				square.toString(),
				is(equalTo("h8")));
		assertThat("Square(h8) should have rank 7",
				square.getRank(),
				is(equalTo(7)));
		assertThat("Square(h8) should have file 7",
				square.getFile(),
				is(equalTo(7)));
	}

	@Test
	public void constructor_notation_b5() {
		Square square = new Square('b', 5);

		assertThat("Square(b5) should be on board",
				square.isOnBoard(),
				is(equalTo(true)));
		assertThat("Square(b5) should be notated as b5",
				square.toString(),
				is(equalTo("b5")));
		assertThat("Square(b5) should have rank 4",
				square.getRank(),
				is(equalTo(4)));
		assertThat("Square(b5) should have file 1",
				square.getFile(),
				is(equalTo(1)));
	}

	@Test
	public void equals_numericConstructor() {
		assertThat("Square(3, 5) should equal Square(3, 5)",
				new Square(3, 5).equals(new Square(3, 5)),
				is(equalTo(true)));
	}

	@Test
	public void equals_differentConstructors() {
		assertThat("Square(0, 0) should equal Square(a, 1)",
				new Square(0, 0).equals(new Square('a', 1)),
				is(equalTo(true)));
		assertThat("Square(7, 7) should equal Square(h, 8)",
				new Square(7, 7).equals(new Square('h', 8)),
				is(equalTo(true)));
		assertThat("Square(4, 1) should equal Square(b, 5)",
				new Square(4, 1).equals(new Square('b', 5)),
				is(equalTo(true)));
	}
}
