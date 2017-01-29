package com.monsterchess.gamestate;

import org.junit.Test;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.BasicMove;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

public class GameStateSerializer_FENTest {


	@Test
	public void initialGameStateTest() {
		GameState initialGameState = GameState.createInitialState();

		GameStateSerializer<String> fenSerializer = new GameStateSerializer_FEN();

		assertThat(
			fenSerializer.from(initialGameState),
			is(equalTo("rnbqkbnr/pppppppp/8/8/8/8/3PPP2/4K3 w - - 0 0"))
		);

	}

	@Test
	public void movedGameStateTest() {
		GameState initialGameState = GameState.createInitialState();

		GameState newGameState = initialGameState.makeMove(
			new BasicMove(
				initialGameState.getPiece(new Square('d','2')),
				new Square('d','2'),
				new Square('d','4')
			));

		GameStateSerializer<String> fenSerializer = new GameStateSerializer_FEN();

		assertThat(
			fenSerializer.from(newGameState),
			is(equalTo("rnbqkbnr/pppppppp/8/8/3P4/8/4PP2/4K3 w - - 0 1"))
		);

	}

}
