package de.homedev.chees.pieces;

import java.util.Base64;
import java.util.Set;

import de.homedev.chees.StatisticData;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Bishop piece implementation
 * 
 * 
 */
public class Bishop extends AbstractPiece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String BISHOP_WHITE_SHORTCAT = "bi";
	private static final String BISHOP_BLACK_SHORTCAT = new String(Base64.getDecoder().decode("xLFx"), CHARSET_UTF8);

	public Bishop(PieceColor color, PiecePosition position) {
		super(color, position);
	}

	public Bishop(PieceColor color, char x, char y) {
		super(color, x, y);
	}

	/**
	 * 
	 * @see de.homedev.chees.pieces.AbstractPiece#validateMove(de.homedev.chees.pieces.PiecePosition,
	 *      java.util.Set)
	 */
	@Override
	public void validateMove(PiecePosition to, Set<PiecePosition> allPositions) {
		super.validateMove(to);
		// TODO implement move validation for current piece type
	}

	@Override
	public String getWhiteShortCut() {
		return BISHOP_WHITE_SHORTCAT;
	}

	@Override
	public String getBlackShortCut() {
		return BISHOP_BLACK_SHORTCAT;
	}

	@Override
	public AbstractPiece getFromShortCut(String shortCut, PiecePosition position) {
		if (BISHOP_WHITE_SHORTCAT.equals(shortCut)) {
			return new Bishop(PieceColor.WHITE, position);
		}
		if (BISHOP_BLACK_SHORTCAT.equals(shortCut)) {
			return new Bishop(PieceColor.BLACK, position);
		}
		return null;
	}

	@Override
	public void registerInStatistic(StatisticData statiaticData) {
		if (PieceColor.WHITE.equals(this.getColor())) {
			statiaticData.setWhiteBishops(statiaticData.getWhiteBishops() + 1);
		} else {
			statiaticData.setBlackBishops(statiaticData.getBlackBishops() + 1);
		}
	}

}
