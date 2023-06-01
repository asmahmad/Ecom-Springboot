package com.ecommerce.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public String categories(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		model.addAttribute("title", "Manage Category");

		List<Category> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("size", categories.size());
		model.addAttribute("categoryNew", new Category());
		return "categories";
	}

	// This method is mapped to the POST request with the URL "/add-category"
	@PostMapping("/add-category")
	public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes attributes) {
		try {
			categoryService.save(category);
			attributes.addFlashAttribute("success", "Added successfully");
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			attributes.addFlashAttribute("failed", "Failed to add because duplicate name");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("failed", "Error server");
		}
		return "redirect:/categories";

	}

	@RequestMapping(value = "/findById", method = { RequestMethod.PUT, RequestMethod.GET })
	@ResponseBody
	public Optional<Category> findById(Long id) {
		return categoryService.findById(id);
	}

	@GetMapping("/update-category")
	public String update(Category category, RedirectAttributes attributes) {
		try {
			categoryService.update(category);
			attributes.addFlashAttribute("success", "Updated successfully");
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("failed", "Error server");
		}
		return "redirect:/categories";
	}

	@RequestMapping(value = "/delete-category", method = { RequestMethod.PUT, RequestMethod.GET })
	public String delete(Long id, RedirectAttributes attributes) {
		try {
			categoryService.deleteById(id);
			attributes.addFlashAttribute("success", "Deleted successfully");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("failed", "Failed to deleted");
		}
		return "redirect:/categories";
	}

	@RequestMapping(value = "/enable-category", method = { RequestMethod.PUT, RequestMethod.GET })
	public String enable(Long id, RedirectAttributes attributes) {
		try {
			categoryService.enabledById(id);
			attributes.addFlashAttribute("success", "Enabled successfully");
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addFlashAttribute("failed", "Failed to enabled");
		}
		return "redirect:/categories";
	}
}
