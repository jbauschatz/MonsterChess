
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.GameState;
import com.monsterchess.model.Square;
import com.monsterchess.model.event.ChessEvent;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class BoardView extends JPanel {

	private static final int SQUARE_WIDTH = 30;

	public void addSquareClickListener(SquareClickListener listener) {
		squareListeners.add(listener);
	}

	public void highlightSquare(Square square) {
		if (!square.isOnBoard())
			throw new IllegalArgumentException("Invalid square: " + square);

		SquareButton button = squares[square.getRank()][square.getFile()];
		button.setHighlight(true);
	}

	public void clearHighlights() {
		for (int rank = 0; rank<8; ++rank) {
			for (int file = 0; file<8; ++file) {
				SquareButton button = squares[rank][file];
				button.setHighlight(false);
			}
		}
	}

	public BoardView(GameEngine engine) {
		this.engine = engine;
		squareListeners = new LinkedList<>();
		squares = new SquareButton[8][8];

		setPreferredSize(new Dimension(SQUARE_WIDTH * 8, SQUARE_WIDTH * 8));

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

		for (SquareClickListener listener : squareListeners) {
			listener.squareClicked(square);
		}
	}

	private void refreshPieces(ChessEvent event) {
		GameState state = engine.getCurrentState();

		SwingUtilities.invokeLater(() -> {
				state.getSquares().forEach(s -> squares[s.getRank()][s.getFile()].showPiece(state.getPiece(s)));
		});
	}

	private GameEngine engine;
	private SquareButton[][] squares;
	private List<SquareClickListener> squareListeners;
}
