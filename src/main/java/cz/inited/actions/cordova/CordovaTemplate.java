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
import java.nio.file.Paths;

import static cz.inited.utils.Strings.*;

/**
 * Created by ondre on 09.07.2016.
 */
public class CordovaTemplate {

	private static final Logger LOGGER = Logger.getLogger(CordovaService.class);

	/**
	 * Updates service name to the right format and creates it, or appends
	 * new service to currently existing services.js file or whatever was set
	 * in .inited file
	 * @param name Service name
	 * @param inited Inited settings file
	 * @throws ActionException Exception while creating service file
	 */
	public void create(String name, Inited inited) throws ActionException {
		File templates = inited.getTemplates();
		String templatesPath = templates.getPath();
		String tName = name.endsWith(".html") ? name.substring(0, name.length() - 5) : name;
		tName = tName.endsWith(".js")? name.substring(0, name.length() - 3): tName;
		if (tName.endsWith("template") || tName.endsWith("Template")) {
			tName = tName.substring(0, tName.length() - 8);
		} else if (tName.endsWith("Controller") || tName.endsWith("controller")) {
			tName = tName.substring(0, tName.length() - 10);
		}
		tName = tName.substring(0, 1).toLowerCase() + tName.substring(1);

		createTemplateFile(tName, templatesPath);
	}

	/**
	 * Create template as a new file
	 * @param name
	 * @param templatesPath
	 * @throws ActionException
	 */
	private void createTemplateFile(String name, String templatesPath) throws ActionException {
		File file = new File(templatesPath + "/" + name + ".html");

		if (file.exists()) {
			LOGGER.info(CREATE_FILE_EXISTS);
		} else {
			try {
				String content = getServiceEditedType(name);
				boolean created = file.createNewFile();
				if (created) {
					Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				throw new ActionException(CREATE_CREATING_FILE_ERROR);
			}
		}
	}

	/**
	 * Returns template
	 * @param name New template name
	 * @return Edited template as string
	 * @throws ActionException error while reading from template
	 */
	private String getServiceEditedType(String name) throws ActionException {
		//String varName = name.substring(0, 1).toLowerCase() + name.substring(1);

		String content;
		try {
			content = IO.getFileContent(Paths.get(getClass().getResource("/fileTemplates/cordova/template.html").toURI()));
		} catch (IOException | URISyntaxException ex) {
			ex.printStackTrace();
			throw new ActionException(CREATE_TEMPLATE_MISSING);
		}
		//content = content.replace("$itemname$", varName);
		return content;
	}

}
