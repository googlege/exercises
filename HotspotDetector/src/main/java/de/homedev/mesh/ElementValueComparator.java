package de.homedev.mesh;

import java.util.Comparator;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 * @param <T>
 */
public class ElementValueComparator<T extends AbstractElement> implements Comparator<T> {
	/**
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(T o1, T o2) {
		Double d1 = o1.getValue();
		Double d2 = o2.getValue();
		return d2.compareTo(d1);
	}

}
