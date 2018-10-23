package de.homedev.bowling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Bowling game input , output utilities
 *
 */
public final class InputOutputUtils {
	private InputOutputUtils() {

	}

	/**
	 * Input line
	 * 
	 * @param labelText
	 *            - Input text label
	 * @return input string (line)
	 */
	public static String readLine(String labelText) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				System.out.print(labelText);
				return in.readLine();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * Input integer value
	 * 
	 * @param labelText
	 *            - Input text label
	 * @return integer value
	 */
	public static int readInt(String labelText) {
		while (true) {
			try {
				return Integer.parseInt(readLine(labelText));
			} catch (RuntimeException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * Input integer value between min and max
	 * 
	 * @param labelText
	 *            - Input text label
	 * @param min
	 *            - min integer value
	 * @param max
	 *            - max integer value
	 * @return integer value
	 */
	public static int readIntBetween(String labelText, int min, int max) {
		while (true) {
			int result = readInt(labelText);
			if (result >= min && result <= max) {
				return result;
			} else {
				System.err.println("input:" + result + ". Value must be between " + min + " and " + max);
			}
		}
	}

	/**
	 * Reading frame data (firstThrow, secondThrow, thirdThrow ) with command
	 * line . Function recognize if secondThrow and thirdThrow must be input or
	 * not.
	 * 
	 * @param frameId
	 *            - frame id counting from 0
	 * @return Frame input data
	 */
	public static Frame readFrameData(int frameId) {
		while (true) {
			int firstThrow = readIntBetween("firstThrow:", 0, Frame.PINS_NUMBER);
			if (frameId != Frame.LAST_FRAME) {
				if ((firstThrow == Frame.PINS_NUMBER)) {
					return new Frame(frameId, firstThrow, null, null);
				} else {
					int secondThrow = readIntBetween("secondThrow:", 0, Frame.PINS_NUMBER - firstThrow);
					return new Frame(frameId, firstThrow, secondThrow, null);
				}
			} else {
				Integer thirdThrow = null;
				int secondThrow = 0;
				if ((firstThrow == Frame.PINS_NUMBER)) {
					secondThrow = readIntBetween("secondThrow:", 0, Frame.PINS_NUMBER);
					if ((secondThrow == Frame.PINS_NUMBER)) {
						thirdThrow = readIntBetween("thirdThrow:", 0, Frame.PINS_NUMBER);
					} else {
						thirdThrow = readIntBetween("thirdThrow:", 0, Frame.PINS_NUMBER - secondThrow);
					}
				} else {
					secondThrow = readIntBetween("secondThrow:", 0, Frame.PINS_NUMBER - firstThrow);
					if (secondThrow == Frame.PINS_NUMBER - firstThrow) {
						thirdThrow = readIntBetween("thirdThrow:", 0, Frame.PINS_NUMBER);
					}
				}
				return new Frame(frameId, firstThrow, secondThrow, thirdThrow);
			}
		}
	}

	/**
	 * Reading players data "before" the game
	 * 
	 * @return players data as a list collection
	 */
	public static List<Person> readPlayerData() {
		int playerNamber = readIntBetween("plaerNumber(max:" + BowlingGameMain.MAX_PLAYERS_NUMBER + "):", 1, BowlingGameMain.MAX_PLAYERS_NUMBER);
		List<Person> result = new ArrayList<Person>(playerNamber);
		for (int i = 0; i < playerNamber; i++) {
			String name = readLine("Player " + (i + 1) + " name:");
			result.add(new Person(i, name));
		}
		return result;
	}

	/**
	 * Prints current player history
	 * 
	 * @param history
	 *            - game player history
	 */
	public static void printHistory(List<Frame> history) {
		for (int i = 0; i < history.size(); i++) {
			System.out.println(history.get(i).toString());
		}
	}

	/**
	 * Prints current player history
	 * 
	 * @param person
	 *            - bowling game player
	 */
	public static void printHistory(Person person) {
		System.out.println("History: " + person.toString());
		printHistory(person.getHistory());
	}

}
