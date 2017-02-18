/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.view;

import com.monsterchess.model.Player;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class PlayerStatusIndicator extends JPanel {

	public PlayerStatusIndicator(Player player) {
		JLabel nameLabel = new JLabel(player.name());
		nameLabel.setFont(new Font("Default", Font.BOLD, 24));
		add(nameLabel);
	}
}
