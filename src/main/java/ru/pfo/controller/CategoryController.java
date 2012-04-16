package ru.pfo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.pfo.model.Category;

@Controller
public class CategoryController extends BaseController {

	// private static final Logger LOG =
	// Logger.getLogger(CategoryController.class);

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public String video(Model model) {

		List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("categories", categories);

		return "categories";
	}
}
