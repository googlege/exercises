package de.homedev.chees;

import de.homedev.chees.exception.InconsistentInputDataException;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class StatisticData {
	private int whitePawns = 0;
	private int blackPawns = 0;
	private int whiteBishops = 0;
	private int blackBishops = 0;
	private int whiteRooks = 0;
	private int blackRooks = 0;
	private int blackKing = 0;
	private int whiteKing = 0;
	private int blackKnight = 0;
	private int whiteKnight = 0;
	private int blackQueen = 0;
	private int whiteQueen = 0;

	public int getWhitePawns() {
		return whitePawns;
	}

	public void setWhitePawns(int whitePawns) {
		this.whitePawns = whitePawns;
	}

	public int getBlackPawns() {
		return blackPawns;
	}

	public void setBlackPawns(int blackPawns) {
		this.blackPawns = blackPawns;
	}

	public int getWhiteBishops() {
		return whiteBishops;
	}

	public void setWhiteBishops(int whiteBishops) {
		this.whiteBishops = whiteBishops;
	}

	public int getBlackBishops() {
		return blackBishops;
	}

	public void setBlackBishops(int blackBishops) {
		this.blackBishops = blackBishops;
	}

	public int getWhiteRooks() {
		return whiteRooks;
	}

	public void setWhiteRooks(int whiteRooks) {
		this.whiteRooks = whiteRooks;
	}

	public int getBlackRooks() {
		return blackRooks;
	}

	public void setBlackRooks(int blackRooks) {
		this.blackRooks = blackRooks;
	}

	public int getBlackKing() {
		return blackKing;
	}

	public void setBlackKing(int blackKing) {
		this.blackKing = blackKing;
	}

	public int getWhiteKing() {
		return whiteKing;
	}

	public void setWhiteKing(int whiteKing) {
		this.whiteKing = whiteKing;
	}

	public int getBlackKnight() {
		return blackKnight;
	}

	public void setBlackKnight(int blackKnight) {
		this.blackKnight = blackKnight;
	}

	public int getWhiteKnight() {
		return whiteKnight;
	}

	public void setWhiteKnight(int whiteKnight) {
		this.whiteKnight = whiteKnight;
	}

	public int getBlackQueen() {
		return blackQueen;
	}

	public void setBlackQueen(int blackQueen) {
		this.blackQueen = blackQueen;
	}

	public int getWhiteQueen() {
		return whiteQueen;
	}

	public void setWhiteQueen(int whiteQueen) {
		this.whiteQueen = whiteQueen;
	}

	@Override
	public String toString() {
		return "whitePawns=" + whitePawns + ", blackPawns=" + blackPawns + ", whiteBishops=" + whiteBishops + ", blackBishops=" + blackBishops + ", whiteRooks="
				+ whiteRooks + ", blackRooks=" + blackRooks + ", blackKing=" + blackKing + ", whiteKing=" + whiteKing + ", blackKnight=" + blackKnight
				+ ", whiteKnight=" + whiteKnight + ", blackQueen=" + blackQueen + ", whiteQueen=" + whiteQueen;
	}

	public void validate() {
		if (whitePawns > 8) {
			throw new InconsistentInputDataException("white pawns are " + whitePawns);
		}
		if (blackPawns > 8) {
			throw new InconsistentInputDataException("black pawns are " + blackPawns);
		}
		if (whiteBishops > 2) {
			throw new InconsistentInputDataException("white bishops are " + whiteBishops);
		}
		if (blackBishops > 2) {
			throw new InconsistentInputDataException("black bishops are " + blackBishops);
		}
		if (whiteKnight > 2) {
			throw new InconsistentInputDataException("white knight are " + whiteKnight);
		}
		if (blackKnight > 2) {
			throw new InconsistentInputDataException("black knight are " + blackKnight);
		}
		if (whiteRooks > 2) {
			throw new InconsistentInputDataException("white rooks are " + whiteRooks);
		}
		if (blackRooks > 2) {
			throw new InconsistentInputDataException("black rooks are " + blackRooks);
		}
		if (whiteKing != 1) {
			throw new InconsistentInputDataException("white king must be present and only one (whiteKing=" + whiteKing + ")");
		}
		if (blackKing != 1) {
			throw new InconsistentInputDataException("black king must be present and only one (blackKing=" + blackKing + ")");
		}
	}

}
