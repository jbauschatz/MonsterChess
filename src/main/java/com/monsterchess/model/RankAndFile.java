package com.monsterchess.model;

import java.util.function.BiConsumer;

public class RankAndFile {

	public static boolean isPromotionRank(int rank, Player player) {
		return (rank == 0 && player == Player.BLACK)
				|| (rank == 7 && player == Player.WHITE);
	}

	public static void startTopLeft(BiConsumer<Integer,Integer> callback) {
		for(Integer rank = 7; rank >=0; rank--) {
			for (Integer file = 0; file < 8; file++) {
				callback.accept(rank,file);
			}
		}
	}
}
