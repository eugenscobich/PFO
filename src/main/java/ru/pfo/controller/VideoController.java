package ru.pfo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.pfo.model.Category;
import ru.pfo.model.VideoNews;

@Controller
public class VideoController extends BaseController {

	// private static final Logger LOG =
	// Logger.getLogger(VideoController.class);

	@RequestMapping(value = "/video", method = RequestMethod.GET)
	public String video(Model model, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "category", required = false) Long categoryId) {
		if (page == null) {
			page = 1;
		}
		int totalPages = 0;
		List<VideoNews> videoNewses = new ArrayList<VideoNews>();
		if (categoryId != null) {
			videoNewses = videoNewsService.getVideoNewsesByCategory(categoryId, page);
			totalPages = videoNewsService.getVideoNewsesTotalPageByCategory(categoryId);

		} else {
			videoNewses = videoNewsService.getVideoNewses(page);
			totalPages = videoNewsService.getTotalPage();
		}

		List<Category> categories = categoryService.getAllCategories();

		model.addAttribute("videoNewses", videoNewses);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("categories", categories);

		return "video";
	}
}
