package com.monsterchess.gamestate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import com.monsterchess.model.GameState;

public class GameStateSerializer_Path implements GameStateSerializer<Path> {

	public GameStateSerializer_Path(Path path) {
		if(path == null) {
			throw new IllegalArgumentException("Path cannot be null.");
		}
		this.path = path;
	}

	@Override
	public Path from(GameState gameState) {
		Path resolvedPath = this.path.resolve(System.currentTimeMillis() + ".mc");
		String stringGameState = STRING_SERIALIZER.from(gameState);
		try {
			Files.write(resolvedPath, stringGameState.getBytes());
			return resolvedPath;
		} catch (IOException e) {
			throw new RuntimeException("Failed to write GameState to Path: " + resolvedPath, e);
		}
	}

	@Override
	public GameState from(Path path) {
		Path resolvedPath = this.path.resolve(path);
		try {
			String stringGameState = new String(Files.readAllBytes(resolvedPath));
			return STRING_SERIALIZER.from(stringGameState);
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to read GameState from Path: " + resolvedPath,e);
		}
	}

	private static final GameStateSerializer<String> STRING_SERIALIZER = new GameStateSerializer_String();
	private final Path path;
}
