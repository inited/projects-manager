package cz.inited.actions.cordova;

import cz.inited.Inited;
import cz.inited.utils.IO;
import cz.inited.utils.exceptions.ActionException;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ondre on 08.07.2016.
 */
public class CordovaControllerTest {

	private Inited inited;

	@Before
	public void before() throws Exception {
		inited = Inited.fromPath(Paths.get(getClass().getResource("/tests/project/.inited").toURI()));
	}

	//@Test
	public void testMultiFilesController() throws ActionException, URISyntaxException{
		CordovaController cordovaController = new CordovaController();
		cordovaController.create("test", inited);

		File cFile = new File(getClass().getResource("/tests/project/www/app/controllers/TestController.js").toURI());
		assertTrue(cFile.exists() && cFile.isFile());
	}

	//@Test
	public void testOneFileControllerWithItsCreation() throws URISyntaxException, ActionException, IOException {
		File controllersFile = new File(getClass().getResource("/tests/project/www/app/controllers.js").toURI());
		inited.setControllers(controllersFile);
		boolean deleted = controllersFile.delete();
		assertTrue(deleted);

		CordovaController cordovaController = new CordovaController();
		cordovaController.create("test", inited);
		assertTrue(controllersFile.exists() && controllersFile.isFile());
		String content = IO.getFileContent(controllersFile.toPath());
		assertFalse(content.isEmpty());
	}
}
