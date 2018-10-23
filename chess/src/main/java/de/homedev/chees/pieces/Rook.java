package de.homedev.chees.pieces;

import java.util.Base64;
import java.util.Set;

import de.homedev.chees.StatisticData;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Rook piece implementation
 * 
 */
public class Rook extends AbstractPiece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ROCK_WHITE_SHORTCAT = "ro";
	private static final String ROCK_BLACK_SHORTCAT = new String(Base64.getDecoder().decode("b8m5"), CHARSET_UTF8);

	public Rook(PieceColor color, PiecePosition position) {
		super(color, position);
	}

	public Rook(PieceColor color, char x, char y) {
		super(color, x, y);
	}

	@Override
	public void validateMove(PiecePosition to, Set<PiecePosition> allPositions) {
		super.validateMove(to);
		// TODO implement move validation for current piece type
	}

	@Override
	public String getWhiteShortCut() {
		return ROCK_WHITE_SHORTCAT;
	}

	@Override
	public String getBlackShortCut() {
		return ROCK_BLACK_SHORTCAT;
	}

	@Override
	public AbstractPiece getFromShortCut(String shortCut, PiecePosition position) {
		if (ROCK_WHITE_SHORTCAT.equals(shortCut)) {
			return new Rook(PieceColor.WHITE, position);
		}
		if (ROCK_BLACK_SHORTCAT.equals(shortCut)) {
			return new Rook(PieceColor.BLACK, position);
		}
		return null;
	}

	@Override
	public void registerInStatistic(StatisticData statiaticData) {
		if (PieceColor.WHITE.equals(this.getColor())) {
			statiaticData.setWhiteRooks(statiaticData.getWhiteRooks() + 1);
		} else {
			statiaticData.setBlackRooks(statiaticData.getBlackRooks() + 1);
		}
	}

}
