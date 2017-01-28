/****************************************************************
 * Copyright (c) 2016 Health Innovation Technologies, Inc. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/
package com.monsterchess.player.ai;

import com.monsterchess.model.GameEngine;
import com.monsterchess.model.Player;
import com.monsterchess.model.move.Move;
import com.monsterchess.player.GamePlayer;

import java.util.List;

/**
 *
 */
public class RandomAI implements GamePlayer {

	@Override
	public String getName() {
		return "Random AI";
	}

	@Override
	public Move getMove(GameEngine engine, Player player) {
		List<Move> legalMoves = engine.getLegalMoves(player);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return legalMoves.get((int)(Math.random()*legalMoves.size()));
	}
}
