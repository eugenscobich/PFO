package ru.pfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.pfo.Constant;
import ru.pfo.dao.ICategoryDAO;
import ru.pfo.model.Category;
import ru.pfo.model.VideoNews;
import ru.pfo.service.ICategoryService;
import ru.pfo.service.ISettingsService;
import ru.pfo.util.PropertiesUtil;

@Service("categoryService")
@Transactional
public class CategoryService implements ICategoryService {

	@Autowired
	private ICategoryDAO categoryDAO;

	@Autowired
	private ISettingsService settingsService;

	public void save(Category category) {
		categoryDAO.save(category);
	}

	public void update(Category category) {
		categoryDAO.update(category);
	}

	public Category getNewCategory() {
		Category category = new Category();
		return category;
	}

	@Override
	@Transactional
	public List<Category> getAllCategories() {
		return categoryDAO.getAll();
	}

	@Override
	public void persistCategory(List<Category> categories) {
		for (Category category : categories) {
			if (isExist(category)) {
				Category cat = categoryDAO.getCategoryByUuid(category.getUuid());
				cat.getVideoNewses().addAll(category.getVideoNewses());
				// cat.setVideoNewses();
				categoryDAO.update(cat);
			} else {
				categoryDAO.save(category);
			}

		}
	}

	boolean isExist(Category category) {
		return categoryDAO.isExist(category);
	}

	@Override
	public Category getCategoryWithVideoNewses(Long categoryId, Integer page) {
		int itemsOnPage = settingsService.getSettings().getItemsOnPage();
		int start = (page - 1) * itemsOnPage;
		return categoryDAO.getCategoryWithVideoNewses(categoryId, start, itemsOnPage);
	}

	@Override
	public int getCategoryTotalPage(Long categoryId) {
		int itemsOnPage = PropertiesUtil.getPropertyInteger(Constant.PROPS_KEY_ITEM_ON_PAGES);
		int totalPage = (int) Math.ceil(categoryDAO.getCountOfCategoryWithVideosNewses(categoryId) * 1.0 / itemsOnPage);
		return totalPage;
	}

	@Override
	public Category getCategoryByUuid(String uuid) {
		return categoryDAO.getCategoryByUuid(uuid);
	}

	@Override
	public boolean addVideoNewsToCategory(VideoNews videoNews, String uuid) {
		Category category = categoryDAO.getCategoryByUuid(uuid);
		if (category != null) {
			category.getVideoNewses().add(videoNews);
			categoryDAO.update(category);
			return true;
		}
		return false;
	}

	@Override
	public List<Category> getCategoriesForVideoNews(Long videoNewsId) {
		List<Category> categories = categoryDAO.getCategoriesForVideoNews(videoNewsId);
		return categories;
	}

	@Override
	public void deleteVideoNewsesDoubles() {
		//List<Category> categories = getAllCategories();
		/*for (Category category : categories) {

		}*/
	}
}
