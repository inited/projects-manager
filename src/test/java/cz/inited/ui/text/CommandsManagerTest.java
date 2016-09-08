package cz.inited.ui.text;

import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static cz.inited.utils.Strings.COMMAND_NOT_fOUND;
import static org.junit.Assert.assertEquals;

/**
 * Created by ondre on 08.07.2016.
 */
public class CommandsManagerTest {

	ByteArrayOutputStream byteArrayOutputStream;
	PrintStream printStream;

	@Before
	public void before() {
		byteArrayOutputStream = new ByteArrayOutputStream();
		printStream = new PrintStream(byteArrayOutputStream);
		System.setOut(printStream);
	}

	//@Test
	public void testNotFoundCommand() {
		CommandsManager.getInstance().processCommand("non_existing");
		assertEquals(getTrimmedContent(), COMMAND_NOT_fOUND.trim());
	}

	private String getTrimmedContent() {
		return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8).trim();
	}
}
