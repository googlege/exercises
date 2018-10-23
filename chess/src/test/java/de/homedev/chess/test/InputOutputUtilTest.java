package de.homedev.chess.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.homedev.chees.ChessBoard;
import de.homedev.chees.exception.InconsistentInputDataException;
import de.homedev.chees.ioutils.InputOutputUtils;
import de.homedev.chees.pieces.AbstractPiece;
import de.homedev.chees.pieces.Bishop;
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
public class InputOutputUtilTest {

	private ChessBoard board;
	public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

	/**
	 * Test data initialisation function
	 */
	@Before
	public void init() {
		board = new ChessBoard();
	}

	public File getDBDir() {
		File runDir = new File(System.getProperty("user.dir"));
		return new File(runDir, "etc");
	}

	/**
	 * Reading any file with 'fileName' in database folder
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public InputStream getFileInputStream(String fileName) throws FileNotFoundException {
		final File currentDBDir = getDBDir();
		if (!currentDBDir.exists() || !currentDBDir.isDirectory()) {
			throw new RuntimeException("Database directory" + currentDBDir.getAbsolutePath() + " does not exist");
		}
		File file = new File(currentDBDir, fileName);
		if (!file.exists() || file.isDirectory()) {
			throw new RuntimeException("File " + file.getAbsolutePath() + " does not exist");
		}
		return new FileInputStream(file);
	}

	/**
	 * Test if files chess-startup.txt,chess-01.txt,chess-02.txt,chess-03.txt
	 * are present
	 * 
	 * @throws IOException
	 */
	@Test
	public void testIfFileExist() throws IOException {
		InputStream is = getFileInputStream("chess-startup.txt");
		is.close();
		is = getFileInputStream("chess-01.txt");
		is.close();
		is = getFileInputStream("chess-02.txt");
		is.close();
		is = getFileInputStream("chess-03.txt");
		is.close();
	}

	/**
	 * Test reading from file 'chess-startup.txt'
	 * 
	 * @throws IOException
	 */
	@Test
	public void testReadingFile1() throws IOException {
		InputStream is = getFileInputStream("chess-startup.txt");
		Map<PiecePosition, AbstractPiece> result = InputOutputUtils.readFile(is);
		board.init(result);
		Assert.assertNull(board.get(new PiecePosition('a', '3')));
		AbstractPiece piece = board.get(new PiecePosition('a', '1'));
		Assert.assertNotNull(piece);
		Assert.assertTrue(piece instanceof Rook);
		Assert.assertEquals(PieceColor.WHITE, piece.getColor());
		is.close();
	}

	/**
	 * Test reading from file 'chess-01.txt'
	 * 
	 * @throws IOException
	 */
	@Test
	public void testReadingFile2() throws IOException {
		InputStream is = getFileInputStream("chess-01.txt");
		Map<PiecePosition, AbstractPiece> result = InputOutputUtils.readFile(is);
		board.init(result);
		Assert.assertNull(board.get(new PiecePosition('a', '3')));
		AbstractPiece piece = board.get(new PiecePosition('c', '3'));
		Assert.assertNotNull(piece);
		Assert.assertTrue(piece instanceof Pawn);
		Assert.assertEquals(PieceColor.WHITE, piece.getColor());
		is.close();
	}

	/**
	 * Test reading from file 'chess-02.txt'
	 * 
	 * @throws IOException
	 */
	@Test
	public void testReadingFile3() throws IOException {
		InputStream is = getFileInputStream("chess-02.txt");
		Map<PiecePosition, AbstractPiece> result = InputOutputUtils.readFile(is);
		board.init(result);
		Assert.assertNull(board.get(new PiecePosition('a', '2')));
		AbstractPiece piece = board.get(new PiecePosition('a', '3'));
		Assert.assertNotNull(piece);
		Assert.assertTrue(piece instanceof Pawn);
		is.close();
	}

	/**
	 * Test reading from file 'chess-03.txt'
	 * 
	 * @throws IOException
	 */
	@Test
	public void testReadingFile4() throws IOException {
		InputStream is = getFileInputStream("chess-03.txt");
		Map<PiecePosition, AbstractPiece> result = InputOutputUtils.readFile(is);
		board.init(result);
		Assert.assertNull(board.get(new PiecePosition('a', '2')));
		AbstractPiece piece = board.get(new PiecePosition('c', '3'));
		Assert.assertNotNull(piece);
		Assert.assertTrue(piece instanceof Pawn);
		piece = board.get(new PiecePosition('e', '3'));
		Assert.assertTrue(piece instanceof Bishop);
		is.close();
	}

