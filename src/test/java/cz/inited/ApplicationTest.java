package cz.inited;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static cz.inited.utils.Strings.HELP;
import static org.junit.Assert.assertEquals;

/**
 * Created by ondre on 08.07.2016.
 */
public class ApplicationTest {

	ByteArrayOutputStream byteArrayOutputStream;
	PrintStream printStream;

	@Before
	public void before() {
		byteArrayOutputStream = new ByteArrayOutputStream();
		printStream = new PrintStream(byteArrayOutputStream);
		System.setOut(printStream);
	}

	@Test
	public void testMainHelp() {
		Application.main();
		assertEquals(getTrimmedContent(), HELP.trim());
	}

	private String getTrimmedContent() {
		return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8).trim();
	}
}
