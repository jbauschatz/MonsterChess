
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.GameState;
import com.monsterchess.model.Square;
import com.monsterchess.model.event.ChessEvent;
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

	public BoardView(GameEngine engine) {
		this.engine = engine;
		setPreferredSize(new Dimension(SQUARE_WIDTH * 8, SQUARE_WIDTH * 8));

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

		engine.addListener(this::refreshPieces);
		refreshPieces(null);
	}

	private void squareClicked(SquareButton button) {
		Square square = button.getSquare();

		System.out.println("Clicked: " + square);
	}

	private void refreshPieces(ChessEvent event) {
		GameState state = engine.getCurrentState();

		state.getSquares().forEach(s ->
				squares[s.getRank()][s.getFile()].showPiece(state.getPiece(s))
		);
	}

	private GameEngine engine;
	private SquareButton[][] squares;

	private Piece activePiece;
	private SquareButton activeSquare;
}
