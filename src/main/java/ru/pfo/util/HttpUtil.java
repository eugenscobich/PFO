package ru.pfo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class HttpUtil {

	private final static Logger LOG = Logger.getLogger(HttpUtil.class);

	public static final String POUND = "#";
	public static final String QUESTION = "?";
	public static final String AMPERSAND = "&";
	public static final String EQUAL = "=";
	public static final String BLANK = "";

	public static String addParameter(String url, String name, Object obj) {
		String value = String.valueOf(obj);
		if (url == null) {
			return null;
		}

		String[] urlArray = stripURLAnchor(url, HttpUtil.POUND);

		url = urlArray[0];

		String anchor = urlArray[1];

		StringBuilder sb = new StringBuilder(7);

		sb.append(url);

		if (url.indexOf(HttpUtil.QUESTION) == -1) {
			sb.append(HttpUtil.QUESTION);
		} else if (!url.endsWith(HttpUtil.QUESTION) && !url.endsWith(HttpUtil.AMPERSAND)) {
			sb.append(HttpUtil.AMPERSAND);
		}

		sb.append(name);
		sb.append(HttpUtil.EQUAL);
		try {
			sb.append(URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LOG.warn(e);
			sb.append(value);
		}
		sb.append(anchor);
		return sb.toString();
	}

	public static String setParameter(String url, String name, String value) {
		if (url == null) {
			return null;
		}

		url = removeParameter(url, name);

		return addParameter(url, name, value);
	}

	public static String removeParameter(String url, String name) {

		int pos = url.indexOf(HttpUtil.QUESTION);

		if (pos == -1) {
			return url;
		}

		String[] array = stripURLAnchor(url, HttpUtil.POUND);

		url = array[0];

		String anchor = array[1];

		StringBuilder sb = new StringBuilder();

		sb.append(url.substring(0, pos + 1));
		String query = url.substring(pos + 1, url.length());
		String[] parameters = query.split(HttpUtil.AMPERSAND);

		for (String parameter : parameters) {
			if (parameter.length() > 0) {
				String[] kvp = parameter.split(HttpUtil.EQUAL);

				String key = kvp[0];

				String value = HttpUtil.BLANK;

				if (kvp.length > 1) {
					value = kvp[1];
				}

				if (!key.equals(name)) {
					sb.append(key);
					sb.append(HttpUtil.EQUAL);
					sb.append(value);
					sb.append(HttpUtil.AMPERSAND);
				}
			}
		}

		url = sb.toString().replace(HttpUtil.AMPERSAND + HttpUtil.AMPERSAND, HttpUtil.AMPERSAND);

		if (url.endsWith(HttpUtil.AMPERSAND)) {
			url = url.substring(0, url.length() - 1);
		}

		if (url.endsWith(HttpUtil.QUESTION)) {
			url = url.substring(0, url.length() - 1);
		}

		return url + anchor;
	}

	private static String[] stripURLAnchor(String url, String separator) {
		String anchor = "";

		int pos = url.indexOf(separator);

		if (pos != -1) {
			anchor = url.substring(pos);
			url = url.substring(0, pos);
		}

		return new String[] { url, anchor };
	}

	public static String removeAllParameters(String url) {
		if (url == null) {
			return null;
		}

		String[] urlArray = stripURLAnchor(url, HttpUtil.POUND);

		url = urlArray[0];

		String anchor = urlArray[1];

		int i = url.indexOf(HttpUtil.QUESTION);
		if (i > 0) {
			url = url.substring(0, i);
		}
		return url + anchor;
	}
}
