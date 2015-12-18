package de.fiduciagad.sharea.server.data.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.Page;
import org.ektorp.PageRequest;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fiduciagad.sharea.server.data.repository.dto.Share;
import de.fiduciagad.sharea.server.rest.dto.FindShares;

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

	@View(name = "by_startLocation_by_date", map = "function(doc) { if (doc.docType === 'Share') emit( [startLocation, Date.parse(startDate), Date.parse(endDate)], doc )}")
	public List<Share> findByStartLocation(FindShares findShares) {
		// From now till the future!
		Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);
		Date now = new Date();
		ComplexKey startKey = ComplexKey.of(findShares.getStartLocation(), now, ComplexKey.emptyObject());
		ComplexKey endKey = ComplexKey.of(findShares.getStartLocation(), now, nextYear);

		PageRequest pageRequest = PageRequest.firstPage(findShares.getLimit());
		ViewQuery query = new ViewQuery().designDocId(stdDesignDocumentId).viewName("by_startLocation_by_date")
				.includeDocs(true).startKey(startKey).endKey(endKey);

		Page<Share> result = db.queryForPage(query, pageRequest, Share.class);
		return result.getRows();
	}

};