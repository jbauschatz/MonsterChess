/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.player;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.Player;
import com.monsterchess.model.move.Move;

/**
 *
 */
public interface GamePlayer {

	String getName();

	Move getMove(GameEngine engine, Player player);
}
