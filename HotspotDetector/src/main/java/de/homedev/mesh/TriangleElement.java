package de.homedev.mesh;

/**
 * Triangle measurement element with three nodes per value
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class TriangleElement extends AbstractElement {
	private final Node node1;
	private final Node node2;
	private final Node node3;

	public TriangleElement(int id, Node node1, Node node2, Node node3, double value) {
		super(id, value);
		this.node1 = node1;
		this.node2 = node2;
		this.node3 = node3;
	}

	public Node getNode1() {
		return node1;
	}

	public Node getNode2() {
		return node2;
	}

	public Node getNode3() {
		return node3;
	}

}
