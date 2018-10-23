package de.homedev.mesh.test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.homedev.mesh.TriangleElement;
import de.homedev.mesh.util.InputOutputUtils;

/**
 * Input Output Util. Tests
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class InputOutputUtilTest {

	public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

	/**
	 * Test data initialisation function
	 */
	@Before
	public void init() {

	}

	@Test
	public void testSeparateLineFunction() {
		List<String> result = InputOutputUtils.separateLine("16, 1, 5", ',');
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		Assert.assertEquals("16", result.get(0));
		Assert.assertEquals("1", result.get(1));
		Assert.assertEquals("5", result.get(2));
	}

	@Test
	public void testIfFileExist() throws IOException {
		InputStream is = null;
		try {
			is = InputOutputUtils.getFileInputStream("mesh.txt");
			Assert.assertNotNull(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}

	}

	@Test
	public void testReadingFile1() throws IOException {
		InputStream is = null;
		try {
			is = InputOutputUtils.getFileInputStream("mesh.txt");
			Assert.assertNotNull(is);
			List<TriangleElement> result = InputOutputUtils.readTiangleDataFile(is);
			Assert.assertNotNull(result);
			Assert.assertFalse(result.isEmpty());
			Collections.sort(result);
			int index = Collections.binarySearch(result, new TriangleElement(0, null, null, null, 0d));
			Assert.assertFalse(index < 0);
			TriangleElement triangleElement = result.get(index);
			Assert.assertEquals(0, triangleElement.getId());
			Assert.assertEquals(0, triangleElement.getNode1().getId());
			Assert.assertEquals(11, triangleElement.getNode2().getId());
			Assert.assertEquals(12, triangleElement.getNode3().getId());
			Assert.assertEquals(0.118852d, triangleElement.getValue(), 0d);
			Assert.assertEquals(0, triangleElement.getNode1().getX());
			Assert.assertEquals(0, triangleElement.getNode1().getY());
			Assert.assertEquals(1, triangleElement.getNode2().getX());
			Assert.assertEquals(0, triangleElement.getNode2().getY());
			Assert.assertEquals(1, triangleElement.getNode3().getX());
			Assert.assertEquals(1, triangleElement.getNode3().getY());
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	@Test
	public void testWriteToFile() throws IOException {
		// Read Data
		InputStream is = null;
		List<TriangleElement> data = null;
		try {
			is = InputOutputUtils.getFileInputStream("mesh.txt");
			Assert.assertNotNull(is);
			data = InputOutputUtils.readTiangleDataFile(is);
			Assert.assertNotNull(data);
			Assert.assertFalse(data.isEmpty());
		} finally {
			if (is != null) {
				is.close();
			}
		}
		// Write Data
		String result = InputOutputUtils.writeTiangleData(data);
		InputOutputUtils.writeFile("my_mesh.txt", result);
	}

}
