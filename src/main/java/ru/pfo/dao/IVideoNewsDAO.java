package ru.pfo.dao;

import java.io.Serializable;
import java.util.List;

import ru.pfo.model.VideoNews;

public interface IVideoNewsDAO {
	void save(Object entity);

	VideoNews get(Serializable id);

	VideoNews load(Serializable id);

	@Deprecated
	boolean isExist(VideoNews videoNews);

	List<VideoNews> getVideoNewses(int start, int nrOfItems);

	long getCountOfVideosNewses();

	List<VideoNews> getVideoNewsesByCategory(Long categoryId, int start, int itemsOnPage);

	long getCountOfVideosNewsesByCategory(Long categoryId);

	VideoNews getVideoNewsByTitle_ru(String title);

	void update(Object entity);

	boolean isExist(String videoKey);

	List<VideoNews> getBestVideoNewses(int minProcent);

	List<VideoNews> getAllVideoNewses();

	void remove(VideoNews videoNews);

	List<VideoNews> getVideoNewsesByStringArray(String[] searchArray, int start, int itemsOnPage);

	long getCountOfVideoNewsesByStringArray(String[] searchArray);

	List<VideoNews> getHidenVideoNewses();
}
