package com.monsterchess.gamestate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Bishop;
import com.monsterchess.model.piece.King;
import com.monsterchess.model.piece.Knight;
import com.monsterchess.model.piece.Pawn;
import com.monsterchess.model.piece.Piece;
import com.monsterchess.model.piece.Queen;
import com.monsterchess.model.piece.Rook;

import static java.util.Optional.*;

public class GameStateSerializer_String implements GameStateSerializer<String> {

	@Override
	public String from(final GameState gameState) {

		StringBuilder sb = new StringBuilder();

		RankAndFile.startTopLeft((rank,file) -> {
			Piece piece = gameState.getPiece(new Square(rank,file));
			if(piece != null){
				sb.append(String.format("[%s]",piece.getShorthandFEN()));
			} else {
				sb.append("[ ]");
			}
			if(file == 7 ) {
				sb.append(String.format(" %s\n", rank + 1));
			}
		});

		sb.append(" a  b  c  d  e  f  g  h");

		return sb.toString();

	}

	@Override
	public GameState from(String gameString) {
		Matcher m = CHESS_PATTERN.matcher(gameString);
		GameState gameState = new GameState();

		RankAndFile.startTopLeft((rank,file) -> {
			m.find();
			pieceMap.getOrDefault(m.group().charAt(1), Optional.empty())
				.ifPresent(piece ->
					gameState.addPiece(
						piece,
						new Square(rank,file)));
		});

		return gameState;
	}

	private static Pattern CHESS_PATTERN = Pattern.compile("\\[([ prbnkqPRBNKQ])\\]");

	private static Map<Character,Optional<Piece>> pieceMap = new HashMap<>();

	static {
		pieceMap.put('b', of(new Bishop(Player.BLACK)));
		pieceMap.put('B', of(new Bishop(Player.WHITE)));
		pieceMap.put('r', of(new Rook(Player.BLACK)));
		pieceMap.put('R', of(new Rook(Player.WHITE)));
		pieceMap.put('q', of(new Queen(Player.BLACK)));
		pieceMap.put('Q', of(new Queen(Player.WHITE)));
		pieceMap.put('P', of(new Pawn(Player.WHITE)));
		pieceMap.put('p', of(new Pawn(Player.BLACK)));
		pieceMap.put('n', of(new Knight(Player.BLACK)));
		pieceMap.put('N', of(new Knight(Player.WHITE)));
		pieceMap.put('k', of(new King(Player.BLACK)));
		pieceMap.put('K', of(new King(Player.WHITE)));
	}
}
