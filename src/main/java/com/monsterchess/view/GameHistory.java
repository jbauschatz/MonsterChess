
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.GameState;
import com.monsterchess.model.event.ChessEvent;
import com.monsterchess.model.event.MoveEvent;
import com.monsterchess.model.move.Move;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GameHistory extends JPanel {

	public GameHistory(GameEngine engine) {
		turns = new DefaultListModel<>();
		engine.addListener(this::processEvent);

		setPreferredSize(new Dimension(200, 800));
		setAutoscrolls(true);

		turnsView = new JList<>(turns);
		turnsView.setPreferredSize(new Dimension(180, 800));
		add(new JScrollPane(turnsView));

		numTurns = 1;
		latestTurn = new Turn();
		latestTurn.turnNumber = numTurns;
		turns.addElement(latestTurn);

		turnsView.setCellRenderer((list, value, index, isSelected, cellHasFocus) ->
				{
					JPanel row = new JPanel();
					row.setLayout(new GridLayout(1, 3));

					if (value.whiteFirstMove == null)
						row.add(new JLabel(value.turnNumber + ". "));
					else
						row.add(new JLabel(value.turnNumber + ". " + value.whiteFirstMove));

					if (value.whiteSecondMove != null)
						row.add(new JLabel(value.whiteSecondMove + ""));
					else
						row.add(Box.createHorizontalGlue());

					if (value.blackMove != null)
						row.add(new JLabel(value.blackMove + ""));
					else
						row.add(Box.createHorizontalGlue());

					return row;
				}
		);
	}

	private void processEvent(ChessEvent event) {
		SwingUtilities.invokeLater(() ->{
			if (event instanceof MoveEvent) {
				MoveEvent moveEvent = (MoveEvent)event;

				int moveNum = moveEvent.getMoveNumber() % 3;
				if (moveNum == 1) {
					latestTurn.whiteFirstMove = moveEvent.getMove();
					turnsView.repaint();
				} else if (moveNum == 2) {
					latestTurn.whiteSecondMove = moveEvent.getMove();
					turnsView.repaint();
				} else {
					latestTurn.blackMove = moveEvent.getMove();
					latestTurn = new Turn();
					++numTurns;
					latestTurn.turnNumber = numTurns;
					turns.addElement(latestTurn);
					turnsView.setSelectedValue(latestTurn, true);
				}
			}
		});
	}

	private class Turn {
		int turnNumber;
		Move whiteFirstMove;
		Move whiteSecondMove;
		Move blackMove;
	}

	private DefaultListModel<Turn> turns;
	private JList<Turn> turnsView;
	private Turn latestTurn;
	private int numTurns;
}
