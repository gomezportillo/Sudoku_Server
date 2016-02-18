package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorMessage extends JSONMessage {
	@JSONable
	private String text;

	public ErrorMessage(String texto) {
		super(false);
		this.text=texto;
	}

	public ErrorMessage(JSONObject jso) throws JSONException {
		this(jso.get("text").toString());
	}
	
	public String getText() {
		return text;
	}
}
