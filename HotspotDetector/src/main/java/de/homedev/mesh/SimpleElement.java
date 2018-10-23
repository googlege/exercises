package de.homedev.mesh;

/**
 * Simple measurement element with one node per value
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class SimpleElement extends AbstractElement {
	private final Node node;

	public SimpleElement(int id, Node node, double value) {
		super(id, value);
		this.node = node;
	}

	public SimpleElement(int id, int nodeId, int x1, int y1, double value) {
		this(id, new Node(nodeId, x1, y1), value);
	}

	public Node getNode() {
		return node;
	}

}
