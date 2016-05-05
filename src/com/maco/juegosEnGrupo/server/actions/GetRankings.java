package com.maco.juegosEnGrupo.server.actions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.sudokus.dominio.SudokuRankingMessage;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.server.actions.JSONAction;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;

@SuppressWarnings("serial")
public class GetRankings extends JSONAction {
	private int idGame;
	private String rankings;
	
	@Override
	public String postExecute() {
		try {
			//Manager manager=Manager.get();
			//User user=manager.findUserById(this.idUser);
			this.rankings = "Que pasa primo, es que quieres ver los rankings?";
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			ActionContext.getContext().getSession().put("exception", e);
			return ERROR;
		}
	}

	@Override
	public String getResultado() {
		JSONMessage srm;
		if (this.exception!=null)
			srm=new ErrorMessage(this.exception.getMessage());
		else {
			srm = new SudokuRankingMessage(this.rankings);
			/**try {
				jsa.put(0, idMatch);
			} catch (JSONException e) {
				e.printStackTrace();
			}*/
		}
		return srm.toJSONObject().toString();
	}

	@Override
	public void setCommand(String cmd) {
		JSONObject jso;
		try {
			jso = new JSONObject(cmd);
			this.idGame=jso.getInt("idGame");
		} catch (JSONException e) {
			this.exception=e;
		}
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
}