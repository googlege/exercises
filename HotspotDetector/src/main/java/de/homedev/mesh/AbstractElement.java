package de.homedev.mesh;

/**
 * AbstractElement for any kind of measurement element
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public abstract class AbstractElement implements Comparable<AbstractElement> {
	private final int id;
	private final double value;

	public AbstractElement(int id, double value) {
		super();
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public double getValue() {
		return value;
	}

	/**
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AbstractElement o) {
		return Integer.compare(id, o.id);
	}

	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		AbstractElement other = (AbstractElement) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
