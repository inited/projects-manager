package cz.inited.ui.text.commands;

import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static cz.inited.utils.Strings.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ondre on 08.07.2016.
 */
public class CommandCreateTest {

	@Before
	public void before() throws URISyntaxException, IOException {
		Path path = Paths.get("");
		path = Files.write(path.resolve(".inited"), Files.readAllBytes(Paths.get(getClass().getResource("/tests/project/.inited").toURI())));
		System.out.println("File created: " + path.toAbsolutePath().toString());
	}

	@After
	public void after() throws IOException {
		Files.delete(Paths.get(".inited"));
		System.out.println("File deleted");
	}

	//@Test
	public void testTypeNotSupported() {
		CommandCreate commandCreate = new CommandCreate();
		String answer = commandCreate.run("create", "not_supported", "app_name");
		assertEquals(answer, CREATE_TYPE_NOT_SUPPORTED);
	}

	//@Test
	public void testNoName() {
		CommandCreate commandCreate = new CommandCreate();
		String answer = commandCreate.run("create", "not_supported");
		assertEquals(answer, CREATE_NO_APP_NAME);
	}

	//@Test
	public void testNoAppType() {
		CommandCreate commandCreate = new CommandCreate();
		String answer = commandCreate.run("create");
		assertEquals(answer, CREATE_NO_APP_TYPE);
	}

	//@Test
	public void testCordovaProjectCreation() {
		CommandCreate commandCreate = new CommandCreate();
		String answer = commandCreate.run("create", "--cordova", "test_project");
		assertEquals(answer, CREATE_PROJECT_CREATED);
	}

	//@Test
	public void testCordovaControllerCreation() throws URISyntaxException {
		CommandCreate commandCreate = new CommandCreate();
		String answer = commandCreate.run("create", "--cordova", "--controller", "testController.js");
		assertEquals(answer, CREATE_FILE_CREATED);

		File cFile = new File(getClass().getResource("/tests/project/www/app/controllers/TestController.js").toURI());
		assertTrue(cFile.exists() && cFile.isFile());
		cFile.delete();

		File tFile = new File(getClass().getResource("/tests/project/www/app/templates/test.html").toURI());
		assertTrue(tFile.exists() && tFile.isFile());
		tFile.delete();
	}

	//@Test
	public void testCordovaServiceCreation() {
		CommandCreate commandCreate = new CommandCreate();
		String answer = commandCreate.run("create", "--cordova", "--service", "testService");
		assertEquals(answer, CREATE_FILE_CREATED);
	}

}
