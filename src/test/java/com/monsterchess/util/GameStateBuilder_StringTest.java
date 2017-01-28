package com.monsterchess.util;

import org.junit.Test;

import com.monsterchess.gamestate.GameStateBuilderArrayBacked;
import com.monsterchess.gamestate.GameStateBuilder_String;

import static org.junit.Assert.*;

public class GameStateBuilder_StringTest {


	@Test
	public void testGameStateBuilder() throws Exception{
		String expected =
				"[r][n][b][q][k][b][n][r] 8\n" +
				"[p][p][p][p][p][p][p][p] 7\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 6\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 5\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 4\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 3\n" +
				"[ ][ ][ ][P][P][P][ ][ ] 2\n" +
				"[ ][ ][ ][ ][K][ ][ ][ ]\n" +
				" a  b  c  d  e  f  g ";

		GameStateBuilderArrayBacked<String> builder = new GameStateBuilder_String(expected);

		String parsed = builder.build();

		assertEquals(expected,parsed);
	}

	@Test
	public void testBlankGame() throws Exception{

		String expected =
				"[ ][ ][ ][ ][ ][ ][ ][ ] 8\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 7\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 6\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 5\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 4\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 3\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 2\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 1\n" +
				" a  b  c  d  e  f  g ";

		GameStateBuilderArrayBacked<String> builder = new GameStateBuilder_String();

		String actual = builder.build();

		assertEquals(expected,expected,actual);
	}

	@Test
	public void testAdd() throws Exception{

		String expected =
				"[Q][ ][ ][ ][ ][ ][ ][K] 8\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 7\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 6\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 5\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 4\n" +
				"[ ][ ][ ][k][ ][ ][ ][ ] 3\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 2\n" +
				"[p][ ][ ][ ][ ][ ][ ][p] 1\n" +
				" a  b  c  d  e  f  g  h";

		GameStateBuilderArrayBacked<String> builder = new GameStateBuilder_String();

		builder.add('Q','a','8');
		builder.add('K','h','8');
		builder.add('k','d','3');
		builder.add('p','a','1');
		builder.add('p','h','1');

		String actual = builder.build();

		assertEquals(expected,expected,actual);
	}

	@Test
	public void testAddIndex() throws Exception{

		String expected =
				"[Q][ ][ ][ ][ ][ ][ ][K] 8\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 7\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 6\n" +
				"[ ][ ][ ][ ][ ][b][ ][ ] 5\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 4\n" +
				"[ ][ ][ ][k][ ][ ][ ][ ] 3\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 2\n" +
				"[p][ ][ ][ ][ ][ ][ ][N] 1\n" +
				" a  b  c  d  e  f  g  h";

		GameStateBuilderArrayBacked<String> builder = new GameStateBuilder_String();

		builder.add('Q',0,7);
		builder.add('K',7,7);
		builder.add('b',5,4);
		builder.add('k',3,2);
		builder.add('p',0,0);
		builder.add('N',7,0);

		String actual = builder.build();

		assertEquals(expected,expected,actual);
	}
}
