package de.homedev.chess.test;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class Utf8ToBytes {
	public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	public static final Charset CHARSET_ASCII = Charset.forName("ISO-8859-1");
	private static final String BISHOP_BLACK_SHORTCAT = "ıq";
	private static final String KING_BLACK_SHORTCAT = "ıʞ";
	private static final String KNIGHT_BLACK_SHORTCAT = "uʞ";
	private static final String PAWN_BLACK_SHORTCAT = "ɐd";
	private static final String QUEEN_BLACK_SHORTCAT = "nb";
	private static final String ROCK_BLACK_SHORTCAT = "oɹ";

	// private static final String s=new String(bytes1,CHARSET_UTF8);
	public static void main(String[] args) {
		System.out.println(utf8ToBytes("BISHOP_BLACK_SHORTCAT", BISHOP_BLACK_SHORTCAT));
		System.out.println(utf8ToBytes("KING_BLACK_SHORTCAT", KING_BLACK_SHORTCAT));
		System.out.println(utf8ToBytes("KNIGHT_BLACK_SHORTCAT", KNIGHT_BLACK_SHORTCAT));
		System.out.println(utf8ToBytes("PAWN_BLACK_SHORTCAT", PAWN_BLACK_SHORTCAT));
		System.out.println(utf8ToBytes("QUEEN_BLACK_SHORTCAT", QUEEN_BLACK_SHORTCAT));
		System.out.println(utf8ToBytes("ROCK_BLACK_SHORTCAT", ROCK_BLACK_SHORTCAT));
	}

	public static String utf8ToBytes(String label, String str) {
		StringBuilder result = new StringBuilder(200);
		result.append(label).append(" = ").append("new String(Base64.getDecoder().decode(");
		byte[] bytes = str.getBytes(CHARSET_UTF8);
		String encodedBASE64 = new String(Base64.getEncoder().encode(bytes), CHARSET_ASCII);
		result.append('"').append(encodedBASE64).append('"').append("),CHARSET_UTF8);");
		return result.toString();
	}
	/*
	 * BISHOP_BLACK_SHORTCAT = new
	 * String(Base64.getDecoder().decode("xLFx"),CHARSET_UTF8);
	 * KING_BLACK_SHORTCAT = new
	 * String(Base64.getDecoder().decode("xLHKng=="),CHARSET_UTF8);
	 * KNIGHT_BLACK_SHORTCAT = new
	 * String(Base64.getDecoder().decode("dcqe"),CHARSET_UTF8);
	 * PAWN_BLACK_SHORTCAT = new
	 * String(Base64.getDecoder().decode("yZBk"),CHARSET_UTF8);
	 * QUEEN_BLACK_SHORTCAT = new
	 * String(Base64.getDecoder().decode("bmI="),CHARSET_UTF8);
	 * ROCK_BLACK_SHORTCAT = new
	 * String(Base64.getDecoder().decode("b8m5"),CHARSET_UTF8);
	 */
}
