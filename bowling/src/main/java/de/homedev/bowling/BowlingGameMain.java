package de.homedev.bowling;

import java.util.List;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Bowling game main class
 *
 */
public class BowlingGameMain {
	// Max players number
	public static final int MAX_PLAYERS_NUMBER = 6;

	public BowlingGameMain() {
		super();

	}

	/**
	 * main bowling game function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BowlingGameMain bowling = new BowlingGameMain();
			// reading players number and players names
			List<Person> playerList = InputOutputUtils.readPlayerData();
			// we have Frame.LAST_FRAME frames counting from 0
			for (int frameId = 0; frameId <= Frame.LAST_FRAME; frameId++) {
				// each player has own turn with one, two or three
				// throws(dependent of previous result)
				for (Person player : playerList) {
					System.out.println("frameId:" + frameId + " player:" + player.toString());
					// This are only input frame data
					Frame inputData = InputOutputUtils.readFrameData(frameId);
					List<Frame> history = player.getHistory();
					// This are input frame data with current frame calculation.
					// Old frames calculation data could be changed according to
					// description
					Frame frame = bowling.nextFrame(frameId, history, inputData.getFirstThrow(), inputData.getSecondThrow(), inputData.getThirdThrow());
					history.add(frame);
					InputOutputUtils.printHistory(player);
				}
			}
			for (Person player : playerList) {
				InputOutputUtils.printHistory(player);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main bowling game calculation algorithm. It calculates current frame
	 * "endCalculationResult" and changes if necessary "endCalculationResult" of
	 * previous frames
	 * 
	 * @param currentFrameId
	 *            - current frame id calculated from 0
	 * @param history
	 *            - current player history
	 * @param firstThrow
	 *            - frame first throw
	 * @param secondThrow
	 *            - frame second throw
	 * @param thirdThrow
	 *            - frame third throw
	 * @return
	 */
	public Frame nextFrame(int currentFrameId, List<Frame> history, int firstThrow, Integer secondThrow, Integer thirdThrow) {
		// we validate data in constructor. With inconsistent data we will not
		// start calculation
		Frame result = new Frame(currentFrameId, firstThrow, secondThrow, thirdThrow);
		if (currentFrameId != history.size()) {
			throw new InconsistentInputDataException("'currentFrameId' must be equals 'history.size()'");
		}
		int calculationResult = 0;
		// correct previous 1 or 2 frames according to description
		if (currentFrameId > 0) {
			Frame previousFrame = history.get(currentFrameId - 1);
			if (previousFrame.getFirstThrow() == Frame.PINS_NUMBER) {
				previousFrame.setEndCalculationResult(previousFrame.getEndCalculationResult() + firstThrow);
				if (secondThrow != null) {
					previousFrame.setEndCalculationResult(previousFrame.getEndCalculationResult() + secondThrow);
				}
				// {deep 'endCalculationResult' calculation}
				if (currentFrameId - 2 > -1) {
					Frame previousPreviousFrame = history.get(currentFrameId - 2);
					if (previousPreviousFrame.getFirstThrow() == Frame.PINS_NUMBER) {
						previousPreviousFrame.setEndCalculationResult(previousPreviousFrame.getEndCalculationResult() + firstThrow);
						previousFrame.setFirtsCalculationResult(previousFrame.getFirtsCalculationResult() + firstThrow);
						previousFrame.setEndCalculationResult(previousFrame.getEndCalculationResult() + firstThrow);
					}
				}
			} else if (previousFrame.getFirstThrow() + previousFrame.getSecondThrow() == Frame.PINS_NUMBER) {
				previousFrame.setEndCalculationResult(previousFrame.getFirtsCalculationResult() + firstThrow);
			}
			calculationResult = previousFrame.getEndCalculationResult();
		}
		// working with current frame
		calculationResult = calculationResult + firstThrow;
		if (secondThrow != null) {
			calculationResult = calculationResult + secondThrow;
		}
		if (thirdThrow != null) {
			calculationResult = calculationResult + thirdThrow;
		}
		result.setFirtsCalculationResult(calculationResult);
		result.setEndCalculationResult(calculationResult);
		return result;
	}

}
