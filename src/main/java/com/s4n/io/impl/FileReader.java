package com.s4n.io.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileReader {


	public List<String> readFile(final String path) throws IOException {
		Stream<String> lines = Files.lines(Paths.get(path), StandardCharsets.UTF_8);
		List<String> result = lines.collect(Collectors.toList());
		return result;
	}
}
