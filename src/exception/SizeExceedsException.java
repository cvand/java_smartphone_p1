/**
 * @author cvandera
 *
 */

package exception;

public class SizeExceedsException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "You are trying to add elements to a full array.";
	}
}
