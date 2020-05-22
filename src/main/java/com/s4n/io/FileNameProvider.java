package com.s4n.io;

import java.util.List;

/**
 * This class provides methods for solving the route file names in the file system
 */
public interface FileNameProvider {

	/**
	 * This method resolves the file names for the output files
	 * @param count
	 * @return
	 */
	List<String> resolveInputFileNames(int count);

	/**
	 *
	 * This method resolves the input file location in the file system
	 * @param path
	 * @return
	 */
	String provideInputPath(String path);

	/**
	 * This method resolves the output file location in the file system
	 * @param path
	 * @return
	 */
	String provideOutputPath(String path);
}
