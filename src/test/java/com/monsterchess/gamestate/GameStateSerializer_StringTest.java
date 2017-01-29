package com.monsterchess.gamestate;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.piece.King;
import com.monsterchess.model.piece.Knight;
import com.monsterchess.model.piece.Pawn;
import com.monsterchess.model.piece.Queen;

import static org.junit.Assert.*;

public class GameStateSerializer_StringTest {
	GameStateSerializer<String> stringSerializer = new GameStateSerializer_String();

	@Test
	public void testLoadFromPath() throws IOException {
		Path path = Paths.get("src/test/resources/check-state-1.mc");


		String expectedInitialState =
				"[r][n][b][q][k][b][n][r] 8\n" +
				"[p][p][p][p][p][p][p][p] 7\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 6\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 5\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 4\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 3\n" +
				"[ ][ ][ ][P][P][P][ ][ ] 2\n" +
				"[ ][ ][ ][ ][K][ ][ ][ ] 1\n" +
				" a  b  c  d  e  f  g  h";

		//Create initial MonsterChess state
		GameState gs = GameState.createInitialState();

		//Serialize initial state to string
		String gameBoardString = stringSerializer.from(gs);
		System.out.println(gameBoardString);

		//Verify string representation is correct
		assertEquals(expectedInitialState,gameBoardString);

		//Parse expected state from string to GameState
		GameState parsedState = stringSerializer.from(expectedInitialState);

		//Verify White
		assertEquals("P",parsedState.getPiece(new Square('d','2')).getShorthandFEN());
		assertEquals("P",parsedState.getPiece(new Square('e','2')).getShorthandFEN());
		assertEquals("P",parsedState.getPiece(new Square('f','2')).getShorthandFEN());
		assertEquals("K",parsedState.getPiece(new Square('e','1')).getShorthandFEN());

		//Verify Black
		assertEquals("r",parsedState.getPiece(new Square('a','8')).getShorthandFEN());
		assertEquals("n",parsedState.getPiece(new Square('b','8')).getShorthandFEN());
		assertEquals("b",parsedState.getPiece(new Square('c','8')).getShorthandFEN());
		assertEquals("q",parsedState.getPiece(new Square('d','8')).getShorthandFEN());
		assertEquals("k",parsedState.getPiece(new Square('e','8')).getShorthandFEN());
		assertEquals("b",parsedState.getPiece(new Square('f','8')).getShorthandFEN());
		assertEquals("n",parsedState.getPiece(new Square('g','8')).getShorthandFEN());
		assertEquals("r",parsedState.getPiece(new Square('h','8')).getShorthandFEN());
		assertEquals("p",parsedState.getPiece(new Square('a','7')).getShorthandFEN());
		assertEquals("p",parsedState.getPiece(new Square('b','7')).getShorthandFEN());
		assertEquals("p",parsedState.getPiece(new Square('c','7')).getShorthandFEN());
		assertEquals("p",parsedState.getPiece(new Square('d','7')).getShorthandFEN());
		assertEquals("p",parsedState.getPiece(new Square('e','7')).getShorthandFEN());
		assertEquals("p",parsedState.getPiece(new Square('f','7')).getShorthandFEN());
		assertEquals("p",parsedState.getPiece(new Square('g','7')).getShorthandFEN());
		assertEquals("p",parsedState.getPiece(new Square('h','7')).getShorthandFEN());

	}

	@Test
	public void testCorners() {
		String expected =
				"[Q][ ][ ][ ][ ][ ][ ][K] 8\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 7\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 6\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 5\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 4\n" +
				"[ ][ ][ ][k][ ][ ][ ][ ] 3\n" +
				"[ ][ ][ ][ ][ ][ ][ ][ ] 2\n" +
				"[p][ ][ ][ ][ ][ ][ ][n] 1\n" +
				" a  b  c  d  e  f  g  h";

		GameState gameState = new GameState();
		gameState.addPiece(new Queen(Player.WHITE),new Square('a','8'));
		gameState.addPiece(new King(Player.WHITE),new Square('h','8'));
		gameState.addPiece(new King(Player.BLACK),new Square('d','3'));
		gameState.addPiece(new Pawn(Player.BLACK),new Square('a','1'));
		gameState.addPiece(new Knight(Player.BLACK),new Square('h','1'));

		assertEquals(expected,stringSerializer.from(gameState));
	}
}
