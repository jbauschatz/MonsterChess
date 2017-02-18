/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.view;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.Player;

import javax.swing.*;

/**
 *
 */
public class GameStatusPanel extends JPanel {

	public GameStatusPanel(GameEngine engine) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new PlayerStatusIndicator(Player.BLACK));

		GameHistory history = new GameHistory(engine);
		add(history);

		add(new PlayerStatusIndicator(Player.WHITE));
	}
}
