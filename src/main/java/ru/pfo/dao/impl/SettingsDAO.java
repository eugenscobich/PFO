package ru.pfo.dao.impl;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.pfo.dao.ISettingsDAO;
import ru.pfo.model.Settings;

@Repository("settingsDAO")
public class SettingsDAO implements ISettingsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(Object entity) {
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		try {
			session.save(entity);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}

	public Settings get(Serializable id) {

		return (Settings) getSession().get(Settings.class, id);
	}

	public Settings load(Serializable id) {
		return (Settings) getSession().load(Settings.class, id);
	}

	@Override
	public Settings getSettings() {

		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Settings.class);
		Settings settings = (Settings) criteria.uniqueResult();
		session.close();

		return settings;
	}

	@Override
	public void update(Object entity) {
		getSession().update(entity);
		getSession().flush();
	}
}
