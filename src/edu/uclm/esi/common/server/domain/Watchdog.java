package edu.uclm.esi.common.server.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.pedroma.juegosEnGrupo.server.dominio.Game;
import com.pedroma.juegosEnGrupo.server.dominio.Match;
import com.sudokus.dominio.Sudoku;

public class Watchdog implements Runnable {

	private final String[] names;
	private List<SudokuBot> players;
	private Manager manager;
	
	public Watchdog(Manager m){
		this.manager = m;
		players = new ArrayList<SudokuBot>();
		names = new String[]{"xXxMinecr69xXx@mail.es","_cod_TrickShooter_3_@mail.es","56byKiLLeR56@mail.es", "nAxXGaMerr@mail.es"};
	}

	public void run() {
		while (true) {
			try {
				
				//this.checkLastConnection();				

				this.checkWaitingPlayer();

				this.moveRobotPlayers();
				Thread.sleep(10000);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void checkLastConnection() {
		double tenSecondsAgo = System.currentTimeMillis() - 10000;
		Iterator<User> it = this.manager.getAllUsers();
		
		for (User user; it.hasNext();) {
			user = it.next();
			if (user.getLastConnection() < tenSecondsAgo) {
				try {
					this.manager.closeSession(user);
				} catch (SQLException e) {}
			}
		}
	}

	private void checkWaitingPlayer() {
		double tenSecondsAgo = System.currentTimeMillis() - 10000;
		Game g = this.manager.findGameById(3); 
		Iterator<Match> it = g.getAllMatches();
		
		for (Match match; it.hasNext();) {
			match = it.next();
			
			if(!match.isComplete() && match.getStartingTime() < tenSecondsAgo) { 
				SudokuBot bot = new SudokuBot(this.names[new Random().nextInt(4)]);
				this.players.add(bot);
				bot.addToMatch(match);
			}
		}	
	}
	
	private void moveRobotPlayers() {
		for (SudokuBot bot : this.players){
			bot.move();
		}
	}
}

