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
import java.nio.file.StandardOpenOption;

import static cz.inited.utils.Strings.CREATE_FILE_EXISTS;
import static cz.inited.utils.Strings.CREATE_CREATING_FILE_ERROR;
import static cz.inited.utils.Strings.CREATE_TEMPLATE_MISSING;

/**
 * Created by ondre on 09.07.2016.
 */
public class CordovaService {

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
		File services = inited.getServices();
		String servicesPath = services.getPath();
		String fName = name.endsWith(".js") ? name.substring(0, name.length() - 3) : name;
		if (fName.endsWith("service")) {
			fName = fName.substring(0, fName.length() - 7);
			fName += "Service";
		} else if (!fName.endsWith("Service")) {
			fName += "Service";
		}
		fName = fName.substring(0, 1).toUpperCase() + fName.substring(1);

		if (services.isDirectory()) {
			createServiceFile(fName, servicesPath);
		} else {
			appendServiceToFile(fName, services);
		}
	}

	/**
	 * Create service as a new file
	 * @param name
	 * @param servicesPath
	 * @throws ActionException
	 */
	private void createServiceFile(String name, String servicesPath) throws ActionException {
		File file = new File(servicesPath + "/" + name + ".js");

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
				throw new ActionException(CREATE_CREATING_FILE_ERROR);
			}
		}
	}

	/**
	 * Appends service to the existing services.js etc. file
	 * @param name
	 * @param servicesFile
	 * @throws ActionException
	 */
	private void appendServiceToFile(String name, File servicesFile) throws ActionException {
		try {
			String currentContent = IO.getFileContent(servicesFile.toPath());
			String content = getServiceEditedType(name);
			if (currentContent.trim().isEmpty()) {
				content = "\n" + content;
			}
			Files.write(servicesFile.toPath(), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
		} catch (IOException ex) {
			throw new ActionException(CREATE_CREATING_FILE_ERROR);
		}
	}

	/**
	 * Returns edited template for new service
	 * @param name New service name
	 * @return Edited template as string
	 * @throws ActionException error while reading from template
	 */
	private String getServiceEditedType(String name) throws ActionException {
		String varName = name.substring(0, 1).toLowerCase() + name.substring(1);

		String content;
		try {
			content = IO.getFileContent(Paths.get(getClass().getResource("/fileTemplates/cordova/service.js").toURI()));
		} catch (IOException | URISyntaxException ex) {
			ex.printStackTrace();
			throw new ActionException(CREATE_TEMPLATE_MISSING);
		}
		content = content.replace("$itemname$", varName);
		return content;
	}

}
