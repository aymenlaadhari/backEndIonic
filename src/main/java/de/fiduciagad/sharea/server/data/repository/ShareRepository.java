package de.fiduciagad.sharea.server.data.repository;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import de.fiduciagad.sharea.server.data.repository.dto.Category;
import de.fiduciagad.sharea.server.data.repository.dto.Share;

@Component
public class ShareRepository extends AbstractRepository<Share> {

	@Autowired
	public ShareRepository(CouchDbConnector db) {
		super(Share.class, db);
	}

	@View(name = "by_categoryId", map = "function(doc) { if(doc.docType === 'Share' && doc.categoryId) { emit(doc.categoryId, doc._id) }}")
	public List<Share> findByCategory(Category category) {
		return queryView("by_categoryId", category.getId());
	}

	@View(name = "by_startLocation_by_date", map = "classpath:/de/fiduciagad/sharea/server/data/repository/functions/by_location_by_date.js")
	public List<Share> findByStartLocation(String startLocation, int limit) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(startLocation), "Location cannot be empty.");

		if (limit < 1) {
			limit = 10;
		}

		// From now till the future!
		Date now = new Date();
		Date nextYear = DateUtils.addYears(now, 1);
		ComplexKey startKey = ComplexKey.of(startLocation, now);
		ComplexKey endKey = ComplexKey.of(startLocation, ComplexKey.emptyObject());

		ViewQuery query = new ViewQuery().designDocId(stdDesignDocumentId).viewName("by_startLocation_by_date")
				.includeDocs(true).startKey(startKey).endKey(endKey).limit(limit);

		return db.queryView(query, Share.class);
	}

	@View(name = "by_title", map = "function(doc) { if(doc.docType === 'Share' && doc.title) { emit(doc.title, doc._id) }}")
	public List<Share> findByTitle(String title) {
		return queryView("by_title", title);
	}

};