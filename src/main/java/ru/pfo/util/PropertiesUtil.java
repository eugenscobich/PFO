package ru.pfo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesUtil extends PropertyPlaceholderConfigurer {

	private static final Logger LOG = Logger.getLogger(PropertiesUtil.class);

	private static Map<String, String> propertiesMap;

	// Default as in PropertyPlaceholderConfigurer
	private int springSystemPropertiesMode = SYSTEM_PROPERTIES_MODE_FALLBACK;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);
		propertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String valueStr = resolvePlaceholder(keyStr, props, springSystemPropertiesMode);
			propertiesMap.put(keyStr, valueStr);
		}

	}

	public static String getProperty(String name) {
		return propertiesMap.get(name);
	}

	public static Integer getPropertyInteger(String name) {
		try {
			return Integer.parseInt(propertiesMap.get(name));
		} catch (NumberFormatException e) {
			LOG.error(e, e);
		}
		return null;
	}

	public static Long getPropertyLong(String name) {
		try {
			return Long.parseLong(propertiesMap.get(name));
		} catch (NumberFormatException e) {
			LOG.error(e, e);
		}
		return null;
	}

}
