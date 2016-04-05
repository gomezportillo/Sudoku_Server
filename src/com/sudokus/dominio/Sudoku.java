package com.sudokus.dominio;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.juegosEnGrupo.server.dominio.Game;
import com.maco.juegosEnGrupo.server.dominio.Match;

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
		//TODO: cargar uno de los fichero sudokuXX.txt al azar
		return "003020600900305001001806400008102900700000008006708200002609500800203009005010300";
	}

	@Override
	protected void postAddUser(User user) {
		if (this.players.size()==2) {
			try {
				User a = this.players.get(0);
				User b = this.players.get(1);
				
				//
				JSONMessage jsBoardA =new SudokuBoardMessage(this.casillas, a.getEmail(), b.getEmail(), this.hashCode());
				a.addMensajePendiente(jsBoardA);
				
				JSONMessage jsBoardB =new SudokuBoardMessage(this.casillas, b.getEmail(), a.getEmail(), this.hashCode());
				b.addMensajePendiente(jsBoardB);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}



	@Override
	protected void postMove(User user, JSONObject jsoMovement) throws Exception {
		//TODO: informar al otro usuario de que el contrincante ha movido
		//updateBoard(row, col, result);
	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result) throws JSONException, IOException {
		//TODO: actualizar el teclado con los nuevos datos e informar al contrincante del cambio
	}

	@Override
	protected boolean isTheTurnOf(User user) { //este metodo no se usa en los sudokus (ergo siempre es el turno de user)
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}


}
