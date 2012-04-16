package ru.pfo.service;

import ru.pfo.model.Settings;

public interface ISettingsService {

	// @PostInitialize
	void start();

	void save(Settings category);

	void setNewCronExpresion(String cronExpresion);

	Settings getSettings();
}
