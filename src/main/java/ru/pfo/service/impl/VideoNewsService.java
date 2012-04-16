package ru.pfo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.pfo.Constant;
import ru.pfo.dao.IVideoNewsDAO;
import ru.pfo.model.Category;
import ru.pfo.model.VideoNews;
import ru.pfo.parsers.IParser;
import ru.pfo.service.ICategoryService;
import ru.pfo.service.ISettingsService;
import ru.pfo.service.IVideoNewsService;
import ru.pfo.util.PropertiesUtil;

@Service("videoNewsService")
@Transactional
public class VideoNewsService implements IVideoNewsService {

	private static final Logger LOG = Logger.getLogger(VideoNewsService.class);

	@Autowired
	private IVideoNewsDAO videoNewsDAO;

	@Autowired
	private ISettingsService settingsService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IParser parser;

	public void save(VideoNews videoNews) {
		videoNewsDAO.save(videoNews);
	}

	public VideoNews getNewLayer() {
		VideoNews videoNews = new VideoNews();
		return videoNews;
	}

	@Override
	public void persistVideoNewses(List<VideoNews> videoNewses) {
		for (VideoNews videoNews : videoNewses) {
			videoNewsDAO.save(videoNews);
		}

	}

	@Override
	public List<VideoNews> getVideoNewses(int page) {
		int itemsOnPage = settingsService.getSettings().getItemsOnPage();
		int start = (page - 1) * itemsOnPage;
		return videoNewsDAO.getVideoNewses(start, itemsOnPage);
	}

	@Override
	public int getTotalPage() {
		int itemsOnPage = settingsService.getSettings().getItemsOnPage();
		int totalPage = (int) Math.ceil(videoNewsDAO.getCountOfVideosNewses() * 1.0 / itemsOnPage);
		return totalPage;
	}

	@Override
	public List<VideoNews> getVideoNewsesByCategory(Long categoryId, Integer page) {
		int itemsOnPage = settingsService.getSettings().getItemsOnPage();
		int start = (page - 1) * itemsOnPage;
		return videoNewsDAO.getVideoNewsesByCategory(categoryId, start, itemsOnPage);
	}

	@Override
	public int getVideoNewsesTotalPageByCategory(Long categoryId) {
		int itemsOnPage = settingsService.getSettings().getItemsOnPage();
		int totalPage = (int) Math.ceil(videoNewsDAO.getCountOfVideosNewsesByCategory(categoryId) * 1.0 / itemsOnPage);
		return totalPage;
	}

	@Override
	public VideoNews getValidVideoNews(long videoId) {

		VideoNews videoNews = videoNewsDAO.get(videoId);
		long maxLivingLinks = PropertiesUtil.getPropertyLong(Constant.PROPS_KEY_MAX_LIVING_LINKS); // Second
		long dif = new Date().getTime() - videoNews.getLastUrlUpdate().getTime();
		if (maxLivingLinks * 1000 < dif) {
			String str = parser.getVideoUrl(videoNews);
			if (str != null) {
				videoNews.setVideoUrl(str);
				videoNews.setLastUrlUpdate(new Date());
				videoNewsDAO.update(videoNews);
				LOG.info("Return NEW Video URL for: " + videoNews.getTitle());
			} else {
				LOG.error("Return OLD Video URL for: " + videoNews.getTitle());
			}
		} else {
			LOG.debug("Return CURRENT Video URL for: " + videoNews.getTitle());
		}
		return videoNews;
	}

	@Override
	public boolean isExist(String videoKey) {
		return videoNewsDAO.isExist(videoKey);
	}

	@Override
	public List<VideoNews> getBestVideoNewses() {
		int minProcent = PropertiesUtil.getPropertyInteger(Constant.PROPS_KEY_THRESHOLD_FOR_BEST_VIDEO_NEWSES);
		List<VideoNews> videoNewses = videoNewsDAO.getBestVideoNewses(minProcent);
		int nrOfBestVideoNewses = PropertiesUtil.getPropertyInteger(Constant.PROPS_KEY_NUMBER_OF_BEST_VIDEO_NEWSES);
		Collections.shuffle(videoNewses);
		if (videoNewses.size() > nrOfBestVideoNewses) {
			return videoNewses.subList(0, nrOfBestVideoNewses);
		}
		return videoNewses;
	}

