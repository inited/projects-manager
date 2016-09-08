package cz.inited.utils.exceptions;

/**
 * Created by ondre on 08.07.2016.
 */
public class CommandNotFoundException extends Exception {

	public CommandNotFoundException() {
		super();
	}

	public CommandNotFoundException(String message) {
		super(message);
	}
}
