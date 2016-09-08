package cz.inited.ui.text;

import cz.inited.ui.text.commands.ACommand;
import cz.inited.ui.text.commands.CommandCreate;
import cz.inited.utils.exceptions.CommandNotFoundException;

import java.util.HashSet;

import static cz.inited.utils.Strings.*;

/**
 * Created by ondre on 08.07.2016.
 */
public class CommandsManager {

	/**
	 * Singleton instance of commandsManager
	 */
	private static final CommandsManager INSTANCE = new CommandsManager();

	/**
	 * Instance getter
	 * @return
	 */
	public static CommandsManager getInstance() {
		return INSTANCE;
	}

	private HashSet<ACommand> commands;

	/**
	 * Commands manager constructor
	 */
	private CommandsManager() {
		commands = new HashSet<>();
		loadCommands();
	}

	/**
	 * Loads all commands to the set
	 */
	private void loadCommands() {
		commands.add(new CommandCreate());
	}

	/**
	 * Processes command given from program arguments
	 * First argument has to be command name
	 * Another arguments are arguments for command
	 * @param args arguments given to program
	 */
	public void processCommand(String... args) {
		ACommand command;
		try {
			command = getCommand(args[0]);
			System.out.println(command.run(args));
		} catch (CommandNotFoundException ex) {
			System.out.println(COMMAND_NOT_fOUND);
		}
	}

	public ACommand getCommand(String name) throws CommandNotFoundException {
		for(ACommand command: commands) {
			if (command.getName().equals(name)) {
				return command;
			}
		}

		throw new CommandNotFoundException("Command with given name was not found.");
	}
}
