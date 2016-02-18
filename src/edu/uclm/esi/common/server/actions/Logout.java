package edu.uclm.esi.common.server.actions;

import java.sql.SQLException;

import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.LogoutMessageAnnouncement;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;


@SuppressWarnings("serial")
public class Logout extends JSONAction {
	private SQLException exception;
	private int id;
	
	@Override
	public String postExecute() {
		try {
			Manager manager=Manager.get();
			User user=manager.findUserById(id);
			user.getDB().close();
			LogoutMessageAnnouncement lm=new LogoutMessageAnnouncement(user.getEmail());
			//Client.broadcast(lm.toJSON());
			return SUCCESS;
		} catch (SQLException e) {
			ActionContext.getContext().getSession().put("exception", e);
			this.exception=e;
			return ERROR;
		}
	}

	public String getResultado() {
		JSONMessage jso;
		if (this.exception!=null)
			jso=new ErrorMessage(this.exception.getMessage());
		else
			jso=new OKMessage();
		return jso.toJSONObject().toString();
	}

	@Override
	public void setCommand(String cmd) {
		// TODO Auto-generated method stub
		
	}
	
	public void setId(int id) {
		this.id=id;
	}
}
