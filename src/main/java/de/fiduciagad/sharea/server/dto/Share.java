package de.fiduciagad.sharea.server.dto;

public class Share {

	private String _id;
	
	public Share(String _id) {
		this.set_id(_id);
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

}