	/**
	 * Testing writing to file
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWriteToFile() throws IOException {
		board.init();
		final File currentDBDir = getDBDir();
		if (!currentDBDir.exists() || !currentDBDir.isDirectory()) {
			throw new RuntimeException("Database directory" + currentDBDir.getAbsolutePath() + " does not exist");
		}
		File file = new File(currentDBDir, "my_chess-startup.txt");
		OutputStream os = new FileOutputStream(file, false);
		String result = InputOutputUtils.writeFile(board.getCollection());
		os.write(result.getBytes(CHARSET_UTF8));
		os.close();
	}

	/**
	 * Full Test with writing and reading from same file
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWriteAndRead1() throws IOException {
		// init board
		board.init();
		String fileName = "my_chess-startup.txt";
		// ------------whiting-------------------------------------------
		final File currentDBDir = getDBDir();
		if (!currentDBDir.exists() || !currentDBDir.isDirectory()) {
			throw new RuntimeException("Database directory" + currentDBDir.getAbsolutePath() + " does not exist");
		}
		File file = new File(currentDBDir, fileName);
		OutputStream os = new FileOutputStream(file, false);
		String result1 = InputOutputUtils.writeFile(board.getCollection());
		os.write(result1.getBytes(CHARSET_UTF8));
		os.close();
		// ------------reading-------------------------------------------
		InputStream is = getFileInputStream(fileName);
		Map<PiecePosition, AbstractPiece> result2 = InputOutputUtils.readFile(is);
		board.init(result2);
		Assert.assertNull(board.get(new PiecePosition('a', '3')));
		AbstractPiece piece = board.get(new PiecePosition('a', '1'));
		Assert.assertNotNull(piece);
		Assert.assertTrue(piece instanceof Rook);
		Assert.assertEquals(PieceColor.WHITE, piece.getColor());
		is.close();
	}

	/**
	 * Full Test with writing and reading from same file
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWriteAndRead2() throws IOException {
		// init board
		InputStream is1 = getFileInputStream("chess-01.txt");
		Map<PiecePosition, AbstractPiece> result = InputOutputUtils.readFile(is1);
		board.init(result);
		is1.close();
		String fileName = "my_chess-01.txt";
		// ------------whiting-------------------------------------------
		final File currentDBDir = getDBDir();
		if (!currentDBDir.exists() || !currentDBDir.isDirectory()) {
			throw new RuntimeException("Database directory" + currentDBDir.getAbsolutePath() + " does not exist");
		}
		File file = new File(currentDBDir, fileName);
		OutputStream os = new FileOutputStream(file, false);
		String result1 = InputOutputUtils.writeFile(board.getCollection());
		os.write(result1.getBytes(CHARSET_UTF8));
		os.close();
		// ------------reading-------------------------------------------
		InputStream is = getFileInputStream(fileName);
		Map<PiecePosition, AbstractPiece> result2 = InputOutputUtils.readFile(is);
		board.init(result2);
		Assert.assertNull(board.get(new PiecePosition('a', '3')));
		AbstractPiece piece = board.get(new PiecePosition('c', '3'));
		Assert.assertNotNull(piece);
		Assert.assertTrue(piece instanceof Pawn);
		Assert.assertEquals(PieceColor.WHITE, piece.getColor());
		is.close();
	}

	/**
	 * Full Test with writing and reading from same file
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWriteAndRead3() throws IOException {
		// init board
		InputStream is1 = getFileInputStream("chess-02.txt");
		Map<PiecePosition, AbstractPiece> result = InputOutputUtils.readFile(is1);
		board.init(result);
		is1.close();
		String fileName = "my_chess-02.txt";
		// ------------whiting-------------------------------------------
		final File currentDBDir = getDBDir();
		if (!currentDBDir.exists() || !currentDBDir.isDirectory()) {
			throw new RuntimeException("Database directory" + currentDBDir.getAbsolutePath() + " does not exist");
		}
		File file = new File(currentDBDir, fileName);
		OutputStream os = new FileOutputStream(file, false);
		String result1 = InputOutputUtils.writeFile(board.getCollection());
		os.write(result1.getBytes(CHARSET_UTF8));
		os.close();
		// ------------reading-------------------------------------------
		InputStream is = getFileInputStream(fileName);
		Map<PiecePosition, AbstractPiece> result2 = InputOutputUtils.readFile(is);
		board.init(result2);
		Assert.assertNull(board.get(new PiecePosition('a', '2')));
		AbstractPiece piece = board.get(new PiecePosition('a', '3'));
		Assert.assertNotNull(piece);
		Assert.assertTrue(piece instanceof Pawn);
		is.close();
	}

	/**
	 * Full Test with writing and reading from same file
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWriteAndRead4() throws IOException {
		// init board
		InputStream is1 = getFileInputStream("chess-03.txt");
		Map<PiecePosition, AbstractPiece> result = InputOutputUtils.readFile(is1);
		board.init(result);
		is1.close();
		String fileName = "my_chess-03.txt";
		// ------------whiting-------------------------------------------
		final File currentDBDir = getDBDir();
		if (!currentDBDir.exists() || !currentDBDir.isDirectory()) {
			throw new RuntimeException("Database directory" + currentDBDir.getAbsolutePath() + " does not exist");
		}
		File file = new File(currentDBDir, fileName);
		OutputStream os = new FileOutputStream(file, false);
		String result1 = InputOutputUtils.writeFile(board.getCollection());
		os.write(result1.getBytes(CHARSET_UTF8));
		os.close();
		// ------------reading-------------------------------------------
		InputStream is = getFileInputStream(fileName);
		Map<PiecePosition, AbstractPiece> result2 = InputOutputUtils.readFile(is);
		board.init(result2);
		Assert.assertNull(board.get(new PiecePosition('a', '2')));
		AbstractPiece piece = board.get(new PiecePosition('c', '3'));
		Assert.assertNotNull(piece);
		Assert.assertTrue(piece instanceof Pawn);
		piece = board.get(new PiecePosition('e', '3'));
		Assert.assertTrue(piece instanceof Bishop);
		is.close();
	}

	/**
	 * We have got 9 white pawns
	 * 
	 * @throws IOException
	 */
	@Test(expected = InconsistentInputDataException.class)
	public void testErrorInitTest() throws IOException {
		// init board
		InputStream is = getFileInputStream("chess-startup.txt");
		Map<PiecePosition, AbstractPiece> result = InputOutputUtils.readFile(is);
		is.close();
		PiecePosition position = new PiecePosition('a', '3');
		result.put(position, new Pawn(PieceColor.WHITE, position));
		board.init(result);
	}

}
