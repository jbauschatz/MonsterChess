package com.monsterchess.player.ai;

/**
 *
 */
public class MoveEvaluation<MoveType> {

	public MoveType getMove() {
		return move;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return move + ": " + value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MoveEvaluation<?> that = (MoveEvaluation<?>) o;

		if (Double.compare(that.value, value) != 0) return false;
		return move.equals(that.move);

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = move.hashCode();
		temp = Double.doubleToLongBits(value);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public MoveEvaluation(MoveType move, double value) {
		this.move = move;
		this.value = value;
	}

	private final MoveType move;
	private final double value;
}
