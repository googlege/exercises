package de.homedev.mesh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.homedev.mesh.Node;
import de.homedev.mesh.TriangleElement;
import de.homedev.mesh.exception.InconsistentInputDataException;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        hotspot-detector output utilities
 * 
 */
public final class InputOutputUtils {

	public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	public static final char SEPARATOR_CHARACTER = ',';
	private static final String LINE_SEPARATOR = "\r\n";

	private InputOutputUtils() {
	}

	private enum DataType {
		NODES("NODES"), ELEMENTS("ELEMENTS"), VALUES("VALUES");
		private final String id;

		private DataType(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		public static DataType getDataType(String id) {
			for (DataType type : DataType.values()) {
				if (id.contains(type.getId())) {
					return type;
				}
			}
			return null;
		}

	}

	private static class Record implements Comparable<Record> {
		private final List<String> values;

		public Record(List<String> values) {
			super();
			if (values.isEmpty()) {
				throw new InconsistentInputDataException("values array is empty");
			}
			this.values = values;
		}

		public Record(Node node) {
			super();
			this.values = new ArrayList<String>(3);
			this.values.add(Integer.toString(node.getId()));
			this.values.add(Integer.toString(node.getX()));
			this.values.add(Integer.toString(node.getY()));
		}

		/**
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Record o) {
			Integer id1 = Integer.parseInt(values.get(0));
			Integer id2 = Integer.parseInt(o.values.get(0));
			return id1.compareTo(id2);
		}

		/**
		 * 
		 * @return
		 */
		public List<String> getValues() {
			return values;
		}

		public Node toNode() {
			int id = Integer.parseInt(values.get(0));
			int x = Integer.parseInt(values.get(1));
			int y = Integer.parseInt(values.get(2));
			return new Node(id, x, y);
		}

		/**
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder(100);
			for (String value : values) {
				result.append(value).append(" ,");
			}
			result.delete(result.length() - 2, result.length());
			return result.toString();
		}

	}

	/**
	 * separates line/string in parts
	 */
	public static List<String> separateLine(String line, char separator) {
		List<String> result = new ArrayList<String>(4);
		int index = -1;
		int fromIndex = 0;
		while ((index = line.indexOf(separator, fromIndex)) != -1) {
			result.add(line.substring(fromIndex, index).trim());
			fromIndex = index + 1;
		}
		result.add(line.substring(fromIndex).trim());
		return result;
	}

	private static Map<DataType, List<Record>> readData(final BufferedReader br) throws IOException {
		String line = null;
		DataType dataType = null;
		List<Record> result = new ArrayList<Record>(1000);
		Map<DataType, List<Record>> map = new HashMap<DataType, List<Record>>();
		while ((line = br.readLine()) != null) {
			String rest = line.trim();
			if (rest.isEmpty()) {
				continue;
			}
			DataType newDataType = DataType.getDataType(rest.toUpperCase());
			if (dataType == null) {
				dataType = newDataType;
				continue;
			}
			if (newDataType != null) {
				map.put(dataType, result);
				dataType = newDataType;
				result = new ArrayList<Record>(1000);
				continue;
			}
			List<String> values = separateLine(rest, SEPARATOR_CHARACTER);
			result.add(new Record(values));
		}
		if (dataType != null) {
			map.put(dataType, result);
		}
		return map;
	}

	/**
	 * Reads Triangle Element List from File
	 * 
	 * @param is
	 *            - file input stream
	 * @return - Triangle Element List
	 * @throws IOException
	 */
	public static List<TriangleElement> readTiangleDataFile(InputStream is) throws IOException {
		final BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSET_UTF8));
		Map<DataType, List<Record>> map = readData(br);
		List<Record> nodes = map.get(DataType.NODES);
		if (nodes.isEmpty()) {
			throw new InconsistentInputDataException("can't find nodes");
		}
		List<Record> elements = map.get(DataType.ELEMENTS);
		if (elements.isEmpty()) {
			throw new InconsistentInputDataException("can't find elements");
		}
		List<Record> values = map.get(DataType.VALUES);
		if (values.isEmpty()) {
			throw new InconsistentInputDataException("can't find values");
		}
		if (elements.size() != values.size()) {
			throw new InconsistentInputDataException("elements list must be the same size as values list");
		}

