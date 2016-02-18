package com.sudokus.dominio;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.juegosEnGrupo.server.dominio.Game;
import com.maco.juegosEnGrupo.server.dominio.Match;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;

public class Sudoku extends Match {

	public Sudoku(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void postAddUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isTheTurnOf(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void postMove(User user, JSONObject jsoMovement) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result) throws JSONException, IOException {
		// TODO Auto-generated method stub

	}

}
