package de.homedev.chees.ioutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Chess game input , output utilities
 * 
 */
public final class InputOutputUtils {

	public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	private static final char[] X_VALUES = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
	private static final AbstractPiece[] PIECE_VALUES = { new Pawn(PieceColor.WHITE, 'a', '2'), new Bishop(PieceColor.WHITE, 'a', '2'),
			new King(PieceColor.WHITE, 'a', '2'), new Knight(PieceColor.WHITE, 'a', '2'), new Queen(PieceColor.WHITE, 'a', '2'),
			new Rook(PieceColor.WHITE, 'a', '2') };
	private static final String LINE_SEPARATOR = "\r\n";

	private InputOutputUtils() {

	}

	/**
	 * Reading all positions from data file
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Map<PiecePosition, AbstractPiece> readFile(InputStream is) throws IOException {
		Map<PiecePosition, AbstractPiece> result = new HashMap<PiecePosition, AbstractPiece>();
		final BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSET_UTF8));
		String line = null;
		while ((line = br.readLine()) != null) {
			int index = -1;
			String rest = line.trim();
			int x = 0;
			char y = '9';
			while ((index = rest.indexOf('|')) != -1) {
				String value = rest.substring(0, index).trim();
				if (!value.isEmpty()) {
					if (x == 0) {
						y = value.charAt(0);
					} else {
						AbstractPiece pice = getPiece(value, x - 1, y);
						result.put(pice.getPosition(), pice);
					}
				}
				x++;
				rest = rest.substring(index + 1);
			}
		}
		return result;
	}

	private static AbstractPiece getPiece(String shortCut, int x, char y) {
		PiecePosition position = new PiecePosition(X_VALUES[x], y);
		for (AbstractPiece pice : PIECE_VALUES) {
			AbstractPiece result = pice.getFromShortCut(shortCut, position);
			if (result != null) {
				return result;
			}
		}
		throw new RuntimeException("can't recognise shortCut at position:" + position.toString());
	}

	/**
	 * Writing all positions in the chess data file
	 * 
	 * @param allPiecesBlackAndWhite
	 * @return
	 */
	public static String writeFile(Collection<AbstractPiece> allPiecesBlackAndWhite) {
		Object[][] allBordPositions = new Object[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				allBordPositions[i][j] = null;
			}
		}
		for (AbstractPiece piece : allPiecesBlackAndWhite) {
			PiecePosition position = piece.getPosition();
			int y = Math.abs(Integer.parseInt(Character.toString(position.getY())) - 8);
			for (int x = 0; x < X_VALUES.length; x++) {
				if (X_VALUES[x] == position.getX()) {
					allBordPositions[y][x] = piece;
					break;
				}
			}
		}
		StringBuffer result = new StringBuffer(8 * 9 * 2);
		for (int i = 0; i < 8; i++) {
			int y = Math.abs(i - 8);
			result.append(y).append(" |");
			for (int j = 0; j < 8; j++) {
				if (allBordPositions[i][j] == null) {
					result.append("  ").append('|');
				} else {
					String shortCat = ((AbstractPiece) allBordPositions[i][j]).getShortCut();
					result.append(shortCat).append('|');
				}
			}
			result.append(LINE_SEPARATOR);
		}
		result.append("   a  b  c  d  e  f  g  h").append(LINE_SEPARATOR);
		return result.toString();
	}

}
