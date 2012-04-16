package ru.pfo.service.impl;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.pfo.Constant;
import ru.pfo.dao.ISettingsDAO;
import ru.pfo.model.Settings;
import ru.pfo.service.ISettingsService;
import ru.pfo.sheduler.ProjectSheduler;
import ru.pfo.util.PropertiesUtil;

@Service("settingsService")
public class SettingsService implements ISettingsService {

	private final Logger LOG = Logger.getLogger(SettingsService.class);

	private Settings settings;

	@Autowired
	private ISettingsDAO settingsDAO;

	@Autowired
	private ProjectSheduler projectSheduler;

	@PostConstruct
	public void start() {
		this.settings = settingsDAO.getSettings();
		if (settings == null) {
			LOG.info("Settings no found in db.");
			settings = new Settings();

			String cronExpresion = PropertiesUtil.getProperty(Constant.PROPS_KEY_CRON_EXPRESION);

			Long refreshInterval = PropertiesUtil.getPropertyLong(Constant.PROPS_KEY_REFRESH_INTERVAL);
			Integer numberOfPages = PropertiesUtil.getPropertyInteger(Constant.PROPS_KEY_NUMBER_OF_PAGES);
			Integer itemsOnPage = PropertiesUtil.getPropertyInteger(Constant.PROPS_KEY_ITEM_ON_PAGES);
			Long maxLivingLinks = PropertiesUtil.getPropertyLong(Constant.PROPS_KEY_MAX_LIVING_LINKS);

			settings.setCronExpresion(cronExpresion);
			settings.setItemsOnPage(itemsOnPage);
			settings.setMaxLivingLinks(maxLivingLinks);
			settings.setNumberOfPages(numberOfPages);
			settings.setRefreshInterval(refreshInterval);

			settingsDAO.save(settings);
		}
	}

	@Override
	@Transactional
	public void save(Settings settings) {

		settingsDAO.update(settings);
		if (!this.settings.getCronExpresion().equals(settings.getCronExpresion())) {
			projectSheduler.stop();
			projectSheduler.startWithNewCronExpresion(settings.getCronExpresion());
		}

		this.settings = settings;
	}

	@Override
	public Settings getSettings() {
		return this.settings.clone();
	}

	@Override
	public void setNewCronExpresion(String cronExpresion) {
		settings.setCronExpresion(cronExpresion);
		settingsDAO.save(settings);
	}
}
