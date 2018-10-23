package de.homedev.chees.pieces;

import java.util.Base64;
import java.util.Set;

import de.homedev.chees.StatisticData;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Queen piece implementation
 * 
 */
public class Queen extends AbstractPiece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String QUEEN_WHITE_SHORTCAT = "qu";
	private static final String QUEEN_BLACK_SHORTCAT = new String(Base64.getDecoder().decode("bmI="), CHARSET_UTF8);

	public Queen(PieceColor color, PiecePosition position) {
		super(color, position);
	}

	public Queen(PieceColor color, char x, char y) {
		super(color, x, y);
	}

	@Override
	public void validateMove(PiecePosition to, Set<PiecePosition> allPositions) {
		super.validateMove(to);
		// TODO implement move validation for current piece type
	}

	@Override
	public String getWhiteShortCut() {
		return QUEEN_WHITE_SHORTCAT;
	}

	@Override
	public String getBlackShortCut() {
		return QUEEN_BLACK_SHORTCAT;
	}

	@Override
	public AbstractPiece getFromShortCut(String shortCut, PiecePosition position) {
		if (QUEEN_WHITE_SHORTCAT.equals(shortCut)) {
			return new Queen(PieceColor.WHITE, position);
		}
		if (QUEEN_BLACK_SHORTCAT.equals(shortCut)) {
			return new Queen(PieceColor.BLACK, position);
		}
		return null;
	}

	@Override
	public void registerInStatistic(StatisticData statiaticData) {
		if (PieceColor.WHITE.equals(this.getColor())) {
			statiaticData.setWhiteQueen(statiaticData.getWhiteQueen() + 1);
		} else {
			statiaticData.setBlackQueen(statiaticData.getBlackQueen() + 1);
		}
	}

}
