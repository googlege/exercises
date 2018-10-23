package de.homedev.chess.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.homedev.chees.ChessBoard;
import de.homedev.chees.exception.InconsistentInputDataException;
import de.homedev.chees.pieces.AbstractPiece;
import de.homedev.chees.pieces.Pawn;
import de.homedev.chees.pieces.PieceColor;
import de.homedev.chees.pieces.PiecePosition;
import de.homedev.chees.pieces.Rook;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class ChessTest {

	private ChessBoard board;

	/**
	 * Test data initialisation function
	 */
	@Before
	public void init() {
		board = new ChessBoard();
	}

	/**
	 * Tests Pawn creation
	 */
	@Test
	public void chessPawnInitOkTest() {
		board.clearBoard();
		PiecePosition position = new PiecePosition('a', '2');
		Pawn pawn = new Pawn(PieceColor.WHITE, position);
		board.putByInit(pawn);
		Assert.assertEquals(pawn, board.get(position));
	}

	/**
	 * Tests Pawn creation with error
	 */
	@Test(expected = InconsistentInputDataException.class)
	public void chessPawnInitErrorTest1() {
		board.clearBoard();
		PiecePosition position = new PiecePosition('k', '2');
		Pawn pawn = new Pawn(PieceColor.WHITE, position);
		board.putByInit(pawn);
		Assert.assertEquals(pawn, board.get(position));
	}

	@Test(expected = InconsistentInputDataException.class)
	public void chessPawnInitErrorTest2() {
		board.clearBoard();
		PiecePosition position = new PiecePosition('a', '9');
		Pawn pawn = new Pawn(PieceColor.WHITE, position);
		board.putByInit(pawn);
		Assert.assertEquals(pawn, board.get(position));
	}

	@Test(expected = InconsistentInputDataException.class)
	public void chessPawnInitErrorTest3() {
		board.clearBoard();
		PiecePosition position = new PiecePosition('a', '2');
		Pawn pawn = new Pawn(null, position);
		board.putByInit(pawn);
		Assert.assertEquals(pawn, board.get(position));
	}

	@Test(expected = InconsistentInputDataException.class)
	public void chessPawnInitErrorTest4() {
		board.clearBoard();
		PiecePosition position = new PiecePosition('a', '2');
		Pawn pawn = new Pawn(PieceColor.WHITE, null);
		board.putByInit(pawn);
		Assert.assertEquals(pawn, board.get(position));
	}

	@Test
	public void twoPawnsInitOkTest() {
		board.clearBoard();
		PiecePosition position = new PiecePosition('a', '2');
		Pawn pawn = new Pawn(PieceColor.WHITE, position);
		board.putByInit(pawn);
		Assert.assertEquals(pawn, board.get(position));
		position = new PiecePosition('b', '2');
		pawn = new Pawn(PieceColor.WHITE, position);
		board.putByInit(pawn);
		Assert.assertEquals(pawn, board.get(position));
	}

	/**
	 * Tests full board initialization with error
	 */
	@Test(expected = InconsistentInputDataException.class)
	public void twoPawnsInitErrorTest() {
		board.clearBoard();
		PiecePosition position = new PiecePosition('a', '2');
		Pawn pawn = new Pawn(PieceColor.WHITE, position);
		board.putByInit(pawn);
		Assert.assertEquals(pawn, board.get(position));
		position = new PiecePosition('a', '2');
		pawn = new Pawn(PieceColor.WHITE, position);
		board.putByInit(pawn);
	}

	/**
	 * Tests full board initialization
	 */
	@Test
	public void initByStartup() {
		board.init();
		Assert.assertNull(board.get(new PiecePosition('a', '3')));
		AbstractPiece piece = board.get(new PiecePosition('a', '1'));
		Assert.assertNotNull(piece);
		Assert.assertTrue(piece instanceof Rook);
		Assert.assertEquals(PieceColor.WHITE, piece.getColor());
	}

}