		Collections.sort(nodes);
		// Collections.sort(elements);
		Collections.sort(values);
		List<String> list = new ArrayList<String>(1);
		list.add("");
		Record r = new Record(list);
		List<TriangleElement> result = new ArrayList<TriangleElement>(300);
		for (Record el : elements) {
			String idStr = el.getValues().get(0);
			list.add(0, idStr);
			int index = Collections.binarySearch(values, r);
			if (index < 0) {
				throw new InconsistentInputDataException("can't find value for element with id=" + idStr);
			}
			Record valueRecord = values.get(index);
			String valueStr = valueRecord.getValues().get(1);
			double value = Double.parseDouble(valueStr);

			String node1Id = el.getValues().get(1);
			list.add(0, node1Id);
			index = Collections.binarySearch(nodes, r);
			if (index < 0) {
				throw new InconsistentInputDataException("can't find node with id=" + node1Id);
			}
			Node node1 = nodes.get(index).toNode();

			String node2Id = el.getValues().get(2);
			list.add(0, node2Id);
			index = Collections.binarySearch(nodes, r);
			if (index < 0) {
				throw new InconsistentInputDataException("can't find node with id=" + node2Id);
			}
			Node node2 = nodes.get(index).toNode();

			String node3Id = el.getValues().get(3);
			list.add(0, node3Id);
			index = Collections.binarySearch(nodes, r);
			if (index < 0) {
				throw new InconsistentInputDataException("can't find node with id=" + node3Id);
			}
			Node node3 = nodes.get(index).toNode();
			result.add(new TriangleElement(Integer.parseInt(idStr), node1, node2, node3, value));
		}
		return result;
	}

	/**
	 * writes triangle elements list in file
	 * 
	 * @param data
	 *            - triangle elements list
	 * @return String with file text
	 * @throws IOException
	 */
	public static String writeTiangleData(List<TriangleElement> data) throws IOException {
		StringBuffer result = new StringBuffer(200000);

		List<Record> values = new ArrayList<Record>(data.size());
		List<Record> elements = new ArrayList<Record>(data.size());
		Map<Integer, Record> nodes = new TreeMap<Integer, Record>();
		for (TriangleElement el : data) {
			List<String> valueRec = new ArrayList<String>(2);
			valueRec.add(Integer.toString(el.getId()));
			valueRec.add(Double.toString(el.getValue()));
			values.add(new Record(valueRec));

			List<String> elementRec = new ArrayList<String>(4);
			elementRec.add(Integer.toString(el.getId()));
			elementRec.add(Integer.toString(el.getNode1().getId()));
			elementRec.add(Integer.toString(el.getNode2().getId()));
			elementRec.add(Integer.toString(el.getNode3().getId()));
			elements.add(new Record(elementRec));

			nodes.put(el.getNode1().getId(), new Record(el.getNode1()));
			nodes.put(el.getNode2().getId(), new Record(el.getNode2()));
			nodes.put(el.getNode3().getId(), new Record(el.getNode3()));
		}

		result.append(DataType.NODES.getId()).append(LINE_SEPARATOR);
		for (Record r : nodes.values()) {
			result.append(r.toString()).append(LINE_SEPARATOR);
		}

		result.append(DataType.ELEMENTS.getId()).append(LINE_SEPARATOR);
		for (Record r : elements) {
			result.append(r.toString()).append(LINE_SEPARATOR);
		}

		result.append(DataType.VALUES.getId()).append(LINE_SEPARATOR);
		for (Record r : values) {
			result.append(r.toString()).append(LINE_SEPARATOR);
		}
		return result.toString();
	}

	/**
	 * Returns database or Input/output file directory
	 * 
	 * @return
	 */
	public static File getDBDir() {
		File runDir = new File(System.getProperty("user.dir"));
		return new File(runDir, "etc");
	}

	/**
	 * Reading any file with 'fileName' from database directory
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static InputStream getFileInputStream(String fileName) throws FileNotFoundException {
		final File currentDBDir = getDBDir();
		if (!currentDBDir.exists() || !currentDBDir.isDirectory()) {
			throw new RuntimeException("Database directory" + currentDBDir.getAbsolutePath() + " does not exist");
		}
		File file = new File(currentDBDir, fileName);
		if (!file.exists() || file.isDirectory()) {
			throw new RuntimeException("File " + file.getAbsolutePath() + " does not exist");
		}
		return new FileInputStream(file);
	}

	/**
	 * Writes in file with name 'fileName' and text content 'data' in database
	 * directory
	 * 
	 * @param fileName
	 *            - file name
	 * @param data
	 *            - file text content
	 * @throws IOException
	 */
	public static void writeFile(String fileName, String data) throws IOException {
		final File currentDBDir = getDBDir();
		if (!currentDBDir.exists() || !currentDBDir.isDirectory()) {
			throw new RuntimeException("Database directory" + currentDBDir.getAbsolutePath() + " does not exist");
		}
		File file = new File(currentDBDir, fileName);
		if (file.isDirectory()) {
			throw new RuntimeException("File " + file.getAbsolutePath() + " exists and is directory");
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(file, false);
			os.write(data.getBytes(CHARSET_UTF8));
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	/**
	 * writes id's separated with comma in returned string
	 * 
	 * @param ids
	 *            - Id's
	 * @return
	 */
	public static String idsToString(Collection<Integer> ids) {
		StringBuilder sb = new StringBuilder(200);
		if (!ids.isEmpty()) {
			for (Integer id : ids) {
				sb.append(id).append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
		}
		return sb.toString();
	}

	public static String idsToString(List<TriangleElement> list) {
		StringBuilder sb = new StringBuilder(200);
		if (!list.isEmpty()) {
			for (TriangleElement e : list) {
				sb.append(e.getId()).append('(').append(e.getValue()).append(')').append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
		}
		return sb.toString();
	}
}
