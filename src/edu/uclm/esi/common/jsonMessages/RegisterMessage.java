package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterMessage extends JSONMessage {
	@JSONable
	private String email;
	@JSONable
	private String pwd1;
	@JSONable
	private String pwd2; 

	public RegisterMessage(String email, String pwd1, String pwd2) {
		super(true);
		this.email=email;
		this.pwd1=pwd1;
		this.pwd2=pwd2;
	}

	public RegisterMessage(JSONObject jso) throws JSONException {
		this(jso.get("email").toString(), jso.get("pwd1").toString(), jso.get("pwd2").toString());
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPwd1() {
		return pwd1;
	}
	
	public String getPwd2() {
		return pwd2;
	}
}
