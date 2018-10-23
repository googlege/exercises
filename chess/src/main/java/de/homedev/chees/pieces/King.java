package de.homedev.chees.pieces;

import java.util.Base64;
import java.util.Set;

import de.homedev.chees.StatisticData;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        King piece implementation
 * 
 * 
 */
public class King extends AbstractPiece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String KING_WHITE_SHORTCAT = "ki";
	private static final String KING_BLACK_SHORTCAT = new String(Base64.getDecoder().decode("xLHKng=="), CHARSET_UTF8);;

	public King(PieceColor color, PiecePosition position) {
		super(color, position);
	}

	public King(PieceColor color, char x, char y) {
		super(color, x, y);
	}

	@Override
	public void validateMove(PiecePosition to, Set<PiecePosition> allPositions) {
		super.validateMove(to);
		// TODO implement move validation for current piece type
	}

	@Override
	public String getWhiteShortCut() {
		return KING_WHITE_SHORTCAT;
	}

	@Override
	public String getBlackShortCut() {
		return KING_BLACK_SHORTCAT;
	}

	@Override
	public AbstractPiece getFromShortCut(String shortCut, PiecePosition position) {
		if (KING_WHITE_SHORTCAT.equals(shortCut)) {
			return new King(PieceColor.WHITE, position);
		}
		if (KING_BLACK_SHORTCAT.equals(shortCut)) {
			return new King(PieceColor.BLACK, position);
		}
		return null;
	}

	@Override
	public void registerInStatistic(StatisticData statiaticData) {
		if (PieceColor.WHITE.equals(this.getColor())) {
			statiaticData.setWhiteKing(statiaticData.getWhiteKing() + 1);
		} else {
			statiaticData.setBlackKing(statiaticData.getBlackKing() + 1);
		}
	}
}
