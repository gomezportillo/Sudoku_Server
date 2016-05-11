package com.pedroma.juegosEnGrupo.server.actions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.server.actions.JSONAction;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;

@SuppressWarnings("serial")
public class JoinGame extends JSONAction {
	private int idUser;
	private int idGame;
	private int idMatch;
	
	@Override
	public String postExecute() {
		try {
			Manager manager=Manager.get();
			User user=manager.findUserById(this.idUser);
			if (user==null)
				throw new Exception("Usuario no autenticado");
			this.idMatch=manager.add(idGame, idUser);
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			ActionContext.getContext().getSession().put("exception", e);
			return ERROR;
		}
	}

	@Override
	public String getResultado() {
		JSONMessage jso;
		if (this.exception!=null)
			jso=new ErrorMessage(this.exception.getMessage());
		else {
			JSONArray jsa=new JSONArray();
			try {
				jsa.put(0, idMatch);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jso=new OKMessage(jsa);
		}
		return jso.toJSONObject().toString();
	}

	@Override
	public void setCommand(String cmd) {
		JSONObject jso;
		try {
			jso = new JSONObject(cmd);
			this.idUser=jso.getInt("idUser");
			this.idGame=jso.getInt("idGame");
		} catch (JSONException e) {
			this.exception=e;
		}
	}
	
	public void setIdUser(int idUser) {
		this.idUser=idUser;
	}
	
	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
}