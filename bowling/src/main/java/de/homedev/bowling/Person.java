package de.homedev.bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Player Data
 *
 */
public class Person {
	/**
	 * player id
	 */
	private final int id;
	/**
	 * player name
	 */
	private final String name;
	/**
	 * player history
	 */
	private final List<Frame> history;

	public Person(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.history = new ArrayList<Frame>(Frame.LAST_FRAME + 1);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Frame> getHistory() {
		return history;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name;
	}

}
