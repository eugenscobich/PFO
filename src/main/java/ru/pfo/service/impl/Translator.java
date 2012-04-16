package ru.pfo.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import ru.pfo.service.ITranslator;
import ru.pfo.util.Language;

@Service("translator")
public class Translator implements ITranslator {

	private static final String ENCODING = "UTF-8";
	private static final String URL = "http://translate.google.com/translate_a/t";
	private static final String PARAMETERS = "?client=t&text=#TEXT#&hl=en&sl=#FROM#&tl=#TO#&multires=1&otf=1&ssel=0&tsel=0sc=1";

	private final static Logger LOG = Logger.getLogger(Translator.class);

	private String translate(final String text, final Language from, final Language to) {

		final String parameters = PARAMETERS.replaceAll("#TEXT#", text.replaceAll(" ", "%20").toString())
				.replaceAll("#FROM#", from.toString()).replaceAll("#TO#", to.toString());

		String urlstr = URL.concat(parameters);
		String content = null;
		try {
			HttpURLConnection uc = (HttpURLConnection) new URL(urlstr).openConnection();
			uc.setRequestMethod("GET");

			uc.setRequestProperty("User-Agent",
					"Mozilla/5.0 (X11; U; Linux i686; ru-RU; rv:10.0) Gecko/20100101 Firefox/10.0 ");
			uc.setRequestProperty("Referer", "http://translate.google.com/");
			uc.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			uc.setRequestProperty("Accept-Encoding", "gzip, deflate");
			uc.setRequestProperty("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3");
			uc.setRequestProperty("Connection", "keep-alive");

			content = toString(uc.getInputStream(), uc.getContentEncoding());

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

	private static String toString(InputStream inputStream, String encoding) throws IOException {
		if (encoding.equals("gzip")) {
			String string = null;
			StringBuilder outputBuilder = new StringBuilder();
			if (inputStream != null) {
				GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
				BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream, ENCODING));
				while (null != (string = reader.readLine())) {
					outputBuilder.append(string).append('\n');
				}
			}
			return outputBuilder.toString();
		}
		String string;
		StringBuilder outputBuilder = new StringBuilder();
		if (inputStream != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
			while (null != (string = reader.readLine())) {
				outputBuilder.append(string).append('\n');
			}
		}
		return outputBuilder.toString();
	}

	@Override
	public String[] getTranslatedText(String text) {
		String[] result = new String[2];
		String content = null;
		try {
			content = translate(text, Language.ENGLISH, Language.RUSSIAN);
		} catch (Exception e) {
			LOG.error(e, e);
		}

		if (content == null || content.isEmpty()) {
			result[0] = text;
			result[1] = text;
			return result;
		}
		String s = content.substring(4);
		int i = s.indexOf("\",");
		String title_ru = s.substring(0, i);
		result[0] = title_ru;

		s = s.substring(i);
		s = s.substring(3);
		i = s.indexOf("\",");

		s = s.substring(i);
		s = s.substring(3);
		i = s.indexOf("\",");
		String title_tr = s.substring(0, i);
		result[1] = title_tr;

		return result;
	}

}
