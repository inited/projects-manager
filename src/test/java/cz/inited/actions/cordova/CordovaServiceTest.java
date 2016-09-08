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
 * Created by ondre on 09.07.2016.
 */
public class CordovaServiceTest {
	private Inited inited;

	@Before
	public void before() throws Exception {
		inited = Inited.fromPath(Paths.get(getClass().getResource("/tests/project/.inited").toURI()));
	}

	//@Test
	public void testMultiFilesService() throws ActionException, URISyntaxException {
		CordovaService cordovaService = new CordovaService();
		cordovaService.create("test", inited);

		File file = new File(getClass().getResource("/tests/project/www/app/services/TestService.js").toURI());
		assertTrue(file.exists() && file.isFile());
	}

	//@Test
	public void testOneFileServiceWithItsCreation() throws URISyntaxException, ActionException, IOException {
		File servicesFile = new File(getClass().getResource("/tests/project/www/app/services.js").toURI());
		inited.setServices(servicesFile);
		boolean deleted = servicesFile.delete();
		assertTrue(deleted);

		CordovaService cordovaService = new CordovaService();
		cordovaService.create("test", inited);
		assertTrue(servicesFile.exists() && servicesFile.isFile());
		String content = IO.getFileContent(servicesFile.toPath());
		assertFalse(content.isEmpty());
	}
}
