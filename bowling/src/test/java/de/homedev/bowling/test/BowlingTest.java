package de.homedev.bowling.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.homedev.bowling.BowlingGameMain;
import de.homedev.bowling.Frame;
import de.homedev.bowling.InconsistentInputDataException;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class BowlingTest {

	private BowlingGameMain bowling;

	/**
	 * Test data initialisation function
	 */
	@Before
	public void init() {
		bowling = new BowlingGameMain();
	}

	/**
	 * Test first strike frame
	 */
	@Test
	public void nextFrameTest1() {
		List<Frame> history = new ArrayList<Frame>(0);
		Frame result = bowling.nextFrame(0, history, 10, null, null);
		Assert.assertEquals(new Integer(10), result.getFirtsCalculationResult());
	}

	/**
	 * Test first spare frame
	 */
	@Test
	public void nextFrameTest2() {
		List<Frame> history = new ArrayList<Frame>(0);
		Frame result = bowling.nextFrame(0, history, 4, 6, null);
		Assert.assertEquals(new Integer(10), result.getFirtsCalculationResult());
	}

	/**
	 * Input inconsistent data (firstThrow+secondThrow=11
	 * (Frame.PINS_NUMBER==10))
	 */
	@Test(expected = InconsistentInputDataException.class)
	public void nextFrameTestError1() {
		List<Frame> history = new ArrayList<Frame>(0);
		Assert.assertEquals(Frame.PINS_NUMBER, 10);
		bowling.nextFrame(0, history, 5, 6, null);

	}

	/**
	 * Input inconsistent data (firstThrow=5;secondThrow=null
	 * (Frame.PINS_NUMBER==10))
	 */
	@Test(expected = InconsistentInputDataException.class)
	public void nextFrameTestError2() {
		List<Frame> history = new ArrayList<Frame>(0);
		Assert.assertEquals(Frame.PINS_NUMBER, 10);
		bowling.nextFrame(0, history, 5, null, null);

	}

	/**
	 * Input inconsistent data (firstThrow=5;secondThrow=null;thirdThrow=9)
	 * third throw can not exist without second throw
	 */
	@Test(expected = InconsistentInputDataException.class)
	public void nextFrameError3() {
		List<Frame> history = new ArrayList<Frame>(0);
		bowling.nextFrame(0, history, 5, null, 9);

	}

	/**
	 * Testing 3 first strike frames
	 */
	@Test
	public void nextFrameTest3() {
		List<Frame> history = new ArrayList<Frame>(3);
		history.add(bowling.nextFrame(0, history, 10, null, null));// 0
		history.add(bowling.nextFrame(1, history, 10, null, null));// 1
		history.add(bowling.nextFrame(2, history, 10, null, null));// 2
		Frame f0 = history.get(0);
		Frame f1 = history.get(1);
		Frame f2 = history.get(2);
		// for (Frame fr : history) {
		// System.out.println(fr.toString());
		// }
		Assert.assertEquals(new Integer(30), f0.getEndCalculationResult());
		Assert.assertEquals(new Integer(50), f1.getEndCalculationResult());
		Assert.assertEquals(new Integer(60), f2.getEndCalculationResult());
	}

	/**
	 * Testing game with all strike frames
	 */
	@Test
	public void nextFrameTest4() {
		List<Frame> history = new ArrayList<Frame>(10);
		for (int i = 0; i < 9; i++) {
			history.add(bowling.nextFrame(i, history, 10, null, null));
		}
		// In "last frame" all 10 pins could stay 3 times
		history.add(bowling.nextFrame(9, history, 10, 10, 10));
		for (int i = 0; i < history.size(); i++) {
			Assert.assertEquals(i, history.get(i).getFrameId());
			Assert.assertEquals((i + 1) * 30, history.get(i).getEndCalculationResult().intValue());
			System.out.println(history.get(i).toString());
		}
	}

	/**
	 * Testing game with sample data in the game description
	 */
	@Test
	public void nextFrameTest5() {
		List<Frame> history = new ArrayList<Frame>(10);
		history.add(bowling.nextFrame(0, history, 1, 4, null));
		history.add(bowling.nextFrame(1, history, 4, 5, null));
		history.add(bowling.nextFrame(2, history, 6, 4, null));
		history.add(bowling.nextFrame(3, history, 5, 5, null));
		history.add(bowling.nextFrame(4, history, 10, null, null));
		history.add(bowling.nextFrame(5, history, 0, 1, null));
		history.add(bowling.nextFrame(6, history, 7, 3, null));
		history.add(bowling.nextFrame(7, history, 6, 4, null));
		history.add(bowling.nextFrame(8, history, 10, null, null));
		history.add(bowling.nextFrame(9, history, 2, 8, 6));
		System.out.println("------------------------------------------------------");
		for (int i = 0; i < history.size(); i++) {
			Assert.assertEquals(i, history.get(i).getFrameId());
			System.out.println(history.get(i).toString());
		}
		Assert.assertEquals(5, history.get(0).getEndCalculationResult().intValue());
		Assert.assertEquals(14, history.get(1).getEndCalculationResult().intValue());
		Assert.assertEquals(29, history.get(2).getEndCalculationResult().intValue());
		Assert.assertEquals(49, history.get(3).getEndCalculationResult().intValue());
		Assert.assertEquals(60, history.get(4).getEndCalculationResult().intValue());
		Assert.assertEquals(61, history.get(5).getEndCalculationResult().intValue());
		Assert.assertEquals(77, history.get(6).getEndCalculationResult().intValue());
		Assert.assertEquals(97, history.get(7).getEndCalculationResult().intValue());
		Assert.assertEquals(117, history.get(8).getEndCalculationResult().intValue());
		Assert.assertEquals(133, history.get(9).getEndCalculationResult().intValue());
	}
}
