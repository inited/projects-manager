package cz.inited.ui.text.commands;

import cz.inited.Inited;
import cz.inited.actions.cordova.CordovaProject;
import cz.inited.utils.FileTypes;
import cz.inited.utils.exceptions.ActionException;

import static cz.inited.utils.Strings.*;

/**
 * Created by ondre on 08.07.2016.
 */
public class CommandCreate extends ACommand {

	public CommandCreate() {
		super("create");
	}

	@Override
	public String run(String... args) {
		if (args.length == 1) {
			return CREATE_NO_APP_TYPE;
		} else if (args.length == 2) {
			return CREATE_NO_APP_NAME;
		} else {
			if (args[1].equals("--cordova")) {
				try {
					CordovaProject cordovaProject = new CordovaProject();
					if (FileTypes.contains(args[2])) {
						if (args.length == 3) {
							return CREATE_NO_FILE_NAME;
						}
						cordovaProject.createFile(FileTypes.dashedValueOf(args[2]), args[3], Inited.fromCurrentDirectory());
						return CREATE_FILE_CREATED;
					}
					cordovaProject.create(args[2]);
					return CREATE_PROJECT_CREATED;
				} catch (ActionException ex) {
					return ex.getMessage();
				}
			} else {
				return CREATE_TYPE_NOT_SUPPORTED;
			}
		}
	}
}
