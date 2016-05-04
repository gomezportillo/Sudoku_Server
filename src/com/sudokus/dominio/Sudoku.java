package com.sudokus.dominio;

import java.io.IOException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.juegosEnGrupo.server.dominio.Game;
import com.maco.juegosEnGrupo.server.dominio.Match;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.User;

public class Sudoku extends Match {
	public static int SUDOKU = 3;
	private String casillas;
	private String casillasSolucion;

	public Sudoku(Game game) {
		super(game);
		generateRandomSudoku();
	}

	private void generateRandomSudoku() {
		this.casillasSolucion = "483921657967345821251876493548132976729564138136798245372689514814253769695417382";
		this.casillas = "003020600900305001001806400008102900700000008006708200002609500800203009005010300";
	}

	@Override
	protected void postAddUser(User user) {
		if (this.players.size()==2) {
			try {
				User a = this.players.get(0);
				User b = this.players.get(1);

				JSONMessage jsBoardA = new SudokuBoardMessage(this.casillas, a.getEmail(), b.getEmail(), this.hashCode());
				a.addMensajePendiente(jsBoardA);

				JSONMessage jsBoardB = new SudokuBoardMessage(this.casillas, b.getEmail(), a.getEmail(), this.hashCode());
				b.addMensajePendiente(jsBoardB);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
	}

	@Override
	protected void postMove(User user, JSONObject jsoMovement) throws Exception {
		//informa al otro usuario de que el contrincante ha movido y actualiza el tablero

		int row = jsoMovement.getInt("row");
		int col = jsoMovement.getInt("col");
		int idMatch = jsoMovement.getInt("idMatch");
		int idUser = jsoMovement.getInt("idUser");
		int value = jsoMovement.getInt("value");
		
		SudokuMovementAnnouncementMessage msg = new SudokuMovementAnnouncementMessage(row, col, idMatch);
		this.getOppositetUser(user).addMensajePendiente(msg);
		
		SudokuMovementMessage mov = new SudokuMovementMessage(row, col, value, idUser, idMatch);
		
		updateBoard(row, col, mov);
	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage jsm) throws JSONException, IOException {

		SudokuMovementMessage mov = (SudokuMovementMessage) jsm;
		
		User player = getUser(mov.getIdUser());

		this.updateTableroPlayer(player, mov.getCol(), mov.getCol(), mov.getValue());

		if (checkWinnerTablero(player) == true) { //tenemos ganador; mandamos el winnermessage a ambos jugadores
			SudokuWinnerMessage swm = new SudokuWinnerMessage(player.getEmail());
			this.players.get(0).addMensajePendiente(swm);
			this.players.get(1).addMensajePendiente(swm);
		}
	}

	private void updateTableroPlayer(User player, int row, int column, int value){

		String tablero = player.getTablero();
		
		int index = row*column-1;
		String substring1 = tablero.substring(0, index-1);
		String substring2 = tablero.substring(index, this.casillas.length()-1);
		String newValue = String.valueOf(value);

		player.setTablero(substring1 + newValue + substring2);
	}
	
	private boolean checkWinnerTablero(User player) {
		return this.casillasSolucion.equals(player.getTablero());
	}

	private User getUser(int idUser) {
		if(this.players.get(0).getId() == idUser) {
			return this.players.get(0);
		}
		return this.players.get(1);
	}
	
	private User getOppositetUser(User user) {
		if(this.players.get(0).getId() == user.getId()) {
			return this.players.get(1);
		}
		return this.players.get(0);
	}

	@Override
	protected boolean isTheTurnOf(User user) { //esta funcion no se usa en los sudokus (pq siempre es el turno de user)
		return true;
	}

	@Override
	public String toString() {
		String player1 = this.players.get(0).getEmail();
		String player2 = this.players.get(1).getEmail();

		return "Partida de Sudoku: " + player1 + " VS. " + player2;
	}

	public void setCasillas(String casillas) { 
		this.casillas = casillas;
		this.players.get(0).setTablero(casillas);
		this.players.get(1).setTablero(casillas);
	}
	
	public String getCasillas() { return casillas; }

}

