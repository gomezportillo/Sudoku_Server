package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginMessageAnnouncement extends JSONMessage {
	@JSONable
	private String email;

	public LoginMessageAnnouncement(String email) {
		super(false);
		this.email=email;
	}

	public LoginMessageAnnouncement(JSONObject jso) throws JSONException {
		this(jso.get("email").toString());
	}
	
	public String getEmail() {
		return email;
	}
}
