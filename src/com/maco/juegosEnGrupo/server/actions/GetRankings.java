package com.maco.juegosEnGrupo.server.actions;

import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.sudokus.dominio.Sudoku;
import com.sudokus.dominio.SudokuRankingMessage;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.actions.JSONAction;
import edu.uclm.esi.common.server.persistence.BrokerRankings;

@SuppressWarnings("serial")
public class GetRankings extends JSONAction {
	private int idGame;
	private String rankings;

	@Override
	public String postExecute() {
		try {
			//Manager manager=Manager.get();
			//User user=manager.findUserById(this.idUser);
			if (this.idGame == Sudoku.SUDOKU) 
				this.rankings = this.getSudokuRankings();
			else
				this.rankings = "Aun no guardamos rankings de este tipo de juego";

			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			ActionContext.getContext().getSession().put("exception", e);
			return ERROR;
		}
	}

	private String getSudokuRankings() {
		BrokerRankings br = BrokerRankings.get();
		return br.getRankings();
	}

	@Override
	public String getResultado() {
		JSONMessage srm;
		if (this.exception!=null)
			srm=new ErrorMessage(this.exception.getMessage());
		else {
			srm = new SudokuRankingMessage(this.rankings);
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