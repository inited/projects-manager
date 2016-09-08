package cz.inited;

import cz.inited.utils.exceptions.ActionException;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;
/**
 * Created by ondre on 08.07.2016.
 */
public class InitedTest {

	@Test
	public void testInitedCreationFromPath() throws URISyntaxException{
		Path path = Paths.get(getClass().getResource("/tests/project/.inited").toURI());
		Inited inited = Inited.fromPath(path);
		assertNotNull(inited);
	}

	@Test
	public void testSetController() throws ActionException, URISyntaxException {
		Path path = Paths.get(getClass().getResource("/tests/project/.inited").toURI());
		Inited inited = Inited.fromPath(path);
		File originalControllers = inited.getControllers();
		File controllersFile = new File(getClass().getResource("/tests/project/www/app/controllers.js").toURI());

		inited.setControllers(controllersFile);
		inited = Inited.fromPath(path);
		assertEquals(inited.getControllers().getAbsolutePath(), controllersFile.getAbsolutePath());
		inited.setControllers(originalControllers);
	}
}
