/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.player.ai;

import com.monsterchess.model.GameState;

/**
 *
 */
public interface Heuristics {

	/**
	 * A static evaluation of a game state
	 *
	 * A positive value represents an advantage for White; negative is an advantage for Black
	 */
	double evaluate(GameState state);
}
