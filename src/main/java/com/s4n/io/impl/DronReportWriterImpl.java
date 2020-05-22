package com.s4n.io.impl;

import com.s4n.domain.Position;
import com.s4n.io.DronReportWriter;
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
public class DronReportWriterImpl implements DronReportWriter {

	@Override
	public void writeToFile(String targetPath, List<Position> deliveries, String message) {
		Path path = Paths.get(targetPath);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			if (!CollectionUtils.isEmpty(deliveries)) {
				writer.write("== Reporte de entregas ==");
				for (Position delivery : deliveries) {
					writer.write("\n(" + delivery.getX() + ", " + delivery.getY() + ") dirección "
							+ delivery.getDirection().toString());
				}
				if(!StringUtils.isEmpty(message)){
					writer.write(message);
				}
			}

		} catch (IOException e) {
			log.error("Error escribiendo en la ruta {}", targetPath);

		}

	}
}
