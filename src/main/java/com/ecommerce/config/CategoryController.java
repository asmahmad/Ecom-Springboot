package com.ecommerce.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;

//This class serves as a controller for handling category-related requests
@Controller
public class CategoryController {

	// Autowire the CategoryService to use its functionality
	@Autowired
	private CategoryService categoryService;

	// This method is mapped to the GET request with the URL "/categories"
	@GetMapping("/categories")
	public String categories(Model model) {
		model.addAttribute("title", "Manage Category");

		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("size", categories.size());
		model.addAttribute("categoryNew", new Category());
		return "categories";
	}

	// This method is mapped to the POST request with the URL "/add-category"
	@PostMapping("/add-category")
	public String save(@ModelAttribute("categoryNew") Category category, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			categoryService.save(category);
			model.addAttribute("categoryNew", category);
			redirectAttributes.addFlashAttribute("success", "Add successfully!");
		} catch (DataIntegrityViolationException e1) {
			e1.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
		} catch (Exception e2) {
			e2.printStackTrace();
			model.addAttribute("categoryNew", category);
			redirectAttributes.addFlashAttribute("error", "Error server");
		}
		return "redirect:/categories";
	}

}
