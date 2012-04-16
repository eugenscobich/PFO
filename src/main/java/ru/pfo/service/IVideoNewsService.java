package ru.pfo.service;

import java.util.List;

import ru.pfo.model.VideoNews;

public interface IVideoNewsService {

	void save(VideoNews videoNews);

	VideoNews getNewLayer();

	void persistVideoNewses(List<VideoNews> videoNewses);

	List<VideoNews> getVideoNewses(int page);

	int getTotalPage();

	List<VideoNews> getVideoNewsesByCategory(Long categoryId, Integer page);

	int getVideoNewsesTotalPageByCategory(Long categoryId);

	VideoNews getValidVideoNews(long videoId);

	boolean isExist(String videoKey);

	List<VideoNews> getBestVideoNewses();

	void incrementViews(Long id);

	void removeVideoNewsesDuplicates();

	List<VideoNews> getAllVideoNewses();

	List<VideoNews> getVideoNewsesByQuerySearch(String query, Integer page);

	int getVideoNewsesTotalPageByQuerySearch(String query);

	void update(VideoNews videoNews);

	List<VideoNews> getHidenVideoNewses();
}
