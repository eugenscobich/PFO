package ru.pfo.controller.model;

import java.util.ArrayList;
import java.util.List;

import ru.pfo.model.VideoNews;

public class SiteModel {

	private List<VideoNews> bestVideoNewses = new ArrayList<VideoNews>();

	public List<VideoNews> getBestVideoNewses() {
		return bestVideoNewses;
	}

	public void setBestVideoNewses(List<VideoNews> bestVideoNewses) {
		this.bestVideoNewses = bestVideoNewses;
	}
}
