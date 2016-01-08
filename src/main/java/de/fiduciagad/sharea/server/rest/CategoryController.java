package de.fiduciagad.sharea.server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.sharea.server.data.access.CategoryManager;
import de.fiduciagad.sharea.server.data.repository.dto.Category;

@RestController
public class CategoryController {

	// TODO introduce handler
	@Autowired
	private CategoryManager categoryManager;

	@RequestMapping(value = "/api/v1/category", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Category> getCategories() {
		return categoryManager.getAll();
	}

	@RequestMapping(value = "/api/v1/category/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Category getCategory(@PathVariable String id) {
		return categoryManager.get(id);
	}

}
