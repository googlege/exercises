package de.homedev.bowling;

/**
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class InconsistentInputDataException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InconsistentInputDataException() {
		super();

	}

	public InconsistentInputDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public InconsistentInputDataException(String message, Throwable cause) {
		super(message, cause);

	}

	public InconsistentInputDataException(String message) {
		super(message);

	}

	public InconsistentInputDataException(Throwable cause) {
		super(cause);
	}

}
