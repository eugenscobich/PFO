package ru.pfo.dao;

import java.io.Serializable;
import java.util.List;

import ru.pfo.model.Category;

public interface ICategoryDAO {

	void save(Object entity);

	Category get(Serializable id);

	Category load(Serializable id);

	List<Category> getAll();

	boolean isExist(Category category);

	void update(Object category);

	Category getCategoryByUuid(String uuid);

	Category getCategoryWithVideoNewses(Long categoryId, int start, int itemsOnPage);

	Long getCountOfCategoryWithVideosNewses(Long categoryId);

	List<Category> getCategoriesForVideoNews(Long videoNewsId);

}
