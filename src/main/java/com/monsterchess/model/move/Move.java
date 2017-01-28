
package com.monsterchess.model.move;

import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Piece;

/**
 *
 */
public interface Move {

	Square getOperativeSquare();

	Piece getMovingPiece();
}
