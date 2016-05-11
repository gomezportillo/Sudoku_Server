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

	public void move() {
		int col = new Random().nextInt(9);
		int row = new Random().nextInt(9);
		int value = 0;
		int idUser = 0;
		int idMatch = 0;
		
		SudokuMovementMessage jsoMov = new SudokuMovementMessage(row, col, value, idUser, idMatch);
		try {
			this.match.move(this, jsoMov.toJSONObject());
		} catch (Exception e) {
			System.out.println("Algo salió mal. Parece que te va a tocar debugear otra vez");
		}		
	}
}
