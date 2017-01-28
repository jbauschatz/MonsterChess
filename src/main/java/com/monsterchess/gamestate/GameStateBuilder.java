package com.monsterchess.gamestate;

public interface GameStateBuilder<T> {

	T build();

	GameStateBuilder<T> add(char material, char rank, char file);

	GameStateBuilder<T> add(char material, int rank, int file);
}
