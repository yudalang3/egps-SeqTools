package module.benchensdownloader.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

public final class UrlUtils {

	private UrlUtils() {
	}

	public static URL toURL(String input) throws MalformedURLException {
		if (input == null) {
			throw new MalformedURLException("URL is null");
		}
		String trimmed = input.trim();
		if (trimmed.isEmpty()) {
			throw new MalformedURLException("URL is empty");
		}

		if (isLikelyWindowsPath(trimmed)) {
			return Path.of(trimmed).toUri().toURL();
		}

		try {
			if (trimmed.startsWith("file:") && trimmed.indexOf('\\') >= 0) {
				return URI.create(trimmed.replace('\\', '/')).toURL();
			}
			return URI.create(trimmed).toURL();
		} catch (IllegalArgumentException e) {
			if (trimmed.indexOf('\\') >= 0) {
				try {
					return URI.create(trimmed.replace('\\', '/')).toURL();
				} catch (IllegalArgumentException ignored) {
					// fall through
				}
			}
			MalformedURLException malformedURLException = new MalformedURLException("Invalid URL: " + trimmed);
			malformedURLException.initCause(e);
			throw malformedURLException;
		}
	}

	private static boolean isLikelyWindowsPath(String s) {
		return s.length() >= 3 && Character.isLetter(s.charAt(0)) && s.charAt(1) == ':'
				&& (s.charAt(2) == '\\' || s.charAt(2) == '/');
	}
}

