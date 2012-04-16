package ru.pfo.dao;

import java.io.Serializable;

import ru.pfo.model.Settings;

public interface ISettingsDAO {

	void save(Object entity);

	void update(Object entity);

	Settings get(Serializable id);

	Settings getSettings();

	Settings load(Serializable id);
}
