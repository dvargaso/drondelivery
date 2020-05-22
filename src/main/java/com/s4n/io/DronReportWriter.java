package com.s4n.io;

import com.s4n.domain.Position;

import java.util.List;

/**
 * This class performs write operations in the file system
 */
public interface DronReportWriter {
	/**
	 * This method writes the deliveries output in a text file in the specified location
	 * @param targetPath
	 * @param deliveries
	 * @param message
	 */
	void writeToFile(String targetPath, List<Position> deliveries, String message);
}
