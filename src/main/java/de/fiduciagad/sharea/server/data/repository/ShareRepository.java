package de.fiduciagad.sharea.server.data.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import de.fiduciagad.sharea.server.data.repository.dto.Share;

@Component
@View(name = "all", map = "function(doc) { if (doc.docType === 'Share') emit( null, doc._id )}")
public class ShareRepository extends CouchDbRepositorySupport<Share> {

	protected ShareRepository(Class<Share> type, CouchDbConnector db) {
		super(Share.class, db);
		initStandardDesignDocument();
	}

	@Autowired
	public ShareRepository(CouchDbConnector db) {
		this(Share.class, db);
	}

	@View(name = "by_startLocation_by_date", map = "classpath:/de/fiduciagad/sharea/server/data/repository/functions/by_location_by_date.js")
	public List<Share> findByStartLocation(String startLocation, int limit) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(startLocation), "Location cannot be empty.");

		// From now till the future!
		Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);
		Date now = new Date();
		ComplexKey startKey = ComplexKey.of(startLocation, now);
		ComplexKey endKey = ComplexKey.of(startLocation, nextYear);

		ViewQuery query = new ViewQuery().designDocId(stdDesignDocumentId).viewName("by_startLocation_by_date")
				.includeDocs(true).startKey(startKey).endKey(endKey).limit(limit);

		return db.queryView(query, Share.class);
	}

};