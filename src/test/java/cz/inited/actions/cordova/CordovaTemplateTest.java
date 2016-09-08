package cz.inited.actions.cordova;

import cz.inited.Inited;
import cz.inited.utils.exceptions.ActionException;
import org.junit.Before;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * Created by ondre on 09.07.2016.
 */
public class CordovaTemplateTest {

	private Inited inited;

	@Before
	public void before() throws Exception {
		inited = Inited.fromPath(Paths.get(getClass().getResource("/tests/project/.inited").toURI()));
	}

	//@Test
	public void testTemplate() throws ActionException, URISyntaxException {
		CordovaTemplate cordovaTemplate = new CordovaTemplate();
		cordovaTemplate.create("test", inited);

		File file = new File(getClass().getResource("/tests/project/www/app/templates/test.html").toURI());
		assertTrue(file.exists() && file.isFile());
	}

}
