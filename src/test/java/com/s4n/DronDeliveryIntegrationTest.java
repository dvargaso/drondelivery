package com.s4n;

import com.s4n.io.FileReader;
import com.s4n.io.impl.FileReaderImpl;
import com.s4n.util.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Arrays.asList;


/**
 * El desarrollo de esta clase no pudo ser completado por falta de tiempo
 */
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DronDeliveryIntegrationTest {

	private FileReader reader;

	@BeforeEach
	void setUp() throws IOException {
		reader = new FileReaderImpl();
		Configuration.addProperty("dron.input.data.path", "./src/test/data/deliveryData/input/");
		Configuration.addProperty("dron.output.data.path", "./src/test/data/deliveryData/output/");
		Configuration.addProperty("dron.count", "1");


	}


	@Test
	void testCompleteExecution_straightPath() throws IOException {
		String ruta = "AAAA";
		createRouteInputFile(asList(ruta), "in01.txt");
		DronDeliveryApp.main(null);

	}

	private void assertOutputFileContents(String fileName, List<String> expectedOutputs) throws IOException {

		List<String> outputLines = reader.readFile(Configuration.getProperty("dron.output.data.path") + fileName);


	}

	private void createRouteInputFile(List<String> routes, String name) throws IOException {
		Path path = Paths.get(Configuration.getProperty("dron.input.data.path") + name);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			for (String route : routes) {
				writer.write(route);
			}
		}
	}

}


