package com.maco.juegosEnGrupo.server.actions;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;

import com.maco.juegosEnGrupo.server.dominio.Game;
import com.maco.tresenraya.jsonMessages.GameListMessage;
import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.actions.JSONAction;
import edu.uclm.esi.common.server.domain.Manager;

@SuppressWarnings("serial")
public class GameList extends JSONAction {
	private Vector<Game> gameList;
	
	@Override
	public String postExecute() {
		try {
			Manager manager=Manager.get();
			this.gameList=manager.loadAllGames();
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			ActionContext.getContext().getSession().put("exception", e);
			return ERROR;
		}
	}

	@Override
	public String getResultado() {
		JSONMessage result;
		if (this.exception!=null) {
			result=new ErrorMessage(this.exception.getMessage());
		} else {
			JSONArray games=new JSONArray();			
			for (Game g : this.gameList) {
				try {
					games.put(g.toJSON());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			result=new GameListMessage(games);
		}
		String s=result.toString();
		return s;
	}

	@Override
	public void setCommand(String cmd) {
		// TODO Auto-generated method stub
	}
}