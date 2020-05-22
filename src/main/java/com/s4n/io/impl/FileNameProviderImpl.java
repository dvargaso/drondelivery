package com.s4n.io.impl;

import com.s4n.io.FileNameProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FileNameProviderImpl implements FileNameProvider {

	public static final String DRONE_ROUTE_FILE_PREFIX = "in";
	public static final String DRONE_OUTPUT_FILE_PREFIX = "out";
	public static final String TXT_FILE_POSFIX = ".txt";

	@Override
	public List<String> resolveInputFileNames(int count) {
		List<String> fileNameList = new ArrayList<>();
		IntStream.rangeClosed(1, count).forEach(i -> {
			String number = (i < 10) ? ("0" + i) : String.valueOf(i);
			fileNameList.add(DRONE_ROUTE_FILE_PREFIX + number + TXT_FILE_POSFIX);
		});

		return fileNameList;
	}

	@Override
	public String provideInputPath(String path){
		return  "deliveryData/input/" + path;
	}

	@Override
	public String provideOutputPath(String path){
		return  "deliveryData/output/"
				+ path.replace(DRONE_ROUTE_FILE_PREFIX, DRONE_OUTPUT_FILE_PREFIX);
	}


}
