package module.localblast.gui.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
	private static final Logger log = LoggerFactory.getLogger(Util.class);

	public static void appendLineOnEdt(JTextArea textArea, String line) {
		if (textArea == null) {
			return;
		}
		if (SwingUtilities.isEventDispatchThread()) {
			textArea.append(line);
			textArea.append("\n");
		} else {
			SwingUtilities.invokeLater(() -> {
				textArea.append(line);
				textArea.append("\n");
			});
		}
	}

	public static void printMessage(final InputStream input, boolean isError, JTextArea textArea_normal,
			JTextArea textArea_error) {
		new Thread(() -> {
			try (Reader reader = new InputStreamReader(input); BufferedReader bf = new BufferedReader(reader)) {
				String line = null;
				while ((line = bf.readLine()) != null) {
					if (isError) {
						appendLineOnEdt(textArea_error, line);
					} else {
						appendLineOnEdt(textArea_normal, line);
					}
				}
			} catch (Exception e) {
				log.error("Failed to read process output.", e);
			}
		}).start();
	}

	public static void printMessage(final InputStream input, JTextArea textArea_normal) {
		try (Reader reader = new InputStreamReader(input); BufferedReader bf = new BufferedReader(reader)) {
			String line = null;
			while ((line = bf.readLine()) != null) {
				appendLineOnEdt(textArea_normal, line);
			}
		} catch (Exception e) {
			log.error("Failed to read process output.", e);
		}
	}

}
