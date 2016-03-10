package com.sudokus.dominio;

import java.io.IOException;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.juegosEnGrupo.server.dominio.Game;
import com.maco.juegosEnGrupo.server.dominio.Match;
import com.maco.tresenraya.jsonMessages.TresEnRayaBoardMessage;
import com.maco.tresenraya.jsonMessages.TresEnRayaMovement;
import com.maco.tresenraya.jsonMessages.TresEnRayaWaitingMessage;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;

public class Sudoku extends Match {
	public static int SUDOKU = 3;
	private String casillas;

	public Sudoku(Game game) {
		super(game);
		casillas = generateRandomSudoku();
	}

	private String generateRandomSudoku() {
		//TODO: cargar el fichero sudokus.txt y elegir uno al azar
		return "003020600900305001001806400008102900700000008006708200002609500800203009005010300";
	}

	@Override
	protected void postAddUser(User user) {
		if (this.players.size()==2) {

			//TODO: notificar a ambos jugadores que la partida esta lista

			try {
				JSONMessage jsBoard=new TresEnRayaBoardMessage(this.toString());
				//TODO: enviar tablero a los dos jugadores
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//JSONMessage jsm=new SudokuWaitingMessage("Waiting for one more player");
			//TODO: notificar espera
		}
	}


	@Override
	public String toString() {
		String r = casillas;
		r+="#" + this.players.get(0).getEmail() + "#";

		if (this.players.size()==2) {
			r+=this.players.get(1).getEmail() + "#";
		}
		return r;
	}

	@Override
	protected void postMove(User user, JSONObject jsoMovement) throws Exception {
		//TODO: informar al otro usuario de que el contrincante ha movido
		//updateBoard(row, col, result);
	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result) throws JSONException, IOException {
		//TODO: actualizar el teclado con los nuevos datos
	}

	@Override
	protected boolean isTheTurnOf(User user) { // este metodo no se usa en los sudokus
		return true;
	}


}
