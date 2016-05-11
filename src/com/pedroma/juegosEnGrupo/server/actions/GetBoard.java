package com.pedroma.juegosEnGrupo.server.actions;

import org.json.JSONException;

import com.maco.tresenraya.jsonMessages.TresEnRayaBoardMessage;
import com.opensymphony.xwork2.ActionContext;
import com.pedroma.juegosEnGrupo.server.dominio.Game;
import com.pedroma.juegosEnGrupo.server.dominio.Match;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.actions.JSONAction;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;

@SuppressWarnings("serial")
public class GetBoard extends JSONAction {
	private int idUser;
	private int idGame;
	private int idMatch;
	private Match match;
	
	@Override
	public String postExecute() {
		try {
			Manager manager=Manager.get();
			User user=manager.findUserById(this.idUser);
			if (user==null)
				throw new Exception("Usuario no autenticado");
			Game g=manager.findGameById(idGame);
			this.match=g.findMatchById(idMatch, idUser);
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			ActionContext.getContext().getSession().put("exception", e);
			return ERROR;
		}
	}

	public String getResultado() {
		JSONMessage jso;
		if (this.exception!=null)
			jso=new ErrorMessage(this.exception.getMessage());
		else {
			try {
				jso=new TresEnRayaBoardMessage(match.toString());
			} catch (JSONException e) {
				jso=new ErrorMessage(this.exception.getMessage());
			}
		}
		return jso.toJSONObject().toString();
	}

	@Override
	public void setCommand(String cmd) {}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}
}