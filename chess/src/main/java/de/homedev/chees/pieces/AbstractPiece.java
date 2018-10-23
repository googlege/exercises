package de.homedev.chees.pieces;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Set;

import de.homedev.chees.StatisticData;
import de.homedev.chees.exception.InconsistentInputDataException;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 * 
 *        https://en.wikipedia.org/wiki/Chess_piece
 * 
 *        Abstract piece object with common piece functionality
 * 
 *
 */
public abstract class AbstractPiece implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	private final PieceColor color;
	private PiecePosition position;

	/**
	 * Abstract Piece Constructor
	 * 
	 * @param color
	 * @param position
	 */
	public AbstractPiece(PieceColor color, PiecePosition position) {
		super();
		this.color = color;
		this.position = position;
		validate();
	}

	/**
	 * Abstract Piece Constructor
	 * 
	 * @param color
	 * @param x
	 * @param y
	 */
	public AbstractPiece(PieceColor color, char x, char y) {
		this(color, new PiecePosition(x, y));
	}

	/**
	 * Returns piece color
	 * 
	 * @return
	 */
	public PieceColor getColor() {
		return color;
	}

	/**
	 * Returns piece position
	 * 
	 * @return
	 */
	public PiecePosition getPosition() {
		return position;
	}

	/**
	 * 
	 * @param position
	 */
	public void setPosition(PiecePosition position) {
		if (position == null) {
			throw new InconsistentInputDataException("Piece position can not be NULL");
		}
		this.position = position;
	}

	/**
	 * Common input data validation functionality
	 */
	public void validate() {
		if (this.color == null) {
			throw new InconsistentInputDataException("Piece color can not be NULL");
		}
		if (this.position == null) {
			throw new InconsistentInputDataException("Piece position can not be NULL");
		}
		position.validate();
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "color=" + color + ", position=" + position;
	}

	/**
	 * Common move validation functionality
	 * 
	 * @param to
	 */
	protected void validateMove(PiecePosition to) {
		if (to == null) {
			throw new InconsistentInputDataException("Piece position 'to' can not be NULL");
		}
	};

	/**
	 * Concrete piece move validation Only Knight can jump and we need positions
	 * of all others pieces
	 * 
	 * @param to
	 *            - move to position
	 * @param allPiecesBlackAndWhite
	 *            - only Knight can jump
	 */
	public abstract void validateMove(PiecePosition to, Set<PiecePosition> allPositions);

	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	/**
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractPiece other = (AbstractPiece) obj;
		if (color != other.color)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	/**
	 * Returns piece short cut
	 * 
	 * @return
	 */
	public String getShortCut() {
		if (color.equals(PieceColor.WHITE)) {
			return getWhiteShortCut();
		}
		return getBlackShortCut();
	}

	/**
	 * Creates Piece if possible from pice short cut and pice position
	 * 
	 * @param shortCut
	 * @param position
	 * @return
	 */
	public abstract AbstractPiece getFromShortCut(String shortCut, PiecePosition position);

	/**
	 * Returns White Short Cut
	 * 
	 * @return
	 */
	public abstract String getWhiteShortCut();

	/**
	 * Returns Black Short Cut
	 * 
	 * @return
	 */
	public abstract String getBlackShortCut();

	/**
	 * Register current piece in statistic data
	 * 
	 * @param statiaticData
	 */
	public abstract void registerInStatistic(StatisticData statiaticData);
}
