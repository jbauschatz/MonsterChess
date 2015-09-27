
package com.monsterchess.model;

/**
 *
 */
public class Square {

	@Override
	public String toString() {
		return notation;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Square))
			return false;

		Square otherSquare = (Square)other;
		return rank == otherSquare.rank && file == otherSquare.file;
	}

	public int getRank() {
		return rank;
	}

	public int getFile() {
		return file;
	}

	public char getFileLetter() {
		return (char)(file + 'a');
	}

	public Square getLeft() {
		return new Square(rank, file-1);
	}

	public Square getRight() {
		return new Square(rank, file+1);
	}

	public Square getUp() {
		return new Square(rank+1, file);
	}

	public Square getDown() {
		return new Square(rank-1, file);
	}

	public boolean isOnBoard() {
		return rank >= 0 && rank <= 7 && file >= 0 && file <= 7;
	}

	public Square(int rank, int file) {
		this.rank = rank;
		this.file = file;

		notation = getFileLetter() + "" + (rank + 1);
	}

	private int rank;
	private int file;
	private String notation;
}
