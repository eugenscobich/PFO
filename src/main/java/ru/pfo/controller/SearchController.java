package ru.pfo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ru.pfo.model.VideoNews;

@Controller
public class SearchController extends BaseController {

	private static final Logger LOG = Logger.getLogger(CategoryController.class);

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model, @RequestParam("query") String query,
			@RequestParam(value = "page", required = false) Integer page, HttpServletRequest request,
			HttpServletResponse response) {
		if (page == null) {
			page = 1;
		}

		int totalPages = 0;
		String message = null;

		List<VideoNews> videoNewses = new ArrayList<VideoNews>();
		if (query != null && !query.trim().isEmpty()) {
			try {
				query = URLDecoder.decode(query, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				LOG.warn(e);
			}
			videoNewses = videoNewsService.getVideoNewsesByQuerySearch(query, page);
			if (videoNewses != null && !videoNewses.isEmpty()) {
				totalPages = videoNewsService.getVideoNewsesTotalPageByQuerySearch(query);
			} else {
				LOG.debug("Not found the search result for query: " + query);
				message = getMessage("not-found-the-search-results", null, request.getLocale());
			}
		} else {
			videoNewses = videoNewsService.getVideoNewses(page);
			totalPages = videoNewsService.getTotalPage();
		}
		model.addAttribute("query", query);
		model.addAttribute("message", message);
		model.addAttribute("videoNewses", videoNewses);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", totalPages);

		return "search-result";

	}

}
