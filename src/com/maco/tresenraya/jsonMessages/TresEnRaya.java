package com.maco.tresenraya.jsonMessages;

import java.io.IOException;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.juegosEnGrupo.server.dominio.Game;
import com.maco.juegosEnGrupo.server.dominio.Match;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.server.domain.User;


public class TresEnRaya extends Match {
	public static int TRES_EN_RAYA = 1;
	public static char X='X', O='O', WHITE = ' ';
	private User userWithTurn;
	
	private char[][] squares;
	
	public TresEnRaya(Game game) {
		super(game);
		squares=new char[3][3];
		for (int row=0; row<3; row++)
			for (int col=0; col<3; col++)
				squares[row][col]=WHITE;
	}

	@Override
	public String toString() {
		String r="";
		for (int row=0; row<3; row++)
			for (int col=0; col<3; col++)
				r+=this.squares[row][col];
		r+="#" + this.players.get(0).getEmail() + "#";
		if (this.players.size()==2) {
			r+=this.players.get(1).getEmail() + "#";
			r+=this.userWithTurn.getEmail();
		}
		return r;
	}

	@Override
	public void postMove(User user, JSONObject jsoMovement) throws Exception {
		if (!jsoMovement.get("type").equals(TresEnRayaMovement.class.getSimpleName())) {
			throw new Exception("Unexpected type of movement");
		}
		int row=jsoMovement.getInt("row");
		int col=jsoMovement.getInt("col");
		JSONMessage result=null;
		if (this.squares[row][col]!=WHITE) {
			result=new ErrorMessage("Square busy");
		} else if (!this.isTheTurnOf(user)) {
			result=new ErrorMessage("It's not your turn");
		} 
		updateBoard(row, col, result);
	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result)
			throws JSONException, IOException {
		if (result==null) {
			if (this.userWithTurn.equals(this.players.get(0))) {
				this.squares[row][col]=X;
				this.userWithTurn=this.players.get(1);
			} else {
				this.squares[row][col]=O;
				this.userWithTurn=this.players.get(0);
			}
			result=new TresEnRayaBoardMessage(this.toString());
			//TODO: asignar mensaje
		}
	}

	@Override
	protected boolean isTheTurnOf(User user) {
		return this.userWithTurn.equals(user);
	}

	@Override
	protected void postAddUser(User user) {
		if (this.players.size()==2) {
			Random dado=new Random();
			JSONMessage jsTurn=new TresEnRayaWaitingMessage("Match ready. You have the turn.");
			JSONMessage jsNoTurn=new TresEnRayaWaitingMessage("Match ready. Wait for the opponent to move.");
			if (dado.nextBoolean()) {
				this.userWithTurn=this.players.get(0);
				//TODO: notificar a ambos jugadores que la partida esta lista
			} else {
				this.userWithTurn=this.players.get(1);
				
			}
			try {
				JSONMessage jsBoard=new TresEnRayaBoardMessage(this.toString());
				//TODO: enviar tablero a los dos jugadores
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JSONMessage jsm=new TresEnRayaWaitingMessage("Waiting for one more player");
			//TODO: notificar espera
		}
		
	}
}
