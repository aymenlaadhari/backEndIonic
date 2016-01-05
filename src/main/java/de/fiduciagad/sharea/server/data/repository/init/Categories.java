package de.fiduciagad.sharea.server.data.repository.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import de.fiduciagad.sharea.server.data.repository.CategoryRepository;
import de.fiduciagad.sharea.server.data.repository.dto.Category;
import de.fiduciagad.sharea.server.data.repository.dto.CategoryConfig;

@Component
public class Categories {

	@Autowired
	private CategoryRepository categoryRepository;

	private Log logger = LogFactory.getLog(Categories.class);

	public Categories() {
	}

	private List<Category> createCategories() {
		List<Category> list = Lists.newArrayList();
		list.add(createOfficeCategory());
		list.add(createTravelCategory());
		list.add(createMealCategory());
		return list;
	}

	private Category createMealCategory() {
		CategoryConfig config = new CategoryConfig(true, true, true, false, true, false, false);
		Category category = new Category("eat-together", "Gemeinsam Essen",
				"http://www.theholyrood.co.uk/files/2012/10/Content_FoodDrink.jpg",
				Sets.newHashSet("ion-md-restaurant", "ion-ios-restaurant", "ion-md-cafe", "ion-md-ice-cream",
						"ion-md-pizza", "ion-ios-beer", "ion-md-wine", "ion-ios-wine", "ion-md-beer"),
				config);
		return category;
	}

	private Category createOfficeCategory() {
		CategoryConfig config = new CategoryConfig(true, false, true, true, true, false, true);
		Category category = new Category("share-office", "Büro teilen", "../img/sharedota-buero.jpg",
				Sets.newHashSet("ion-ios-printer", "ion-ios-monitor"), config);
		return category;
	}

	private Category createTravelCategory() {
		CategoryConfig config = new CategoryConfig(true, true, true, true, true, true, true);
		Category category = new Category("travel-together", "Gemeinsam Reisen", "../img/taxi.jpg",
				Sets.newHashSet("ion-md-car", "ion-md-train"), config);
		return category;
	}

	public Map<String, String> init() {
		HashMap<String, String> categoryIdMapping = Maps.newHashMap();
		for (Category category : createCategories()) {
			List<Category> dbResult = categoryRepository.findByInternalName(category.getInternalName());
			if (dbResult.isEmpty()) {
				logger.info("Adding example Category: " + category.getInternalName());
				categoryRepository.add(category);
				categoryIdMapping.put(category.getInternalName(), category.getId());
			} else {
				logger.info("Did not add example Category: " + category.getInternalName());
				categoryIdMapping.put(dbResult.get(0).getInternalName(), dbResult.get(0).getId());
			}
		}
		return categoryIdMapping;
	}

}
