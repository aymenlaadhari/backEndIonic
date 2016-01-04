package de.fiduciagad.sharea.server.data.access;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.CategoryRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Category;

@Component
public class CategoryManager {

	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryManager() {
	}

	public void create(Category comment) {
		categoryRepository.add(comment);
	}

	public Category get(String id) {
		return categoryRepository.get(id);
	}

	public List<Category> getAll() {
		return categoryRepository.getAll();
	}

}
