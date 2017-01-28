package com.monsterchess.gamestate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameStateBuilder_String extends GameStateBuilderArrayBacked<String> {

	private static Pattern CHESS_PATTERN = Pattern.compile("\\[([ prbnkqPRBNKQ])\\]");

	public GameStateBuilder_String() {
		super();
	}

	public GameStateBuilder_String(String gameState) {
		super();
		Matcher m = CHESS_PATTERN.matcher(gameState);

		for(int index = 0; index < 64; index++) {
			m.find();
			add(m.group().charAt(1),
				index % 8,
				Math.abs((index / 8)-7));
		}
	}

	@Override
	public String build() {

		StringBuilder sb = new StringBuilder();

		for(int index = 0 ; index < 64 ; index++ ) {
			if(index != 0 && index % 8 == 0) {
				sb.append(" ").append(Integer.toString(9 - (index / 8))).append("\n");
			}
			sb.append('[').append(this.gameBoard[index]).append(']');

		}
		sb.append(" 1\n a  b  c  d  e  f  g  h");

		return sb.toString();
	}


}
