package application.gui;

import java.io.InputStream;

import javax.annotation.Resource;

public class ResourceLoader {
	public static InputStream load(String path) {
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		if (input == null) {
			input = Resource.class.getResourceAsStream("/" + path);
		}
		return input;
	}
}
