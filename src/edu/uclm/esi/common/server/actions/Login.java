package edu.uclm.esi.common.server.actions;

import java.sql.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;

@SuppressWarnings("serial")
public class Login extends JSONAction {
	private String email;
	private String pwd;
	private User user;	private String userType;
	
	protected String postExecute() {
		try {
			Connection bd=User.identify(email, pwd);
			this.user=new User(bd, email, userType);
			Manager manager=Manager.get();
			manager.add(user, ip);
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			ActionContext.getContext().getSession().put("exception", e);
			return ERROR;
		}
	}

	@Override
	public void setCommand(String cmd){
		JSONObject jso;
		try {
			jso = new JSONObject(cmd);
			this.email=jso.get("email").toString();
			this.pwd=jso.get("pwd").toString();
			this.userType=jso.get("userType").toString();
		} catch (JSONException e) {
			this.exception=e;
		}
	}

	@Override
	public String getResultado() {
		JSONMessage jsm;
		if (this.exception!=null)
			jsm=new ErrorMessage(this.exception.getMessage());
		else {
			JSONArray jsa=new JSONArray();
			try {
				jsa.put(0, this.user.getId());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsm=new OKMessage(jsa);
		}
		JSONObject result=jsm.toJSONObject();
		return result.toString();
	}

}
