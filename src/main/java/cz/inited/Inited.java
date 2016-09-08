package cz.inited;

import cz.inited.utils.exceptions.ActionException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static cz.inited.utils.Strings.INITED_FILE_NOT_FOUND;
import static cz.inited.utils.Strings.INITED_FILE_SAVE_ERROR;

/**
 * Created by ondre on 08.07.2016.
 */
public class Inited {

	private static final Logger LOGGER = Logger.getLogger(Inited.class);
	/**
	 * Creates inited file from currently working directory or its parents
	 * Throws exception if no .inited file is found
	 * @return Inited file for current directory
	 */
	public static Inited fromCurrentDirectory() {
		Path currentPath = Paths.get("");
		return fromPath(currentPath);
	}

	/**
	 * Creates inited file from given path or its parents
	 * Throws exception if no .inited file is found
	 * @param currentPath Inited file for given path
	 * @return
	 */
	public static Inited fromPath(Path currentPath) {
		LOGGER.info("Checking for .inited file in " + currentPath.toAbsolutePath().toString());
		File initedFile = new File(currentPath.resolve(".inited").toUri());
		while(!initedFile.exists()) {
			currentPath = currentPath.getParent();
			if (currentPath == null) {
				throw new NullPointerException(INITED_FILE_NOT_FOUND);
			}
			initedFile = new File(currentPath.toString() + "/.inited");
		}
		try {
			Inited inited = new Inited(initedFile);
			return inited;
		} catch (Exception ex) {
			throw new NullPointerException(INITED_FILE_NOT_FOUND);
		}
	}

	/**
	 * Inited file properties
	 */
	private Map<String, String> properties;
	/**
	 * Location of the parent folder of the .inited file
	 */
	private String projectLocation;
	/**
	 * .inited properties file
	 */
	private File initedFile;

	/**
	 * Creates inited instance and loads properties from .inited file
	 * @param initedFile .inited file with properties
	 * @throws Exception If file is not found or is bad
	 */
	private Inited(File initedFile) throws Exception {
		properties = new HashMap<>();
		projectLocation = initedFile.getParent();
		this.initedFile = initedFile;

		BufferedReader br = new BufferedReader(new FileReader(initedFile));
		String line;
		while((line = br.readLine()) != null) {
			String[] args = line.split("=");
			if (args.length == 2) {
				properties.put(args[0], args[1]);
			}
		}
	}

	public File getFile() {
		return initedFile;
	}

	/**
	 * Returns app name
	 * @return App name
	 */
	public String getName() {
		return properties.get("name");
	}

	/**
	 * Sets new project name
	 * @param name Project name
	 */
	public void setName(String name) throws ActionException {
		properties.put("name", name);
		save();
	}

	/**
	 * Returns controllers path
	 * @return Controllers path
	 */
	public File getControllers() {
		File file = new File(projectLocation + "/" + properties.get("controllers"));

		if (!file.exists()) {
			if (file.getName().endsWith(".js")) {
				try {
					new File(file.getParent()).mkdirs();
					file.createNewFile();
				} catch (Exception ex) {}
			} else {
				file.mkdirs();
			}
		}

		return file;
	}

	/**
	 * Sets new controllers location
	 * @param file Controllers directory or file
	 */
	public void setControllers(File file) throws ActionException {
		properties.put("controllers", file.getPath().replace(projectLocation, ""));
		save();
	}

	/**
	 * Returns templates path
	 * @return Templates path
	 */
	public File getTemplates() {
		File file = new File(projectLocation + "/" + properties.get("templates"));

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	/**
	 * Sets new templates location
	 * @param file Templates directory or file
	 */
	public void setTemplates(File file) throws ActionException {
		properties.put("templates", file.getPath().replace(projectLocation, ""));
		save();
	}

	/**
	 * Returns services path
	 * @return Services path
	 */
	public File getServices() {
		File file = new File(projectLocation + "/" + properties.get("services"));

		if (!file.exists()) {
			if (file.getName().endsWith(".js")) {
				try {
					new File(file.getParent()).mkdirs();
					file.createNewFile();
				} catch (Exception ex) {}
			} else {
				file.mkdirs();
			}
		}

		return file;
	}

	/**
	 * Sets new services path
	 * @param file Services directory or file
	 */
	public void setServices(File file) throws ActionException {
		properties.put("services", file.getPath().replace(projectLocation, ""));
		save();
	}

	/**
	 * Returns libs folder for another libraries
	 * @return Libraries folder
	 */
	public File getLibs() {
		File file = new File(projectLocation + "/" + properties.get("libs"));

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	/**
	 * Sets the new libs path
	 * @param file Libs directory
	 */
	public void setLibs(File file) throws ActionException {
		properties.put("libs", file.getPath().replace(projectLocation, ""));
		save();
	}

	public String getProjectType() {
		return properties.get("type");
	}

	public void setProjectType(String type) throws ActionException {
		properties.put("type", type);
		save();
	}

	/**
	 * Saves current setting to the file
	 */
	private void save() throws ActionException {
		StringBuilder stringBuilder = new StringBuilder();
		for(Map.Entry<String, String> entry: properties.entrySet()) {
			stringBuilder.append(entry.getKey());
			stringBuilder.append("=");
			stringBuilder.append(entry.getValue());
			stringBuilder.append("\n");
		}

		try {
			Files.write(initedFile.toPath(), stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException ex) {
			throw new ActionException(INITED_FILE_SAVE_ERROR);
		}
	}
}
