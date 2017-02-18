
package com.monsterchess.view;

import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.piece.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class SquareButton extends JButton {

	private static char getChar(Piece piece) {
		if (piece instanceof Pawn)
			return piece.getPlayer() == Player.WHITE ? 'p' : 'o';

		if (piece instanceof King)
			return piece.getPlayer() == Player.WHITE ? 'k' : 'l';

		if (piece instanceof Queen)
			return piece.getPlayer() == Player.WHITE ? 'q' : 'w';

		if (piece instanceof Rook)
			return piece.getPlayer() == Player.WHITE ? 'r' : 't';

		if (piece instanceof Bishop)
			return piece.getPlayer() == Player.WHITE ? 'b' : 'v';

		if (piece instanceof Knight)
			return piece.getPlayer() == Player.WHITE ? 'n' : 'm';

		return ' ';
	}

	public void showPiece(Piece piece) {
		if (piece == null)
			setText("");
		else
			setText(getChar(piece) + "");
	}

	public Square getSquare() {
		return square;
	}

	public void setHighlight(boolean highlight) {
		if (highlight)
			setBackground(Color.YELLOW);
		else
			setBackground(color);
	}

	public SquareButton(Square square, int width) {
		this.square = square;

		if (square.getFile()%2 != square.getRank()%2) // Dark square
			color = new java.awt.Color(122, 93, 38);
		else
			color = new java.awt.Color(214, 200, 174);
		setBackground(color);
		setOpaque(true);
		setBorderPainted(false);

		try {
			InputStream stream = getClass().getResourceAsStream("/CASEFONT.TTF");
			Font uniFont = Font.createFont(Font.TRUETYPE_FONT, stream);
			uniFont = uniFont.deriveFont(48f);
			setFont(uniFont);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setPreferredSize(new Dimension(width, width));
		setMaximumSize(getPreferredSize());
		setMinimumSize(getPreferredSize());
	}

	private final Square square;
	private final java.awt.Color color;
}
