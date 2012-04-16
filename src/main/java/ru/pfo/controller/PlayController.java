package ru.pfo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.pfo.model.Category;
import ru.pfo.model.VideoNews;

@Controller
public class PlayController extends BaseController {

	private static final Logger LOG = Logger.getLogger(PlayController.class);

	@RequestMapping(value = "/play/{videoTitle}", method = RequestMethod.GET)
	public String video(Model model, @PathVariable("videoTitle") String videoTitle) {
		String path = "";
		try {
			path = URLDecoder.decode(videoTitle, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.error(e, e);
		}
		int i = path.indexOf(' ');
		String videoId = path.substring(0, i);
		VideoNews videoNews = videoNewsService.getValidVideoNews(Long.parseLong(videoId));
		videoNewsService.incrementViews(videoNews.getId());
		List<Category> categories = categoryService.getCategoriesForVideoNews(videoNews.getId());
		model.addAttribute("videoNews", videoNews);
		model.addAttribute("categories", categories);
		return "play";
	}
}
