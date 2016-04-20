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
	private String tableroPlayer1;
	private String tableroPlayer2;

	public Sudoku(Game game) {
		super(game);
		this.setCasillas(generateRandomSudoku());
	}

	private String generateRandomSudoku() {
		//TODO: cargar uno de los fichero sudokuXX.txt al azar (prioridad 0)
		return "003020600900305001001806400008102900700000008006708200002609500800203009005010300";
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
		//TODO: informar al otro usuario de que el contrincante ha movido
		//updateBoard(row, col, result);
		//a partir de aqui estoy trabajando offline; no puedo probar si lo estoy haciendo bien

		int row = jsoMovement.getInt("row");
		int col = jsoMovement.getInt("col");
		SudokuMovementAnnouncementMessage msg = new SudokuMovementAnnouncementMessage(row, col);

		user.addMensajePendiente(msg); //es a este usuario o al otro?
	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result) throws JSONException, IOException {
		//TODO: actualizar el tablero del jugador con los nuevos cambios
		//a partir de aqui estoy trabajando offline; no puedo probar si lo estoy haciendo bien

		User player = null; //obtenemos el player que hace el movimiento del JSONMessage
		int value = 0;
		
		int playerInt;
		if (player.getId()==this.players.get(0).getId()) 
			playerInt = 1;
		else //es el otro jugador
			playerInt = 2;

		if(this.updateTableroPlayer(playerInt, row, col, value) == true) {
			return;
			//TODO: tenemos un ganador
		}
	}

	@Override
	protected boolean isTheTurnOf(User user) { //este metodo no se usa en los sudokus (pq siempre es el turno de user)
		return true;
	}

	@Override
	public String toString() {
		String player1 = this.players.get(0).getEmail();
		String player2 = this.players.get(1).getEmail();

		return "Partida de Sudoku: " + player1 + " VS. " + player2;
	}

	public String getCasillas() { return casillas; }
	public String getTableroPlayer1() { return tableroPlayer1; }
	public String getTableroPlayer2() { return tableroPlayer2; }

	public void setCasillas(String casillas) { 
		this.casillas = casillas;
		this.setTableroPlayer1(casillas);
		this.setTableroPlayer2(casillas);
	}

	public void setTableroPlayer1(String tableroPlayer1) {
		this.tableroPlayer1 = tableroPlayer1;
	}

	public void setTableroPlayer2(String tableroPlayer2) {
		this.tableroPlayer2 = tableroPlayer2;
	}

	private boolean updateTableroPlayer(int player, int row, int column, int value){
		String tablero = player==1 ? this.tableroPlayer1 : this.tableroPlayer2; 

		int index = row*column-1;
		String substring1 = tablero.substring(0, index-1);
		String substring2 = tablero.substring(index, this.tableroPlayer1.length()-1);
		String newValue = String.valueOf(value);

		tablero = substring1 + newValue + substring2;

		return comprobarTableroResuelto(this.tableroPlayer1);

	}


	//http://codereview.stackexchange.com/questions/46033/sudoku-checker-in-java
	private boolean comprobarTableroResuelto(String tableroPlayer12) {
		int [][] tablero = convertirTableroAIntVector(tableroPlayer12);

		for (int i = 0; i < 9; i++) {

			int[] row = new int[9];
			int[] square = new int[9];
			int[] column = tablero[i].clone();

			for (int j = 0; j < 9; j ++) {
				row[j] = tablero[j][i];
				square[j] = tablero[(i / 3) * 3 + j / 3][i * 3 % 9 + j % 3];
			}
			if (!(validar(column) && validar(row) && validar(square)))
				return false;
		}
		return true;
	}

	private boolean validar(int[] check) {
		int i = 0;
		Arrays.sort(check);
		for (int number : check) {
			if (number != ++i)
				return false;
		}
		return true;
	}

	private int[][] convertirTableroAIntVector(String tableroString){
		int [][] tableroInt = new int[8][8];		
		int factor;

		for (int i=0; i<9; i++) {
			factor = 9*i;
			for (int j=0; i<9; i++) {
				tableroInt [i][j] = tableroString.charAt(j+factor);
			}
		}
		return tableroInt; 
	}

}

