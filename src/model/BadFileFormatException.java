/**
* Airport
* <p>
* This exception is thrown if the JSON parser found an invalid structure in a file
* @author Marius Spix
*/

package model;

public class BadFileFormatException extends Exception {

	private static final long serialVersionUID = 297546714301753024L;

	public BadFileFormatException(String message) {
		super(message);
	}
}
