
package com.monsterchess.model.move;

import com.monsterchess.model.MonsterChess;
import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Piece;

/**
 *
 */
public interface Move {

	void execute(MonsterChess game);

	Square getOperativeSquare();

	Piece getMovingPiece();
}
