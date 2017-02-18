package com.monsterchess.gamestate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Piece;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GameStateBuilder_FileTest {

	String getFENorBlank(GameState gameState, Square square) {
		Piece p = gameState.getPiece(square);
		return p == null ? "" : p.getShorthandFEN();
	}

	@Test
	public void testLoadFromPath() throws IOException {

		GameStateSerializer<Path> pathSerializer = new GameStateSerializer_Path(Paths.get("src/test/resources"));

		GameState expectedGameState = GameState.createInitialState();

		Path generatedPath = null;
		try {
			generatedPath = pathSerializer.from(expectedGameState);

			GameState loadedGameState = pathSerializer.from(generatedPath.getFileName());

			// Double-escaped pattern for matching either kind of slash
			String fileSeparatorPattern = "[\\\\\\/]";

			Pattern p = Pattern.compile(
					"src" + fileSeparatorPattern
					+ "test" + fileSeparatorPattern
					+ "resources" + fileSeparatorPattern
					+ "\\d+\\.mc");
			Matcher m = p.matcher(generatedPath.toString());

			assertThat("Check that generatedPath matches expected format.",
				m.find(),
				is(equalTo(true)));

			expectedGameState.getSquares()
				.forEach(square -> {
					assertThat("Loaded game state square state should match expected state.",
						getFENorBlank(loadedGameState,square),
						is(equalTo(getFENorBlank(expectedGameState,square))));
				});

		} finally {
			Files.deleteIfExists(generatedPath);
		}
	}

	@Test
	public void testLoadInitialFromFile() {
		Path path = Paths.get("src/test/resources");

		GameStateSerializer<Path> pathSerializer = new GameStateSerializer_Path(path);

		GameState loadedGameState = pathSerializer.from(Paths.get("initial-monster-chess.mc"));
		GameState expectedGameState = GameState.createInitialState();

		expectedGameState.getSquares()
			.forEach(square -> {
				assertThat("Loaded game state square state should match expected state.",
					getFENorBlank(loadedGameState,square),
					is(equalTo(getFENorBlank(expectedGameState,square))));
			});
	}
}
