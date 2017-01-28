package com.monsterchess.gamestate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GameStateBuilder_File implements GameStateBuilder<File> {

	private final Path path;
	private final GameStateBuilder_String delegate;

	public GameStateBuilder_File(Path path, GameStateBuilder_String delegate) {
		this.path = path;
		this.delegate = delegate;
	}

	public static GameStateBuilder_File fromFile(Path path) throws IOException {
		String content = new String(Files.readAllBytes(path));
		GameStateBuilder_File fileBuilder = new GameStateBuilder_File(
			path,new GameStateBuilder_String(content));
		return fileBuilder;
	}

	public static GameStateBuilder_File toFile(Path path) throws IOException {
		GameStateBuilder_File fileBuilder = new GameStateBuilder_File(
			path,new GameStateBuilder_String());
		return fileBuilder;
	}



	@Override
	public File build() {
		String content = delegate.build();
		try {
			Files.write(path, content.getBytes());
			return path.toFile();
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to write file.",e);
		}
	}

	@Override
	public GameStateBuilder<File> add(char material, char rank, char file) {
		delegate.add(material,rank,file);
		return this;
	}

	@Override
	public GameStateBuilder<File> add(char material, int rank, int file) {
		delegate.add(material,rank,file);
		return this;
	}

	public String gameStateAsString() {
		return delegate.build();
	}
}
