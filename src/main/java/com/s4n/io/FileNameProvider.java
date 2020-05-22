package com.s4n.io;

import java.util.List;

public interface FileNameProvider {
	List<String> resolveInputFileNames(int count);

	String provideInputPath(String path);

	String provideOutputPath(String path);
}
