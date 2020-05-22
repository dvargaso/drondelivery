package com.s4n.io;

import java.io.IOException;
import java.util.List;

/**
 * this class provides methods for reading from the file system
 */
public interface FileReader {

	/**
	 * This method reads a file a text returns the lines included
	 * @param path
	 * @return
	 * @throws IOException
	 */
	List<String> readFile(String path) throws IOException;
}
