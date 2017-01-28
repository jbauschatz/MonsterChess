package com.monsterchess.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.monsterchess.gamestate.GameStateBuilder_File;

import static org.junit.Assert.assertEquals;

public class GameStateBuilder_FileTest {
	@Test
	public void testLoadFromPath() throws IOException {
		Path path = Paths.get("src/test/resources/check-state-1.mc");

		GameStateBuilder_File fileBuilder = GameStateBuilder_File.fromFile(path);

		String expected =
				"[r][n][b][q][k][b][n][r] 8\n" +
				"[p][p][p][p][p][p][p][p] 7\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 6\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 5\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 4\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 3\n" +
				"[ ][ ][ ][P][P][P][ ][ ] 2\n" +
				"[ ][ ][ ][ ][K][ ][ ][ ] 1\n" +
				" a  b  c  d  e  f  g  h";

		assertEquals(expected, fileBuilder.gameStateAsString());
	}
}
