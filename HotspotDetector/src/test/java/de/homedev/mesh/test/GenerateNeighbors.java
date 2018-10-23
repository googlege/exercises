package de.homedev.mesh.test;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class GenerateNeighbors {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder(100);
		int delta = 0;
		for (int i = 0; i < 10; i++) {
			sb.append("int[] x").append(i + 1).append("= {");
			for (int j = delta; j < delta + 20; j++) {
				sb.append(j % 2 == 0 ? j + 1 : j - 1).append(',');
			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append("};\r\n");
			delta = delta + 20;
		}

		for (int i = 0; i < 20; i = i + 2) {
			sb.append("int[] y").append(i / 2 + 1).append("= {");
			for (int j = 0; j < 10; j++) {
				sb.append(i + 20 * j).append(',').append(i + 20 * j + 1).append(',');
			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append("};\r\n");
		}
		System.out.println(sb.toString());
	}

}
