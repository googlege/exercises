package de.homedev.chees.pieces;

import java.util.Base64;
import java.util.Set;

import de.homedev.chees.StatisticData;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Pawn piece implementation
 * 
 */
public class Pawn extends AbstractPiece {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PAWN_WHITE_SHORTCAT = "pa";
	private static final String PAWN_BLACK_SHORTCAT = new String(Base64.getDecoder().decode("yZBk"), CHARSET_UTF8);

	public Pawn(PieceColor color, PiecePosition position) {
		super(color, position);
	}

	public Pawn(PieceColor color, char x, char y) {
		super(color, x, y);
	}

	@Override
	public void validateMove(PiecePosition to, Set<PiecePosition> allPositions) {
		super.validateMove(to);
		// TODO implement move validation for current piece type
	}

	@Override
	public String getWhiteShortCut() {
		return PAWN_WHITE_SHORTCAT;
	}

	@Override
	public String getBlackShortCut() {
		return PAWN_BLACK_SHORTCAT;
	}

	@Override
	public AbstractPiece getFromShortCut(String shortCut, PiecePosition position) {
		if (PAWN_WHITE_SHORTCAT.equals(shortCut)) {
			return new Pawn(PieceColor.WHITE, position);
		}
		if (PAWN_BLACK_SHORTCAT.equals(shortCut)) {
			return new Pawn(PieceColor.BLACK, position);
		}
		return null;
	}

	@Override
	public void registerInStatistic(StatisticData statiaticData) {
		if (PieceColor.WHITE.equals(this.getColor())) {
			statiaticData.setWhitePawns(statiaticData.getWhitePawns() + 1);
		} else {
			statiaticData.setBlackPawns(statiaticData.getBlackPawns() + 1);
		}
	}

}
