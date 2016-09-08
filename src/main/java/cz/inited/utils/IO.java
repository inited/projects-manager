package cz.inited.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by ondre on 08.07.2016.
 */
public final class IO {

	/**
	 * Returns String content of a file
	 * @param path Path to the file
	 * @return File content
	 * @throws IOException Error while reading file
	 */
	public static String getFileContent(Path path) throws IOException{
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);
		return content;
	}

	private IO() {

	}
}
