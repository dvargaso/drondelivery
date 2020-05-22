package com.s4n.io;

import com.s4n.domain.Position;

import java.util.List;

public interface DronReportWriter {
	void writeToFile(String targetPath, List<Position> deliveries, String message);
}
