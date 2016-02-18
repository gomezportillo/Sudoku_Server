package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginWithGoogleMessage extends JSONMessage {
	@JSONable
	private String email;

	public LoginWithGoogleMessage(String email) {
		super(true);
		this.email=email;
	}

	public LoginWithGoogleMessage(JSONObject jso) throws JSONException {
		this(jso.get("email").toString());
	}
	
	public String getEmail() {
		return email;
	}
}
