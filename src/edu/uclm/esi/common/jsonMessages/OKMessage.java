package edu.uclm.esi.common.jsonMessages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OKMessage extends JSONMessage {
	@JSONable
	private JSONArray additionalInfo;
	
	public OKMessage() {
		super(false);
		additionalInfo=null;
	}

	public OKMessage(JSONArray additionalInfo) {
		super(false);
		this.additionalInfo=additionalInfo;
	}

	public OKMessage(JSONObject jso) throws JSONException {
		super(false);
		if (jso.has("additionalInfo")) {
			this.additionalInfo=jso.getJSONArray("additionalInfo");
		}
	}
	
	public JSONArray getAdditionalInfo() {
		return additionalInfo;
	}
}
