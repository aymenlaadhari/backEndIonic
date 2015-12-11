package de.fiduciagad.sharea.server.persistence.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.Key.ComplexKey;
import com.cloudant.client.api.views.UnpaginatedRequestBuilder;
import com.cloudant.client.api.views.ViewRequest;
import com.cloudant.client.api.views.ViewResponse;

import de.fiduciagad.sharea.server.dto.Share;
import de.fiduciagad.sharea.server.persistence.generic.DaoImpl;

@Component
public class ShareDaoImpl extends DaoImpl<Share> implements ShareDaoIf {

	private static final Log log = LogFactory.getLog(ShareDaoImpl.class);
	private final Integer DEFAULT_LIMIT = 10;

	public ShareDaoImpl() {
		super(Share.class);
	}

	@Override
	public List<Share> readSharesByPlaceFrom(String location, Integer limit) {

		// Setzen der aktuellen Zeit
		LocalDateTime dateTimeNow = LocalDateTime.now();

		LocalDateTime dateTimeInOneYear = dateTimeNow.plusYears(1);

		//@formatter:off
		//generating the view request
		UnpaginatedRequestBuilder<ComplexKey,Share> vrb = getViewRequestBuilder("share", "possibleShares").newRequest(Key.Type.COMPLEX,Share.class);
		
		//Adding start and endKey
		vrb.startKey(Key.complex(location).add(dateTimeNow.format(DateTimeFormatter.ISO_LOCAL_DATE)))
		   .endKey(Key.complex(location).add(dateTimeInOneYear.format(DateTimeFormatter.ISO_LOCAL_DATE)));
		//When no limit, then default
		if(limit!=null){
			vrb.limit(limit);
		}
		else{
			vrb.limit(DEFAULT_LIMIT);
		}
		//Build the request
		ViewRequest<ComplexKey, Share> request = vrb.build();
		//@formatter:on
		
		//get Response
		ViewResponse<ComplexKey, Share> vr = null;
		try {
			vr = request.getResponse();
		} catch (Exception e) {
			log.error("Returned error, when requesting View", e);
		}

		return vr.getValues();
	}
	
}
