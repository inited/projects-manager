package cz.inited.ui.text;

import cz.inited.Project;
import cz.inited.utils.exceptions.ActionException;
import cz.inited.utils.exceptions.MissingTypeException;
import cz.inited.utils.exceptions.ProjectException;
import org.junit.Before;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by ondre on 11.07.2016.
 */
public class ProjectTest {

	Project project;

	@Before
	public void before() throws ProjectException{
		project = new Project(Paths.get("/tests/project/.inited"));
	}

	//@Test
	public void testGetSetProjectType() throws MissingTypeException, ActionException, ProjectException {
		Project.ProjectType projectType = project.getType();
		assertEquals(projectType, Project.ProjectType.CORDOVA);

		project.setType(Project.ProjectType.INITED);
		project = new Project(Paths.get("/tests/project/.inited"));
		projectType = project.getType();
		assertEquals(projectType, Project.ProjectType.INITED);

		project.setType(Project.ProjectType.CORDOVA);
	}
}
