package ru.pfo.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.pfo.model.VideoNews;

@Controller
public class HomeController extends BaseController {

	private static final Logger LOG = Logger.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		LOG.debug("Home Controler");
		LOG.debug(request.getServerName());

		List<VideoNews> videoNewses = videoNewsService.getVideoNewses(1);
		int totalPages = videoNewsService.getTotalPage();

		OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
		String enc = writer.getEncoding();

		model.addAttribute("enc", enc);
		model.addAttribute("videoNewses", videoNewses);
		model.addAttribute("page", 1);
		model.addAttribute("totalPages", totalPages);

		return "home";
	}
}
