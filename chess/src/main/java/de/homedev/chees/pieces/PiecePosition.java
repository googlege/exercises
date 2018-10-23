package de.homedev.chees.pieces;

import java.io.Serializable;

import de.homedev.chees.exception.InconsistentInputDataException;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Piece Position
 * 
 *
 */
public class PiecePosition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final char x;
	private final char y;

	public PiecePosition(char x, char y) {
		super();
		this.x = Character.toLowerCase(x);
		this.y = y;
	}

	public char getX() {
		return x;
	}

	public char getY() {
		return y;
	}

	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		PiecePosition other = (PiecePosition) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	private void validateX(char x) {
		if ((x == 'a') || (x == 'b') || (x == 'c') || (x == 'd') || (x == 'e') || (x == 'f') || (x == 'g') || (x == 'h')) {
			return;
		}
		throw new InconsistentInputDataException("x=" + x + ". Value must be 'a  b  c  d  e  f  g  h' ");
	}

	private void validateY(char y) {
		if ((y == '1') || (y == '2') || (y == '3') || (y == '4') || (y == '5') || (y == '6') || (y == '7') || (y == '8')) {
			return;
		}
		throw new InconsistentInputDataException("y=" + y + ". Value must be between 1 and 8");
	}

	public void validate() {
		validateX(this.x);
		validateY(this.y);
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "x=" + x + ", y=" + y;
	}

}
