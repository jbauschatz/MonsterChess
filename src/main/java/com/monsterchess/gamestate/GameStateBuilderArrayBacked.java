package com.monsterchess.gamestate;

public abstract class GameStateBuilderArrayBacked<T> implements GameStateBuilder<T> {

	protected char[] gameBoard = new char[]{
		' ',' ',' ',' ',' ',' ',' ',' ',
		' ',' ',' ',' ',' ',' ',' ',' ',
		' ',' ',' ',' ',' ',' ',' ',' ',
		' ',' ',' ',' ',' ',' ',' ',' ',
		' ',' ',' ',' ',' ',' ',' ',' ',
		' ',' ',' ',' ',' ',' ',' ',' ',
		' ',' ',' ',' ',' ',' ',' ',' ',
		' ',' ',' ',' ',' ',' ',' ',' '
	};

	protected GameStateBuilderArrayBacked() {

	}

	/**
	 * Uses actual rank and file chars rather than int index.
	 *
	 * @param material
	 * @param rank
	 * @param file
	 * @return
	 */
	@Override
	public GameStateBuilderArrayBacked<T> add(char material, char file, char rank) {
		int file_int = (file - 97);
		int rank_int = (rank - 49);
		add(material,file_int,rank_int);
		return this;
	}

	/**
	 * Uses int 0-7 indexes rather than rank and file char.
	 *
	 * @param material
	 * @param rank
	 * @param file
	 * @return
	 */
	@Override
	public GameStateBuilderArrayBacked<T> add(char material, int file, int rank) {
		int rank_offset = Math.abs(rank - 7);
		int file_offset = file;
		gameBoard[(rank_offset * 8) + file_offset] = material;
		return this;
	}

}
