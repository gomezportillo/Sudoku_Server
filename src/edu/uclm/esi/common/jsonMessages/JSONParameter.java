package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParameter extends JSONMessage {
	@JSONable
	private String name;
	@JSONable
	private String value;

	public JSONParameter(String name, String value) {
		super(false);
		this.name=name;
		this.value=value;
	}

	public JSONParameter(JSONObject jso) throws JSONException {
		this(jso.get("name").toString(), jso.get("value").toString());
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}
