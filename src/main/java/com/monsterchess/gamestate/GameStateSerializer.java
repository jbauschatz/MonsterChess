package com.monsterchess.gamestate;

import com.monsterchess.model.GameState;

public interface GameStateSerializer<T> {

	T from(GameState gameState);

	GameState from(T t);

}
