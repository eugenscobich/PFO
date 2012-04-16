package ru.pfo.service;

import java.util.List;

import ru.pfo.model.Category;
import ru.pfo.model.VideoNews;

public interface ICategoryService {

	void save(Category category);

	void update(Category category);

	Category getNewCategory();

	List<Category> getAllCategories();

	void persistCategory(List<Category> categories);

	Category getCategoryWithVideoNewses(Long categoryId, Integer page);

	int getCategoryTotalPage(Long categoryId);

	Category getCategoryByUuid(String categoryStr);

	boolean addVideoNewsToCategory(VideoNews videoNews, String uuid);

	List<Category> getCategoriesForVideoNews(Long videoNewsId);

	void deleteVideoNewsesDoubles();
}
