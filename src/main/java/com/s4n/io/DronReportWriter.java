package com.s4n.io;

import com.s4n.domain.Position;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class DronReportWriter {

	public void writeToFile(String targetPath, List<Position> deliveries) {
		Path path = Paths.get(targetPath);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {

			writer.write("== Reporte de entregas ==");
			for (Position delivery : deliveries) {
				writer.write("\n(" + delivery.getX() + ", " + delivery.getY() + ") dirección "
						+ delivery.getDirection().toString());
			}


		} catch (IOException e) {
			log.error("Error escribiendo en la ruta {}", targetPath);

		}

	}
}
