package de.homedev.mesh;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.homedev.mesh.exception.InconsistentInputDataException;
import de.homedev.mesh.util.InputOutputUtils;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class CalculateMain {
	private final Map<Integer, TriangleElement> hotSpots;
	private final List<TriangleElement> data;
	private List<List<TriangleElement>> neighborsLists = null;

	public CalculateMain(List<TriangleElement> data) {
		super();
		this.data = data;
		this.hotSpots = new TreeMap<Integer, TriangleElement>();
		Collections.sort(this.data);
		calculateNeighborsLists();
		for (List<TriangleElement> list : neighborsLists) {
			Map<Integer, TriangleElement> tmp = calculateHotspot(list);
			System.out.println(InputOutputUtils.idsToString(tmp.keySet()));
			hotSpots.putAll(tmp);
		}
	}

	/**
	 * Filtering same hotspots.
	 * 
	 * the first two hotspots are not the elements with the two first maximum
	 * values because the element where the second maximum value is achieved
	 * will probably be the neighbor element of the first one. In this case it
	 * would belong to the first hotspot from the engineering point of view.
	 */
	public void filterSameHotspots() {
		int[][] neighborsElementIdsLists = neighborsIds();
		for (int[] neighborsElementIds : neighborsElementIdsLists) {
			TriangleElement last = null;
			for (int id : neighborsElementIds) {
				TriangleElement newLast = hotSpots.get(id);
				// same hotspot
				if ((last != null) && (newLast != null)) {
					if (newLast.getValue() > last.getValue()) {
						hotSpots.remove(last.getId());
					} else {
						hotSpots.remove(newLast.getId());
						continue;
					}
				}
				last = newLast;
			}
		}
	}

	public List<TriangleElement> getData() {
		return data;
	}

	private int[][] neighborsIds() {
		neighborsLists = new ArrayList<List<TriangleElement>>(100);
		int[] x1 = { 1, 0, 3, 2, 5, 4, 7, 6, 9, 8, 11, 10, 13, 12, 15, 14, 17, 16, 19, 18 };
		int[] x2 = { 21, 20, 23, 22, 25, 24, 27, 26, 29, 28, 31, 30, 33, 32, 35, 34, 37, 36, 39, 38 };
		int[] x3 = { 41, 40, 43, 42, 45, 44, 47, 46, 49, 48, 51, 50, 53, 52, 55, 54, 57, 56, 59, 58 };
		int[] x4 = { 61, 60, 63, 62, 65, 64, 67, 66, 69, 68, 71, 70, 73, 72, 75, 74, 77, 76, 79, 78 };
		int[] x5 = { 81, 80, 83, 82, 85, 84, 87, 86, 89, 88, 91, 90, 93, 92, 95, 94, 97, 96, 99, 98 };
		int[] x6 = { 101, 100, 103, 102, 105, 104, 107, 106, 109, 108, 111, 110, 113, 112, 115, 114, 117, 116, 119, 118 };
		int[] x7 = { 121, 120, 123, 122, 125, 124, 127, 126, 129, 128, 131, 130, 133, 132, 135, 134, 137, 136, 139, 138 };
		int[] x8 = { 141, 140, 143, 142, 145, 144, 147, 146, 149, 148, 151, 150, 153, 152, 155, 154, 157, 156, 159, 158 };
		int[] x9 = { 161, 160, 163, 162, 165, 164, 167, 166, 169, 168, 171, 170, 173, 172, 175, 174, 177, 176, 179, 178 };
		int[] x10 = { 181, 180, 183, 182, 185, 184, 187, 186, 189, 188, 191, 190, 193, 192, 195, 194, 197, 196, 199, 198 };
		int[] y1 = { 0, 1, 20, 21, 40, 41, 60, 61, 80, 81, 100, 101, 120, 121, 140, 141, 160, 161, 180, 181 };
		int[] y2 = { 2, 3, 22, 23, 42, 43, 62, 63, 82, 83, 102, 103, 122, 123, 142, 143, 162, 163, 182, 183 };
		int[] y3 = { 4, 5, 24, 25, 44, 45, 64, 65, 84, 85, 104, 105, 124, 125, 144, 145, 164, 165, 184, 185 };
		int[] y4 = { 6, 7, 26, 27, 46, 47, 66, 67, 86, 87, 106, 107, 126, 127, 146, 147, 166, 167, 186, 187 };
		int[] y5 = { 8, 9, 28, 29, 48, 49, 68, 69, 88, 89, 108, 109, 128, 129, 148, 149, 168, 169, 188, 189 };
		int[] y6 = { 10, 11, 30, 31, 50, 51, 70, 71, 90, 91, 110, 111, 130, 131, 150, 151, 170, 171, 190, 191 };
		int[] y7 = { 12, 13, 32, 33, 52, 53, 72, 73, 92, 93, 112, 113, 132, 133, 152, 153, 172, 173, 192, 193 };
		int[] y8 = { 14, 15, 34, 35, 54, 55, 74, 75, 94, 95, 114, 115, 134, 135, 154, 155, 174, 175, 194, 195 };
		int[] y9 = { 16, 17, 36, 37, 56, 57, 76, 77, 96, 97, 116, 117, 136, 137, 156, 157, 176, 177, 196, 197 };
		int[] y10 = { 18, 19, 38, 39, 58, 59, 78, 79, 98, 99, 118, 119, 138, 139, 158, 159, 178, 179, 198, 199 };
		int[][] allArrays = { x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, y1, y2, y3, y4, y5, y6, y7, y8, y9, y10 };
		return allArrays;
	}

	private void calculateNeighborsLists() {
		neighborsLists = new ArrayList<List<TriangleElement>>(100);
		int[][] neighborsElementIdsLists = neighborsIds();
		for (int[] neighborsElementIds : neighborsElementIdsLists) {
			List<TriangleElement> list = new ArrayList<TriangleElement>(neighborsElementIds.length);
			for (int id : neighborsElementIds) {
				int index = Collections.binarySearch(data, new TriangleElement(id, null, null, null, 0d));
				if (index < 0) {
					throw new InconsistentInputDataException("can't find value for element with id=" + id);
				}
				list.add(data.get(index));
			}
			neighborsLists.add(list);
		}
	}

	/**
	 * Calculates hotspots for given neighbors element list
	 * 
	 * @param list
	 *            - neighbors elements
	 * @return calculated hotspots
	 */
	private Map<Integer, TriangleElement> calculateHotspot(List<TriangleElement> list) {
		Map<Integer, TriangleElement> result = new HashMap<Integer, TriangleElement>();
		TriangleElement last = null;
		TriangleElement secondToLast = null;
		for (TriangleElement el : list) {
			if (secondToLast == null) {
				secondToLast = last;
				last = el;
				continue;
			}
			if (secondToLast.getValue() < last.getValue() && (last.getValue() > el.getValue())) {
				result.put(last.getId(), last);
			}
			secondToLast = last;
			last = el;
		}
		return result;
	}

	public Map<Integer, TriangleElement> getHotSpots() {
		return hotSpots;
	}

	public static void main(String[] args) {
		InputStream is = null;
		try {
			is = InputOutputUtils.getFileInputStream("mesh.txt");
			List<TriangleElement> data = InputOutputUtils.readTiangleDataFile(is);
			CalculateMain main = new CalculateMain(data);
			Map<Integer, TriangleElement> hotSpots = main.getHotSpots();
			StringBuilder sb = new StringBuilder(200);
			sb.append("Calculated HotSpots(Element Id's) before filtering\r\n");
			sb.append(InputOutputUtils.idsToString(hotSpots.keySet()));
			sb.append("\r\nCalculated HotSpots(Element Id's) after filtering\r\n");
			main.filterSameHotspots();
			sb.append(InputOutputUtils.idsToString(hotSpots.keySet()));
			sb.append("\r\nCalculated HotSpots(Element Id's) after filtering sorted by value\r\n");
			List<TriangleElement> list = new ArrayList<TriangleElement>(main.getHotSpots().values());
			Collections.sort(list, new ElementValueComparator<TriangleElement>());
			sb.append(InputOutputUtils.idsToString(list));
			System.out.println(sb.toString());
			InputOutputUtils.writeFile("CalculatedHotSpots.txt", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
