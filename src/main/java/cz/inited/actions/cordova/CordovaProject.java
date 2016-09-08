package cz.inited.actions.cordova;

import cz.inited.Inited;
import cz.inited.Project;
import cz.inited.utils.FileTypes;
import cz.inited.utils.IO;
import cz.inited.utils.exceptions.ActionException;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static cz.inited.utils.Strings.*;

/**
 * Created by ondre on 08.07.2016.
 */
public class CordovaProject extends Project {

	private static final Logger LOGGER = Logger.getLogger(CordovaProject.class);

	/**
	 * Creates new cordova project from templates on github by cloning it and
	 * updating variables in files
	 * @param name
	 * @throws ActionException
	 */
	public void create(String name) throws ActionException {
		CloneCommand command = new CloneCommand();
		command.setURI("https://github.com/inited/cordova-project-template.git");
		LOGGER.info(CREATE_LOG_CLONNING);

		File file = new File(name);
		if (!file.exists()) {
			boolean created = file.mkdirs();
			if (!created) {
				throw new ActionException(CREATE_CREATING_FOLDER_ERROR);
			}
		} else {
			if (!file.isDirectory()) {
				throw new ActionException(CREATE_NOT_FOLDER_ERROR);
			} else if (file.list().length>0) {
				throw new ActionException(CREATE_FOLDER_NOT_EMPTY);
			}
		}
		command.setDirectory(file);
		try {
			Git git = command.call();
			git.close();
			LOGGER.info(CREATE_LOG_CREATING_PROJECT);
			File gitDir = new File(file.toURI().getPath() + "/.git");
			if (gitDir.exists() && gitDir.isDirectory()) {
				try {
					FileUtils.delete(gitDir, FileUtils.RECURSIVE);
				} catch (IOException ex) {
					throw new ActionException(CREATE_GIT_FOLDER_NOT_REMOVED);
				}
			}
			replaceNames(file);
		} catch (GitAPIException ex) {
			ex.printStackTrace();
			throw new ActionException(CREATE_CLONE_ERROR);
		}
	}

	/**
	 * Creates file for cordova project
	 * @param fileType type of created file
	 * @param name file name
	 * @param inited inited settings file
	 * @throws ActionException exception while creating file
	 */
	public void createFile(FileTypes fileType, String name, Inited inited) throws ActionException {
		switch(fileType) {
			case CONTROLLER:
				CordovaController cordovaController = new CordovaController();
				cordovaController.create(name, inited);
			case TEMPLATE:
				CordovaTemplate cordovaTemplate = new CordovaTemplate();
				cordovaTemplate.create(name, inited);
				break;
			case SERVICE:
				CordovaService cordovaService = new CordovaService();
				cordovaService.create(name, inited);
				break;
			default:
				throw new ActionException(CREATE_TYPE_NOT_SUPPORTED);
		}
	}

	/**
	 * Replaces project names in a initial project files
	 * @param file
	 * @throws ActionException
	 */
	private void replaceNames(File file) throws ActionException{
		String name = file.getName();
		Path packageJsonPath = new File(file.getPath() + "/package.json").toPath();
		Path configXmlPath = new File(file.getPath() + "/config.xml").toPath();
		Path indexHtmlPath = new File(file.getPath() + "/www/index.html").toPath();
		Path homeHtmlPath = new File(file.getPath() + "/www/app/templates/home.html").toPath();
		Path initedPath = new File(file.getPath() + "/.inited").toPath();
		Path abuildPath = new File(file.getPath() + "/abuild.sh").toPath();
		Path adistPath = new File(file.getPath() + "/adist.sh").toPath();
		Path apubPath = new File(file.getPath() + "/apub.sh").toPath();
		Path areleasePath = new File(file.getPath() + "/arelease.sh").toPath();
		Path ibuildPath = new File(file.getPath() + "/ibuild.sh").toPath();
		Path idistPath = new File(file.getPath() + "/idist.sh").toPath();
		Path ipubPath = new File(file.getPath() + "/ipub.sh").toPath();
		Path ireleasePath = new File(file.getPath() + "/irelease.sh").toPath();
		replaceNameIn(packageJsonPath, name);
		replaceNameIn(configXmlPath, name);
		replaceSafeNameIn(configXmlPath, name);
		replaceNameIn(indexHtmlPath, name);
		replaceNameIn(homeHtmlPath, name);
		replaceNameIn(initedPath, name);
		replaceNameIn(abuildPath, name);
		replaceNameIn(adistPath, name);
		replaceNameIn(apubPath, name);
		replaceNameIn(areleasePath, name);
		replaceNameIn(ibuildPath, name);
		replaceNameIn(idistPath, name);
		replaceNameIn(ipubPath, name);
		replaceNameIn(ireleasePath, name);
	}

	/**
	 * Replaces $projectname$ variable in given file with given name
	 * @param file File to be variable replaced in
	 * @param name Name to be variable replaced with
	 * @throws ActionException
	 */
	private void replaceNameIn(Path file, String name) throws ActionException {
		try {
			String content = IO.getFileContent(file);
			content = content.replace("$projectname$", name);
			Files.write(file, content.getBytes(StandardCharsets.UTF_8));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ActionException(CREATE_REPLACING_ERROR);
		}
	}

	private void replaceSafeNameIn(Path file, String name) throws ActionException {
		if(name.contains(" ")) {
			name = name.replace(" ", "-");
			name = name.toLowerCase();
		}
		try {
			String content = IO.getFileContent(file);
			content = content.replace("$safeprojectname$", name);
			Files.write(file, content.getBytes(StandardCharsets.UTF_8));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ActionException(CREATE_REPLACING_ERROR);
		}
	}
}
