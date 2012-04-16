package ru.pfo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import ru.pfo.sheduler.ProjectSheduler;

public class GoogleTranslate {

	private static final String ENCODING = "UTF-8";

	private static final String URL = "http://translate.google.com/translate_a/t";
	private static final String PARAMETERS = "?client=t&text=#TEXT#&hl=en&sl=#FROM#&tl=#TO#&multires=1&otf=1&ssel=0&tsel=0sc=1";
	private final static Logger LOG = Logger.getLogger(ProjectSheduler.class);

	public static String translate(final String text, final Language from, final Language to) {

		final String parameters = PARAMETERS.replaceAll("#TEXT#", text.replaceAll(" ", "%20").toString())
				.replaceAll("#FROM#", from.toString()).replaceAll("#TO#", to.toString());

		String urlstr = URL.concat(parameters);
		String content = null;
		try {
			HttpURLConnection uc = (HttpURLConnection) new URL(urlstr).openConnection();

			uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");

			content = toString(uc.getInputStream());

			if (uc.getResponseCode() != 200) {
				LOG.warn("The title: " + text + " translate unsuccessful!");
				LOG.warn("Error Code: " + uc.getResponseCode());
			}

		} catch (MalformedURLException e) {
			LOG.error(e, e);
		} catch (IOException e) {
			LOG.error(e, e);
		}
		return content;
	}

	private static String toString(InputStream inputStream) throws IOException {
		String string;
		StringBuilder outputBuilder = new StringBuilder();
		if (inputStream != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, ENCODING));
			while (null != (string = reader.readLine())) {
				outputBuilder.append(string).append('\n');
			}
		}
		return outputBuilder.toString();
	}
}
