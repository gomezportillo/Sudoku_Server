package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

public class LogoutMessageAnnouncement extends JSONMessage {
	private String email;

	public LogoutMessageAnnouncement(String email) {
		super(false);
		this.email=email;
	}

	public LogoutMessageAnnouncement(JSONObject jso) throws JSONException {
		this(jso.get("email").toString());
	}

	public String getEmail() {
		return email;
	}
}
