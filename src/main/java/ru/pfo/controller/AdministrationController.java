package ru.pfo.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.pfo.controller.validator.AdministationFormValidator;
import ru.pfo.model.Settings;
import ru.pfo.model.VideoNews;
import ru.pfo.parsers.IParser;
import ru.pfo.service.ICategoryService;
import ru.pfo.service.ISettingsService;
import ru.pfo.service.IVideoNewsService;

@Controller
public class AdministrationController {

	private static final Logger LOG = Logger.getLogger(AdministrationController.class);

	@Autowired
	private ISettingsService settingsService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IVideoNewsService videoNewsService;

	@Autowired
	private IParser pornhubParser;

	@ModelAttribute("settings")
	public Settings newSettings() {
		return settingsService.getSettings();
	}

	@ModelAttribute("videoNews")
	public VideoNews getHidenVideoNews(Model model) {
		List<VideoNews> videoNewses = videoNewsService.getHidenVideoNewses();
		model.addAttribute("nr", videoNewses.size());
		if (videoNewses.isEmpty()) {
			return null;
		}
		return videoNewses.get(0);
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		if (binder.getTarget() instanceof Settings) {
			binder.setValidator(new AdministationFormValidator());
		}
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(@ModelAttribute("settings") Settings settings) {
		return "admin";
	}

	@RequestMapping(value = "/admin/saveSettings", method = RequestMethod.POST)
	public String saveSettings(@Valid Settings settings, BindingResult result) {
		if (result.hasErrors()) {
			LOG.info("Submint has have errors");
			return "admin";
		} else {
			settingsService.save(settings);
			LOG.info("Home");
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/admin/runParser", method = RequestMethod.POST)
	public String runParser() {
		new Thread(pornhubParser).start();
		return "redirect:/";
	}

	@RequestMapping(value = "/admin/removeDuplicates", method = RequestMethod.POST)
	public String removeDuplicates() {
		videoNewsService.removeVideoNewsesDuplicates();
		return "redirect:/";
	}

	@RequestMapping(value = "/admin/viewVideoNews", method = RequestMethod.GET)
	public String viewVideoNews(@ModelAttribute("videoNews") VideoNews videoNews) {
		return "admin-edit-videoNews";
	}

	@RequestMapping(value = "/admin/editVideoNews", method = RequestMethod.POST)
	public String editVideoNews(@ModelAttribute("videoNews") VideoNews videoNews) {
		videoNews.setHiden(false);
		videoNewsService.update(videoNews);
		return "redirect:/admin/viewVideoNews";
	}
}
