package de.fiduciagad.sharea.server.persistence.dao;

import java.util.List;

import de.fiduciagad.sharea.server.dto.Share;
import de.fiduciagad.sharea.server.persistence.generic.Dao;

public interface ShareDaoIf extends Dao<Share>{

	
	public List<Share> readSharesByPlaceFrom(String location, Integer limit);
	
	public Share getShareByID(String _id);

}
