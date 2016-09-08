package cz.inited;

import cz.inited.utils.exceptions.ActionException;
import cz.inited.utils.exceptions.MissingTypeException;
import cz.inited.utils.exceptions.ProjectException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ondre on 08.07.2016.
 */
public class Project {

	private Path initedFilePath;
	private Inited inited;

	public Project(Path path) throws ProjectException {
		inited = Inited.fromPath(path);
		initedFilePath = inited.getFile().toPath();
	}

	public Project() {

	}

	public Path getInitedFilePath() {
		return initedFilePath;
	}

	public Inited getInited() {
		return inited;
	}

	public ProjectType getType() throws MissingTypeException {
		try {
			return ProjectType.valueOf(inited.getProjectType());
		} catch (NullPointerException ex) {
			throw new MissingTypeException();
		}
	}

	public void setType(ProjectType projectType) throws ActionException{
		inited.setProjectType(projectType.name());
	}

	public enum ProjectType {
		CORDOVA,
		INITED
	}

	public List<Path> getScripts() {
		List<Path> paths = new ArrayList<>();
		Path initedParent = initedFilePath.getParent();
		if(Files.isDirectory(initedParent)) {
			try {
				Files.walk(initedParent).forEach(path -> {
					if(Files.isRegularFile(path)) {
						if(getFileExtension(path).equals("sh")) {
							paths.add(path);
						}
					}
				});
			} catch(Exception ex) {
				return paths;
			}
		}

		return paths;
	}

	private static String getFileExtension(Path file) {
		String fileName = file.toFile().getName();
		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".")+1);
		else return "";
	}
}
