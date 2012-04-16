package ru.pfo.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.pfo.dao.ICategoryDAO;
import ru.pfo.model.Category;

@Repository("categoryDAO")
public class CategoryDAO implements ICategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(Object entity) {
		getSession().save(entity);
		getSession().flush();
	}

	public Category get(Serializable id) {

		return (Category) getSession().get(Category.class, id);
	}

	public Category load(Serializable id) {
		return (Category) getSession().load(Category.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll() {
		Query q = getSession()
				.createQuery(
						"select distinct ct from Category ct join ct.videoNewses ctvn where ctvn.addedDate < :nowDate order by ct.name");
		q.setParameter("nowDate", new Date());

		List<Category> categories = new ArrayList<Category>();
		for (Iterator<Category> it = q.iterate(); it.hasNext();) {
			Category c = it.next();
			Query q1 = getSession()
					.createQuery(
							"select count(*) from Category ct join ct.videoNewses ctvn where ctvn.addedDate < :nowDate and ct.id = :ctId");
			q1.setParameter("ctId", c.getId());
			q1.setParameter("nowDate", new Date());
			Long count = (Long) q1.uniqueResult();
			c.setSize(count);
			categories.add(c);
		}

		return categories;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isExist(Category category) {
		Criteria criteria = getSession().createCriteria(Category.class)
				.add(Restrictions.eq("uuid", category.getUuid()));

		List<Category> categories = criteria.list();
		return categories.size() > 0 ? true : false;
	}

	@Override
	public void update(Object entity) {
		getSession().update(entity);
		getSession().flush();

	}

	@Override
	public Category getCategoryByUuid(String uuid) {
		Criteria criteria = getSession().createCriteria(Category.class).add(Restrictions.eq("uuid", uuid));
		return (Category) criteria.uniqueResult();
	}

	@Override
	public Category getCategoryWithVideoNewses(Long categoryId, int start, int itemsOnPage) {
		Criteria criteria = getSession().createCriteria(Category.class);
		criteria.add(Restrictions.eq("id", categoryId));
		Criteria vnc = criteria.createCriteria("videoNewses");
		vnc.setFirstResult(start);
		vnc.setMaxResults(itemsOnPage);
		vnc.addOrder(Order.desc("addedDate"));
		vnc.add(Restrictions.lt("addedDate", new Date()));
		criteria.setFetchMode("videoNewses", FetchMode.JOIN);
		Category category = (Category) criteria.uniqueResult();
		category.getVideoNewses().get(0);
		return category;
	}

	@Override
	public Long getCountOfCategoryWithVideosNewses(Long categoryId) {
		Criteria criteria = getSession().createCriteria(Category.class);
		criteria.add(Restrictions.eq("id", categoryId));
		criteria.setFetchMode("videoNewses", FetchMode.JOIN);
		Criteria vnc = criteria.createCriteria("videoNewses", Criteria.LEFT_JOIN);
		vnc.add(Restrictions.lt("addedDate", new Date()));
		vnc.setProjection(Projections.rowCount());
		Long rowCount = (Long) vnc.uniqueResult();

		return rowCount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategoriesForVideoNews(Long videoNewsId) {
		Criteria criteria = getSession().createCriteria(Category.class);
		criteria.setFetchMode("videoNewses", FetchMode.JOIN);
		Criteria vnc = criteria.createCriteria("videoNewses", Criteria.LEFT_JOIN);
		vnc.add(Restrictions.eq("id", videoNewsId));
		return (List<Category>) vnc.list();
	}

}
