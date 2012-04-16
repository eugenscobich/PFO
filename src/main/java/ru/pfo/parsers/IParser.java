package ru.pfo.parsers;

import ru.pfo.model.VideoNews;

public interface IParser extends Runnable {
	String getVideoUrl(VideoNews videoNews);
}
