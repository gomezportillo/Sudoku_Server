package com.sudokus.dominio;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.pedroma.juegosEnGrupo.server.dominio.Game;
import com.pedroma.juegosEnGrupo.server.dominio.Match;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.persistence.BrokerRankings;

public class Sudoku extends Match {
	public static int SUDOKU = 3;
	private String tablero;
	private String tableroSolucion;
	private double startingTime;

	public Sudoku(Game game) {
		super(game);
		this.generateRandomSudoku();
		this.startingTime = System.currentTimeMillis();
	}

	private void generateRandomSudoku() {
		String tablero_bueno = "003020600900305001001806400008102900700000008006708200002609500800203009005010300";

		this.tableroSolucion = "483921657967345821251876493548132976729564138136798245372689514814253769695417382";
		this.tablero = "483921657967345821251870493548132976729564138136798245372689514014253769695417382";
	}

	@Override
	protected void postAddUser(User user) {
		if (this.players.size()==2) {
			try {
				this.setTableroDeJugadores(this.tablero);

				User a = this.players.get(0);
				User b = this.players.get(1);

				JSONMessage jsBoardA = new SudokuBoardMessage(this.tablero, a.getEmail(), b.getEmail(), this.hashCode());
				a.addMensajePendiente(jsBoardA);

				JSONMessage jsBoardB = new SudokuBoardMessage(this.tablero, b.getEmail(), a.getEmail(), this.hashCode());
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

		if (row==-1 && col==-1) { //el jugador ha abandonado la partida
			this.finishMatchAndLogOut(user);
		}
		else {
			//informamos al contrincante del movimiento
			SudokuMovementAnnouncementMessage msg = new SudokuMovementAnnouncementMessage(row, col, idMatch);
			this.getOppositetUser(user).addMensajePendiente(msg);

			//actualizamos el tablero con el nuevo movimiento (comprobando si está resuelto)	
			SudokuMovementMessage mov = new SudokuMovementMessage(row, col, value, idUser, idMatch);
			updateBoard(row, col, mov);
		}
	}

	private void finishMatchAndLogOut(User user) {
		/**
		 * Este metodo se encarga de informar al contrincante de que ha ganado,
		 * terminar la partida y
		 * actualizar la bbdd con la victoria de este jugador  
		 */
		User opponent = this.getOppositetUser(user);
		
		BrokerRankings.get().addAVictory(opponent.getEmail());
		
		double finishTime = (System.currentTimeMillis() - this.startingTime) / 1000;
		SudokuWinnerMessage swm = new SudokuWinnerMessage(opponent.getEmail(), finishTime);
		opponent.addMensajePendiente(swm);

		Manager m = Manager.get();
		/**
		try {
			m.closeSession(opponent); //cerramos la sesion del jugador
		} catch (SQLException e) {}
		*/
		Game g = m.findGameById(Sudoku.SUDOKU); //borramos la partida del manager
		g.remove(this);
		
	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage jsm) throws JSONException, IOException {

		SudokuMovementMessage mov = (SudokuMovementMessage) jsm;

		User player = getUser(mov.getIdUser());

		this.updateTableroPlayer(player, mov.getRow(), mov.getCol(), mov.getValue());

		if (checkWinnerTablero(player) == true) { //tenemos ganador; mandamos el winnermessage a ambos jugadores
			BrokerRankings.get().addAVictory(player.getEmail());
			
			double finishTime = (System.currentTimeMillis() - this.startingTime) / 1000;
			SudokuWinnerMessage swm = new SudokuWinnerMessage(player.getEmail(), finishTime);
			this.players.get(0).addMensajePendiente(swm);
			this.players.get(1).addMensajePendiente(swm);
		}
	}

	private void updateTableroPlayer(User player, int row, int column, int value){

		String tablero = player.getTablero();

		int index = column+(9*row);
		String substring1 = tablero.substring(0, index);
		String substring2 = tablero.substring(index+1, this.tablero.length());
		String newValue = String.valueOf(value);

		player.setTablero(substring1 + newValue + substring2);
	}

	private boolean checkWinnerTablero(User player) {
		return this.tableroSolucion.equals(player.getTablero());
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

	public void setTableroDeJugadores(String casillas) { 
		this.players.get(0).setTablero(casillas);
		this.players.get(1).setTablero(casillas);
	}

	public String getCasillas() { return this.tablero; }

	@Override
	public double getStartingTime() { return this.startingTime; }
}

