package de.homedev.chees;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.homedev.chees.exception.InconsistentInputDataException;
import de.homedev.chees.pieces.AbstractPiece;
import de.homedev.chees.pieces.Bishop;
import de.homedev.chees.pieces.King;
import de.homedev.chees.pieces.Knight;
import de.homedev.chees.pieces.Pawn;
import de.homedev.chees.pieces.PieceColor;
import de.homedev.chees.pieces.PiecePosition;
import de.homedev.chees.pieces.Queen;
import de.homedev.chees.pieces.Rook;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class ChessBoard {
	/**
	 * we could make different arrays for black and white pieces or only one. we
	 * could validate data by using key value in the map object or we can go
	 * throw all elements, we could sort array list etc.
	 */
	private Map<PiecePosition, AbstractPiece> allPiecesBlackAndWhite = new HashMap<PiecePosition, AbstractPiece>();

	public AbstractPiece move(PiecePosition from, PiecePosition to) {
		AbstractPiece pieseFrom = allPiecesBlackAndWhite.get(from);
		if (pieseFrom == null) {
			throw new InconsistentInputDataException("Can't find any piese on position:" + from);
		}
		pieseFrom.validateMove(to, allPiecesBlackAndWhite.keySet());
		AbstractPiece pieseTo = allPiecesBlackAndWhite.get(to);
		if (pieseTo != null) {
			if (pieseTo.getColor().equals(pieseFrom.getColor())) {
				throw new InconsistentInputDataException("You can't take own piese:" + pieseTo.toString());
			}
			allPiecesBlackAndWhite.remove(to);
		}
		allPiecesBlackAndWhite.remove(from);
		pieseFrom.setPosition(to);
		allPiecesBlackAndWhite.put(to, pieseFrom);
		return pieseFrom;
	}

	public void putByInit(AbstractPiece piece) {
		PiecePosition position = piece.getPosition();
		AbstractPiece pieceOnPosition = allPiecesBlackAndWhite.get(position);
		if (pieceOnPosition != null) {
			throw new InconsistentInputDataException("Position:" + position.toString() + " is busy");
		}
		allPiecesBlackAndWhite.put(position, piece);
	}

	public AbstractPiece get(PiecePosition position) {
		return allPiecesBlackAndWhite.get(position);
	}

	public AbstractPiece remove(PiecePosition position) {
		return allPiecesBlackAndWhite.remove(position);
	}

	public void clearBoard() {
		allPiecesBlackAndWhite.clear();
	}

	public void init() {
		allPiecesBlackAndWhite.clear();
		putByInit(new Pawn(PieceColor.WHITE, 'a', '2'));
		putByInit(new Pawn(PieceColor.WHITE, 'b', '2'));
		putByInit(new Pawn(PieceColor.WHITE, 'c', '2'));
		putByInit(new Pawn(PieceColor.WHITE, 'd', '2'));
		putByInit(new Pawn(PieceColor.WHITE, 'e', '2'));
		putByInit(new Pawn(PieceColor.WHITE, 'f', '2'));
		putByInit(new Pawn(PieceColor.WHITE, 'g', '2'));
		putByInit(new Pawn(PieceColor.WHITE, 'h', '2'));

		putByInit(new Rook(PieceColor.WHITE, 'a', '1'));
		putByInit(new Knight(PieceColor.WHITE, 'b', '1'));
		putByInit(new Bishop(PieceColor.WHITE, 'c', '1'));
		putByInit(new Queen(PieceColor.WHITE, 'd', '1'));
		putByInit(new King(PieceColor.WHITE, 'e', '1'));
		putByInit(new Bishop(PieceColor.WHITE, 'f', '1'));
		putByInit(new Knight(PieceColor.WHITE, 'g', '1'));
		putByInit(new Rook(PieceColor.WHITE, 'h', '1'));

		putByInit(new Pawn(PieceColor.BLACK, 'a', '7'));
		putByInit(new Pawn(PieceColor.BLACK, 'b', '7'));
		putByInit(new Pawn(PieceColor.BLACK, 'c', '7'));
		putByInit(new Pawn(PieceColor.BLACK, 'd', '7'));
		putByInit(new Pawn(PieceColor.BLACK, 'e', '7'));
		putByInit(new Pawn(PieceColor.BLACK, 'f', '7'));
		putByInit(new Pawn(PieceColor.BLACK, 'g', '7'));
		putByInit(new Pawn(PieceColor.BLACK, 'h', '7'));

		putByInit(new Rook(PieceColor.BLACK, 'a', '8'));
		putByInit(new Knight(PieceColor.BLACK, 'b', '8'));
		putByInit(new Bishop(PieceColor.BLACK, 'c', '8'));
		putByInit(new Queen(PieceColor.BLACK, 'd', '8'));
		putByInit(new King(PieceColor.BLACK, 'e', '8'));
		putByInit(new Bishop(PieceColor.BLACK, 'f', '8'));
		putByInit(new Knight(PieceColor.BLACK, 'g', '8'));
		putByInit(new Rook(PieceColor.BLACK, 'h', '8'));

		validate();

	}

	public Collection<AbstractPiece> getCollection() {
		return allPiecesBlackAndWhite.values();
	}

	public void init(Map<PiecePosition, AbstractPiece> allPiecesBlackAndWhite) {
		this.allPiecesBlackAndWhite = allPiecesBlackAndWhite;
		validate();
	}

	/**
	 * verify that only the correct number of pieces are added. For example:
	 * There are only 8 white pawns allowed...
	 */
	public void validate() {
		StatisticData statisticData = new StatisticData();
		for (AbstractPiece piece : allPiecesBlackAndWhite.values()) {
			piece.registerInStatistic(statisticData);
		}
		// System.out.println(statiaticData);
		statisticData.validate();
	}

}
