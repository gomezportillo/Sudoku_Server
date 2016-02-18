package edu.uclm.esi.common.server.actions;

import java.util.Random;

import javax.mail.MessagingException;

import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;

@SuppressWarnings("serial")
public class RecoverPwd extends ActionSupport {
	private String email;
	private Exception exception;
	
	public String execute() {
		try {
			EMailSenderService server=new EMailSenderService();
			long codigo=new Random().nextLong();
			server.enviarPorGmail(this.email, codigo);
			// TODO: guardar c??digo en el servidor.
			return SUCCESS;
		} catch (MessagingException e) {
			this.exception=e;
			return ERROR;
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public JSONObject getResultado() {
		JSONMessage jso;
		if (this.exception!=null)
			jso=new ErrorMessage(this.exception.getMessage());
		else
			jso=new OKMessage();
		return jso.toJSONObject();
	}

}
