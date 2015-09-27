
package com.monsterchess.view;

import com.monsterchess.model.MonsterChess;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.Move;
import com.monsterchess.model.piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 *
 */
public class BoardView extends JPanel {

	private static final int SQUARE_WIDTH = 30;

	public void refreshPieces() {
		game.getSquares().forEach(s ->
			squares[s.getRank()][s.getFile()].showPiece(game.getPiece(s))
		);
	}

	public BoardView(MonsterChess game) {
		this.game = game;
		squares = new SquareButton[8][8];

		setLayout(new GridLayout(8, 8));
		for (int rank = 7; rank>=0; --rank) {
			for (int file = 0; file<8; ++file) {
				Square square = new Square(rank, file);
				SquareButton squareButton = new SquareButton(square, SQUARE_WIDTH);
				add(squareButton);

				squares[rank][file] = squareButton;

				squareButton.addActionListener(actionEvent -> squareClicked(squareButton));
			}
		}

		game.addListener(this::refreshPieces);
		refreshPieces();
	}

	private void squareClicked(SquareButton button) {
		Square square = button.getSquare();

		if (activePiece == null) {
			if (!game.isEmpty(square)) {
				Piece pieceClicked = game.getPiece(square);
				if (pieceClicked.getPlayer() == game.getPlayerToMove()) {
					activePiece = pieceClicked;
					activeSquare = button;
					button.setHighlight(true);
				}
			}
		} else {
			// See if a legal move has been clicked
			Optional<Move> moveOption = game.getLegalMoves()
					.stream()
					.filter(move -> move.getMovingPiece() == activePiece)
					.filter(move -> move.getOperativeSquare().equals(square))
					.findAny();

			if (moveOption.isPresent())
				game.makeMove(moveOption.get());

			// Clear out selections and highlights
			if (activeSquare != null)
				activeSquare.setHighlight(false);
			activePiece = null;
			activeSquare = null;
		}
	}

	private MonsterChess game;
	private SquareButton[][] squares;

	private Piece activePiece;
	private SquareButton activeSquare;
}
