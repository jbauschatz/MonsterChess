package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.BasicMove;
import com.monsterchess.model.move.Capture;
import com.monsterchess.model.move.Move;
import com.monsterchess.model.move.MoveFactory;
import com.monsterchess.model.piece.Piece;
import com.monsterchess.player.GamePlayer;

import java.util.List;

/**
 *
 */
public class UserInputPlayer implements GamePlayer {
	@Override
	public String getName() {
		return "Human";
	}

	@Override
	public Move getMove(GameEngine engine, Player player) {
		System.out.println("Waiting for user input...");

		// Block this thread until a move is made
		// TODO find a more sophisticated way to do this
		while (moveInput == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Move move = moveInput;
		moveInput = null;
		return move;
	}

	public UserInputPlayer(GameEngine engine, GameWindow window) {
		this.engine = engine;
		this.window = window;
		window.addSquareClickListener(this::squareClicked);
	}

	private void squareClicked(Square clickedSquare) {
		List<Move> legalMoves = engine.getLegalMoves();
		Player player = engine.getCurrentState().getPlayerToMove(); // TODO pass this as class parameter

		window.clearHighlights();

		if (selectedPiece == null) {
			Piece clickedPiece = engine.getCurrentState().getPiece(clickedSquare);
			if (clickedPiece != null && clickedPiece.getPlayer() == player) {
				selectedSquare = clickedSquare;
				selectedPiece = clickedPiece;

				window.highlightSquare(selectedSquare);
			} else {
				selectedSquare = null;
				selectedPiece = null;
			}
		} else {
			Piece clickedPiece = engine.getCurrentState().getPiece(clickedSquare);
			if (clickedPiece == null) {
				// TODO look for promotion moves

				// Attempt to move
				Move move = MoveFactory.buildMove(selectedSquare, clickedSquare, engine.getCurrentState());
				if (legalMoves.contains(move)) {
					moveInput = move;
				}

				// Deselect
				selectedPiece = null;
				selectedSquare = null;
			} else if (clickedPiece == selectedPiece) {
				// Deselect
				selectedPiece = null;
				selectedSquare = null;
			} else if (clickedPiece.getPlayer() == player) {
				// Select the new piece
				selectedSquare = clickedSquare;
				selectedPiece = clickedPiece;
				window.highlightSquare(selectedSquare);
			} else {
				Move move = MoveFactory.buildMove(selectedSquare, clickedSquare, engine.getCurrentState());
				if (legalMoves.contains(move)) {
					moveInput = move;
				}

				// Deselect
				selectedPiece = null;
				selectedSquare = null;
			}
		}
	}

	private GameEngine engine;
	private GameWindow window;
	private Piece selectedPiece;
	private Square selectedSquare;
	private Move moveInput;
}
