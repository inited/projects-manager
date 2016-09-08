package cz.inited.ui.text.commands;

/**
 * Created by ondre on 08.07.2016.
 */
public abstract class ACommand {

	private final String name;

	public ACommand(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract String run(String... args);
}
