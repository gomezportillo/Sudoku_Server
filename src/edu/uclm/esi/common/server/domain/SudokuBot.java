package edu.uclm.esi.common.server.domain;

import java.util.Random;

import org.json.JSONObject;

import com.pedroma.juegosEnGrupo.server.dominio.Match;
import com.sudokus.dominio.SudokuMovementMessage;

public class SudokuBot extends User{

	private Match match;

	public SudokuBot( String email) {
		super(email);
	}

	public void addToMatch(Match match) {
		try {
			this.match = match;
			match.add(this);
		} catch (Exception e) {}
	}

	@SuppressWarnings("unused")
	public void move() {

		/**
		 * La clase sudoku va actualizando el tablero de los jugadores, por
		 * lo que en cada getTablero obtendremos nuestro tablero actualizad.
		 * Ahora probamos posiciones al azar hasta dar con una que aún sea 0.
		 */
		String current_tablero = this.getTablero(); 
		int col, row, index;
		char current_val;
		int n_trials = 0; //por limitar de algun modo el bucle infiniti
		
		do {
			col = new Random().nextInt(9);
			row = new Random().nextInt(9);
			index = row*9+col;
			current_val = current_tablero.charAt(index);
			n_trials += 1;
		} while (current_val != '0' && n_trials < 99);

		int value = 9; //this value does not matter
		int idUser = 0;
		int idMatch = 1; //this.match.hashCode();

		SudokuMovementMessage jsoMov = new SudokuMovementMessage(row, col, value, idUser, idMatch);
		try {
			this.match.move(this, jsoMov.toJSONObject());
		} catch (Exception e) {
			System.out.println("Algo salió mal. Parece que te va a tocar debugear OTRA VEZ");
		}		
	}
}
