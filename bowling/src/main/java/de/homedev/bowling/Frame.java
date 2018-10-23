package de.homedev.bowling;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Frame Data
 *
 */
public class Frame {
	// last frame/turn id calculated from 0 with interval 1
	public static final int LAST_FRAME = 9;
	// number of pins
	public static final int PINS_NUMBER = 10;
	/**
	 * Frame Id
	 */
	private final int frameId;
	/**
	 * Frame first throw
	 */
	private final int firstThrow;
	/**
	 * Frame second throw
	 */
	private Integer secondThrow;
	/**
	 * Frame third throw
	 */
	private Integer thirdThrow;
	/**
	 * first calculation result - "result after frame has been down. (It will be
	 * changed to, if endCalculationResult of previous frame changed)"; TODO
	 * value could be deleted
	 */
	private Integer firstCalculationResult;
	/**
	 * end calculation result - changed first calculation result from next
	 * frames.
	 */
	private Integer endCalculationResult;

	public Frame(int frameId, int firstThrow, Integer secondThrow, Integer thirdThrow) {
		this(frameId, firstThrow, secondThrow, thirdThrow, null, null);
	}

	public Frame(int frameId, int firstThrow, Integer secondThrow, Integer thirdThrow, Integer firstCalculationResult, Integer endCalculationResult) {
		super();
		this.frameId = frameId;
		this.firstThrow = firstThrow;
		this.secondThrow = secondThrow;
		this.thirdThrow = thirdThrow;
		this.firstCalculationResult = firstCalculationResult;
		this.endCalculationResult = endCalculationResult;
		this.validate();
	}

	public Integer getSecondThrow() {
		return secondThrow;
	}

	public void setSecondThrow(Integer secondThrow) {
		this.secondThrow = secondThrow;
	}

	public Integer getThirdThrow() {
		return thirdThrow;
	}

	public void setThirdThrow(Integer thirdThrow) {
		this.thirdThrow = thirdThrow;
	}

	public Integer getFirtsCalculationResult() {
		return firstCalculationResult;
	}

	public void setFirtsCalculationResult(Integer firtsCalculationResult) {
		this.firstCalculationResult = firtsCalculationResult;
	}

	public Integer getEndCalculationResult() {
		return endCalculationResult;
	}

	public void setEndCalculationResult(Integer endCalculationResult) {
		this.endCalculationResult = endCalculationResult;
	}

	public int getFirstThrow() {
		return firstThrow;
	}

	public int getFrameId() {
		return frameId;
	}

	/**
	 * Validates Frame Data
	 */
	public void validate() {
		if ((firstThrow < 0) || (firstThrow > PINS_NUMBER)) {
			throw new InconsistentInputDataException("firstThrow can't be less as 0 or bigger " + PINS_NUMBER);
		}
		if (firstThrow < PINS_NUMBER) {
			if (secondThrow == null) {
				throw new InconsistentInputDataException("firstThrow=" + firstThrow + " secondThrow can't be NULL");
			}
			if (frameId != LAST_FRAME && (firstThrow + secondThrow) > PINS_NUMBER) {
				throw new InconsistentInputDataException("firstThrow+secondThrow can't be bigger " + PINS_NUMBER);
			}
			if ((secondThrow < 0)) {
				throw new InconsistentInputDataException("secondThrow can't be less as 0");
			}
		}
		if ((frameId != LAST_FRAME) && (firstThrow == PINS_NUMBER) && (secondThrow != null)) {
			throw new InconsistentInputDataException("!strike! secondThrow must be null");
		}

		if (firstCalculationResult != null && endCalculationResult != null && endCalculationResult < firstCalculationResult) {
			throw new InconsistentInputDataException("endCalculationResult can't be less then firtsCalculationResult");
		}

		if (thirdThrow != null) {
			if (frameId != Frame.LAST_FRAME) {
				throw new InconsistentInputDataException("thirdThrow exists only in last frame");
			}
			if (thirdThrow < 0 || thirdThrow > PINS_NUMBER) {
				throw new InconsistentInputDataException("thirdThrow can't be less as 0 or bigger " + PINS_NUMBER);
			}
			if (secondThrow == null) {
				throw new InconsistentInputDataException("thirdThrow can't exist without secondThrow");
			}
		}
	}

	@Override
	public String toString() {
		boolean strike = (firstThrow == PINS_NUMBER);
		// (!strike)==(secondThrow!=null)
		boolean spare = (!strike) && (firstThrow + secondThrow == PINS_NUMBER);
		return "frameId=" + frameId + " firstThrow=" + firstThrow + ", secondThrow=" + secondThrow + ", thirdThrow=" + thirdThrow + ", firtsCalculationResult="
				+ firstCalculationResult + ", endCalculationResult=" + endCalculationResult + ", strike=" + strike + ", spare=" + spare;
	}

}
