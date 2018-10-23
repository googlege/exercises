package de.homedev.chees.pieces;

import java.util.Base64;
import java.util.Set;

import de.homedev.chees.StatisticData;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Knight piece implementation
 * 
 */
public class Knight extends AbstractPiece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String KNIGHT_WHITE_SHORTCAT = "kn";
	private static final String KNIGHT_BLACK_SHORTCAT = new String(Base64.getDecoder().decode("dcqe"), CHARSET_UTF8);

	public Knight(PieceColor color, PiecePosition position) {
		super(color, position);
	}

	public Knight(PieceColor color, char x, char y) {
		super(color, x, y);
	}

	@Override
	public void validateMove(PiecePosition to, Set<PiecePosition> allPositions) {
		super.validateMove(to);
		// TODO implement move validation for current piece type
	}

	@Override
	public String getWhiteShortCut() {
		return KNIGHT_WHITE_SHORTCAT;
	}

	@Override
	public String getBlackShortCut() {
		return KNIGHT_BLACK_SHORTCAT;
	}

	@Override
	public AbstractPiece getFromShortCut(String shortCut, PiecePosition position) {
		if (KNIGHT_WHITE_SHORTCAT.equals(shortCut)) {
			return new Knight(PieceColor.WHITE, position);
		}
		if (KNIGHT_BLACK_SHORTCAT.equals(shortCut)) {
			return new Knight(PieceColor.BLACK, position);
		}
		return null;
	}

	@Override
	public void registerInStatistic(StatisticData statiaticData) {
		if (PieceColor.WHITE.equals(this.getColor())) {
			statiaticData.setWhiteKnight(statiaticData.getWhiteKnight() + 1);
		} else {
			statiaticData.setBlackKnight(statiaticData.getBlackKnight() + 1);
		}
	}

}
