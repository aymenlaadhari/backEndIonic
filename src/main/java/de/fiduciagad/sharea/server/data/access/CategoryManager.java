package de.fiduciagad.sharea.server.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.CategoryRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Category;

@Component
public class CategoryManager extends AbstractManager<Category, CategoryRepository> {

	@Autowired
	public CategoryManager(CategoryRepository categoryRepository) {
		super(categoryRepository);
	}

}
