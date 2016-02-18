package edu.uclm.esi.common.server.actions;

import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.utils.ValidatorUtil;

@SuppressWarnings("serial")
public class Register extends JSONAction {
	private String email;
	private String pwd1, pwd2;
	
	public String postExecute() {
		try {
			User.insert(email, pwd1);
			return SUCCESS;
		} catch (Exception e) {
			ActionContext.getContext().getSession().put("exception", e);
			this.exception=e;
			return ERROR;
		}
	}
	
	@Override
	public void validate() {
		if (this.pwd1==null || this.pwd2==null || this.pwd1.length()==0 || this.pwd2.length()==0) {
			super.addFieldError("pwd1", "Las passwords deben coincidir");
		}
		if (!this.pwd1.equals(this.pwd2)) {
			super.addFieldError("pwd2", "Las passwords deben coincidir");
		}
		if (this.email==null || this.email.length()==0) {
			super.addFieldError("email", "El email no puede estar vac??o");
		}
		if (this.email!=null && this.email.length()>45) {
			super.addFieldError("email", "El email no puede tener m??s de 45 caracteres");
		}
		if (!ValidatorUtil.validateEmail(email))
			super.addFieldError("email", "La direcci??n de email es inv??lida");
	}

	@Override
	public void setCommand(String cmd) {
		try {
			JSONObject jso=new JSONObject(cmd);
			this.email=jso.get("email").toString();
			this.pwd1=jso.get("pwd1").toString();
			this.pwd2=jso.get("pwd2").toString();
		} catch (JSONException e) {
			this.exception=e;
		}	
	}

	@Override
	public String getResultado() {
		JSONMessage jso;
		if (this.exception!=null)
			jso=new ErrorMessage(this.exception.getMessage());
		else
			jso=new OKMessage();
		return jso.toJSONObject().toString();
	}
}
