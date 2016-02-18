package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginMessage extends JSONMessage {
	@JSONable
	private String email;
	@JSONable
	private String pwd;
	@JSONable
	private String userType;

	public LoginMessage(String email, String pwd, String userType) {
		super(true);
		this.email=email;
		this.pwd=pwd;
		this.userType=userType;
	}

	public LoginMessage(JSONObject jso) throws JSONException {
		this(jso.get("email").toString(), jso.get("pwd").toString(), jso.get("userType").toString());
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public String getUserType() {
		return userType;
	}
}
