package module.localblast.gui.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
	private static final Logger log = LoggerFactory.getLogger(Util.class);

	public static List<String> splitCommandTokens(String command) {
		List<String> tokens = new ArrayList<>();
		if (command == null) {
			return tokens;
		}
		StringTokenizer stringTokenizer = new StringTokenizer(command);
		while (stringTokenizer.hasMoreTokens()) {
			tokens.add(stringTokenizer.nextToken());
		}
		return tokens;
	}

	public static void printMessage(final InputStream input, boolean isError, JTextArea textArea_normal,
			JTextArea textArea_error) {
		new Thread(() -> {
			Reader reader = new InputStreamReader(input);
			BufferedReader bf = new BufferedReader(reader);
			String line = null;
			try {
				while ((line = bf.readLine()) != null) {
					if (isError) {
						textArea_error.append(line + "\n");
						// System.err.println(line);
					} else {
						textArea_normal.append(line + "\n");
						// System.out.println(line);
					}
				}
			} catch (IOException e) {
				log.error("Failed to read process output.", e);
			}
		}).start();
	}

	public static void printMessage(final InputStream input, JTextArea textArea_normal) {
//		new Thread(() -> {
			Reader reader = new InputStreamReader(input);
			BufferedReader bf = new BufferedReader(reader);
			String line = null;
			try {
				while ((line = bf.readLine()) != null) {
					textArea_normal.append(line);
					textArea_normal.append("\n");
				}
			} catch (IOException e) {
				log.error("Failed to read process output.", e);
			}
//		}).start();
	}

}
