package ru.pfo.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.pfo.dao.IVideoNewsDAO;
import ru.pfo.model.VideoNews;

@Repository("videoNewsDAO")
public class VideoNewsDAO implements IVideoNewsDAO {

	private static final Logger LOG = Logger.getLogger(VideoNewsDAO.class);
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(Object entity) {
		getSession().save(entity);
		getSession().flush();
	}

	public VideoNews get(Serializable id) {

		return (VideoNews) getSession().get(VideoNews.class, id);
	}

	public VideoNews load(Serializable id) {
		return (VideoNews) getSession().load(VideoNews.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isExist(VideoNews videoNews) {
		Criteria criteria = getSession().createCriteria(VideoNews.class).add(
				Restrictions.eq("videoKey", videoNews.getVideoKey()));

		List<VideoNews> videoNewses = criteria.list();
		return videoNewses.size() > 0 ? true : false;
	}

	@Override
	public List<VideoNews> getVideoNewses(int start, int nrOfItems) {
		Criteria criteria = getDefaultCriteria();
		criteria.setFirstResult(start);
		criteria.setMaxResults(nrOfItems);

		@SuppressWarnings("unchecked")
		List<VideoNews> videoNewses = criteria.list();
		return videoNewses;
	}

	@Override
	public long getCountOfVideosNewses() {
		Long rowCount = (Long) getDefaultCriteria().setProjection(Projections.rowCount()).uniqueResult();
		return rowCount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VideoNews> getVideoNewsesByCategory(Long categoryId, int start, int itemsOnPage) {
		Criteria vnc = getDefaultCriteria();
		vnc.setFirstResult(start);
		vnc.setMaxResults(itemsOnPage);

		Criteria cc = vnc.createCriteria("categories");
		cc.add(Restrictions.eq("id", categoryId));

		return vnc.list();
	}

	@Override
	public long getCountOfVideosNewsesByCategory(Long categoryId) {
		Criteria vnc = getDefaultCriteria();
		Criteria cc = vnc.createCriteria("categories");
		cc.add(Restrictions.eq("id", categoryId));
		Long rowCount = (Long) vnc.setProjection(Projections.rowCount()).uniqueResult();
		return rowCount;
	}

	@Override
	public VideoNews getVideoNewsByTitle_ru(String title_ru) {
		Criteria criteria = getSession().createCriteria(VideoNews.class);
		criteria.add(Restrictions.eq("title_ru", title_ru));
		VideoNews videoNews = null;
		try {
			videoNews = (VideoNews) criteria.uniqueResult();
		} catch (HibernateException e) {
			LOG.error(e, e);
		}
		return videoNews;
	}

	@Override
	public void update(Object entity) {
		getSession().update(entity);
		getSession().flush();

	}

	@Override
	public boolean isExist(String videoKey) {
		Criteria criteria = getSession().createCriteria(VideoNews.class).add(Restrictions.eq("videoKey", videoKey));
		@SuppressWarnings("unchecked")
		List<VideoNews> videoNewses = criteria.list();
		return videoNewses.size() > 0 ? true : false;
	}

	@Override
	public List<VideoNews> getBestVideoNewses(int minProcent) {
		Criteria criteria = getSession().createCriteria(VideoNews.class).add(Restrictions.gt("rating", minProcent));
		criteria.add(Restrictions.lt("addedDate", new Date()));
		@SuppressWarnings("unchecked")
		List<VideoNews> videoNewses = criteria.list();
		return videoNewses;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<VideoNews> getAllVideoNewses() {
		Criteria criteria = getSession().createCriteria(VideoNews.class);
		return criteria.list();
	}

	@Override
	public void remove(VideoNews videoNews) {
		getSession().delete(videoNews);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VideoNews> getVideoNewsesByStringArray(String[] searchArray, int start, int itemsOnPage) {
		Criteria vnc = getDefaultCriteria();
		vnc.setFirstResult(start);
		vnc.setMaxResults(itemsOnPage);

		String sqlRestriction = "";
		int i = 1;
		for (String str : searchArray) {
			sqlRestriction += "{alias}.title_tr like \"%" + str + "%\" OR " + "{alias}.title_ru like \"%" + str
					+ "%\" OR " + "{alias}.title like \"%" + str + "%\"";
			if (i < searchArray.length) {
				sqlRestriction += " OR ";
			}
			i++;
		}
		vnc.add(Restrictions.sqlRestriction(sqlRestriction));
		return vnc.list();

	}

	@Override
	public long getCountOfVideoNewsesByStringArray(String[] searchArray) {
		Criteria vnc = getDefaultCriteria();
		String sqlRestriction = "";
		int i = 1;
		for (String str : searchArray) {
			sqlRestriction += "{alias}.title_tr like \"%" + str + "%\" OR " + "{alias}.title_ru like \"%" + str
					+ "%\" OR " + "{alias}.title like \"%" + str + "%\"";
			if (i < searchArray.length) {
				sqlRestriction += " OR ";
			}
			i++;
		}
		vnc.add(Restrictions.sqlRestriction(sqlRestriction));
		Long rowCount = (Long) vnc.setProjection(Projections.rowCount()).uniqueResult();
		return rowCount;
	}

	private Criteria getDefaultCriteria() {
		Criteria criteria = getSession().createCriteria(VideoNews.class);
		criteria.add(Restrictions.lt("addedDate", new Date()));
		criteria.addOrder(Order.desc("addedDate"));
		return criteria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VideoNews> getHidenVideoNewses() {
		Criteria criteria = getSession().createCriteria(VideoNews.class);
		criteria.add(Restrictions.eq("hiden", true));
		return criteria.list();
	}
}
