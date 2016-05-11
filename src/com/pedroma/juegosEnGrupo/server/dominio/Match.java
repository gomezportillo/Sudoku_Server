package com.pedroma.juegosEnGrupo.server.dominio;

import java.io.IOException;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.tresenraya.jsonMessages.TresEnRaya;
import com.sudokus.dominio.Sudoku;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;

public abstract class Match {
	protected Vector<User> players;
	protected Game game;

	public Match(Game game) {
		this.game=game;
		this.players=new Vector<User>();
	}

	public boolean isComplete() {
		return this.players.size()==game.getPlayersMin();
	}

	public void add(User user) throws Exception {
		if (this.players.contains(user))
			throw new Exception(user.getEmail() + " is already playing this match");
		this.players.add(user);
		postAddUser(user);
	}

	protected abstract void postAddUser(User user) throws Exception;

	public boolean isPlaying(int idUser) {
		for (User player : players)
			if (player.getId()==idUser)
				return true;
		return false;
	}

	public static Match build(Game game) {
		switch(game.getId()) {
			case (1):
				return new TresEnRaya(game);
			case (3):
				return new Sudoku(game);
			default:
				return null;
		}
	}

	@Override
	public abstract String toString();

	public void move(User user, JSONObject jsoMovement) throws Exception {
		if (!isTheTurnOf(user))
			throw new Exception("It's not your turn");

		postMove(user, jsoMovement);
	}
	protected abstract void postMove(User user, JSONObject jsoMovement) throws Exception;

	protected abstract boolean isTheTurnOf(User user);
	protected abstract void updateBoard(int row, int col, JSONMessage result) throws JSONException, IOException;
	public abstract double getStartingTime();
}
