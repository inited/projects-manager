package cz.inited.actions.cordova;

import cz.inited.Inited;
import cz.inited.utils.IO;
import cz.inited.utils.exceptions.ActionException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static cz.inited.utils.Strings.CREATE_FILE_EXISTS;
import static cz.inited.utils.Strings.CREATE_CREATING_FILE_ERROR;
import static cz.inited.utils.Strings.CREATE_TEMPLATE_MISSING;

/**
 * Created by ondre on 08.07.2016.
 */
public class CordovaController {

	private static final Logger LOGGER = Logger.getLogger(CordovaController.class);

	/**
	 * Updates controller name to the right format and creates it, or appends
	 * new controller to currently existing controllers.js file
	 * @param name Controller name written like
	 * @param inited Inited settings file
	 * @throws ActionException Exception while creating controller file
	 */
	public void create(String name, Inited inited) throws ActionException {
		File controllers = inited.getControllers();
		String controllersPath = controllers.getPath();
		String cName = name.endsWith(".js") ? name.substring(0, name.length() - 3) : name;
		if (cName.endsWith("controller")) {
			cName = cName.substring(0, cName.length() - 10);
			cName += "Controller";
		} else if (!cName.endsWith("Controller")) {
			cName += "Controller";
		}
		cName = cName.substring(0, 1).toUpperCase() + cName.substring(1);

		if (controllers.isDirectory()) {
			createControllerFile(cName, controllersPath);
		} else {
			appendControllerToFile(cName, controllers);
		}
	}

	/**
	 * Create controller as a new file
	 * @param name
	 * @param controllersPath
	 * @throws ActionException
	 */
	private void createControllerFile(String name, String controllersPath) throws ActionException {
		File file = new File(controllersPath + "/" + name + ".js");

		if (file.exists()) {
			LOGGER.info(CREATE_FILE_EXISTS);
		} else {
			try {
				String content = getControllerEditedType(name);
				boolean created = file.createNewFile();
				if (created) {
					Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
				}
			} catch (IOException ex) {
				throw new ActionException(CREATE_CREATING_FILE_ERROR);
			}
		}
	}

	/**
	 * Appends controller to the existing controllers.js etc. file
	 * @param name
	 * @param controllersFile
	 * @throws ActionException
	 */
	private void appendControllerToFile(String name, File controllersFile) throws ActionException {
		try {
			String currentContent = IO.getFileContent(controllersFile.toPath());
			String content = getControllerEditedType(name);
			if (currentContent.trim().isEmpty()) {
				content = "\n" + content;
			}
			Files.write(controllersFile.toPath(), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
		} catch (IOException ex) {
			throw new ActionException(CREATE_CREATING_FILE_ERROR);
		}
	}

	/**
	 * Returns edited template for new controller
	 * @param name New controller name
	 * @return Edited template as string
	 * @throws ActionException error while reading from template
	 */
	private String getControllerEditedType(String name) throws ActionException {
		String cVarName = name.substring(0, 1).toLowerCase() + name.substring(1);

		String content;
		try {
			content = IO.getFileContent(Paths.get(getClass().getResource("/fileTemplates/cordova/controller.js").toURI()));
		} catch (IOException | URISyntaxException ex) {
			ex.printStackTrace();
			throw new ActionException(CREATE_TEMPLATE_MISSING);
		}
		content = content.replace("$itemname$", name);
		content = content.replace("$itemvarname$", cVarName);
		return content;
	}

	private void addToIndex(Path script) {

	}
}