	@Override
	public void incrementViews(Long videoNewsId) {
		VideoNews vn = videoNewsDAO.get(videoNewsId);
		vn.setViews(vn.getViews() + 1);
		videoNewsDAO.update(vn);
	}

	@Override
	public void removeVideoNewsesDuplicates() {
		LOG.info("Start remove video newses duplicates");
		List<VideoNews> videoNewses = getAllVideoNewses();
		List<VideoNews> normals = new ArrayList<VideoNews>();
		List<VideoNews> excludes = new ArrayList<VideoNews>();
		for (VideoNews videoNews : videoNewses) {
			boolean contains = false;
			for (VideoNews vn : normals) {
				if (vn.getVideoKey().equals(videoNews.getVideoKey())) {
					contains = true;
				}
			}
			if (contains) {
				LOG.debug("Found duplicate for videoKey: " + videoNews.getVideoKey());
				excludes.add(videoNews);
			} else {
				normals.add(videoNews);
			}
		}

		for (VideoNews videoNews : excludes) {
			for (Category cat : videoNews.getCategories()) {
				cat.getVideoNewses().remove(videoNews);
				categoryService.update(cat);
			}
			videoNewsDAO.remove(videoNews);
		}
		LOG.info("End remove video newses duplicates");
	}

	@Override
	public List<VideoNews> getAllVideoNewses() {
		return videoNewsDAO.getAllVideoNewses();
	}

	@Override
	public List<VideoNews> getVideoNewsesByQuerySearch(String query, Integer page) {
		if (query == null) {
			return null;
		}
		query = removeUnwantedCharacters(query);

		String[] searchArray = query.split(" ");

		int itemsOnPage = settingsService.getSettings().getItemsOnPage();
		int start = (page - 1) * itemsOnPage;

		List<VideoNews> videoNewses = videoNewsDAO.getVideoNewsesByStringArray(searchArray, start, itemsOnPage);

		return videoNewses;
	}

	@Override
	public int getVideoNewsesTotalPageByQuerySearch(String query) {
		if (query == null) {
			return 0;
		}
		query = removeUnwantedCharacters(query);

		String[] searchArray = query.split(" ");

		int itemsOnPage = settingsService.getSettings().getItemsOnPage();
		int totalPage = (int) Math.ceil(videoNewsDAO.getCountOfVideoNewsesByStringArray(searchArray) * 1.0
				/ itemsOnPage);

		return totalPage;
	}

	private String removeUnwantedCharacters(String str) {
		str = str.replace("~", " ");
		str = str.replace("`", " ");
		str = str.replace("!", " ");
		str = str.replace("@", " ");
		str = str.replace("#", " ");
		str = str.replace("$", " ");
		str = str.replace("%", " ");
		str = str.replace("@", " ");
		str = str.replace("^", " ");
		str = str.replace("&", " ");
		str = str.replace("*", " ");
		str = str.replace("(", " ");
		str = str.replace(")", " ");
		str = str.replace("-", " ");
		str = str.replace("_", " ");
		str = str.replace("+", " ");
		str = str.replace("=", " ");
		str = str.replace("{", " ");
		str = str.replace("}", " ");
		str = str.replace("]", " ");
		str = str.replace("[", " ");
		str = str.replace("|", " ");
		str = str.replace("\\", " ");
		str = str.replace(":", " ");
		str = str.replace(";", " ");
		str = str.replace("'", " ");
		str = str.replace("\"", " ");
		str = str.replace("<", " ");
		str = str.replace(",", " ");
		str = str.replace(">", " ");
		str = str.replace(".", " ");
		str = str.replace("?", " ");
		str = str.replace("/", " ");

		str = str.replace("     ", " ");
		str = str.replace("    ", " ");
		str = str.replace("   ", " ");
		return str.replace("  ", " ");
	}

	@Override
	public void update(VideoNews videoNews) {
		videoNewsDAO.update(videoNews);
	}

	@Override
	public List<VideoNews> getHidenVideoNewses() {
		return videoNewsDAO.getHidenVideoNewses();
	}
}
