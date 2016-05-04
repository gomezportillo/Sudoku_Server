package edu.uclm.esi.common.server.domain;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.maco.juegosEnGrupo.server.dominio.Game;
import com.maco.juegosEnGrupo.server.dominio.Match;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.LoginMessageAnnouncement;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.persistence.Broker;

public class Manager {
	private static Manager yo;

	private Hashtable<String, User> usersByEmail;
	private Hashtable<Integer, User> usersById;
	private Hashtable<Integer, Game> games;

	private Manager() {
		this.usersByEmail=new Hashtable<String, User>();
		this.usersById=new Hashtable<Integer, User>();
		this.games=new Hashtable<Integer, Game>();

		try {
			this.loadAllGames();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Manager get() {
		if (yo==null)
			yo=new Manager();
		return yo;
	}

	public void add(User user, String ip) throws IOException {
		if (usersByEmail.get(user.getEmail())!=null) { 
			usersByEmail.remove(user.getEmail());
			usersById.remove(user.getId());
		}
		user.setIp(ip);
		usersByEmail.put(user.getEmail(), user);
		usersById.put(user.getId(), user);

		this.announceLogin(user);
	}	

	public void announceLogin(User new_user){
		LoginMessageAnnouncement lm = new LoginMessageAnnouncement(new_user.getEmail());

		//guardamos mensajes con el que luego notificaremos la llegada de uno nuevo jugador

		Enumeration<User> usuariosConectados=this.usersByEmail.elements();
		while(usuariosConectados.hasMoreElements()) {
			User userConectado = usuariosConectados.nextElement();
			userConectado.addMensajePendiente(lm);
		}
	}

	public User findUserByEmail(String email) {
		return this.usersByEmail.get(email);
	}

	public User closeSession(User user) throws SQLException {
		user=usersById.remove(user.getId());
		if (user!=null) {
			user.getDB().close();
		}
		return usersByEmail.remove(user.getEmail());
	}

	public User findUserById(int id) {
		return this.usersById.get(id);
	}

	public Vector<Game> loadAllGames() throws SQLException {
		Connection bd=Broker.get().getDBPrivilegiada();
		Vector<Game> result=new Vector<Game>();
		String sql="Select id, name, playersMin, playersMax from Game order by name";
		PreparedStatement ps=bd.prepareStatement(sql);
		
		ResultSet r=ps.executeQuery();
		while (r.next()) {
			Game g=new Game();
			int r_id_game = r.getInt(1);
			g.setId(r_id_game);
			g.setName(r.getString(2));
			g.setPlayersMin(r.getInt(3));
			g.setPlayersMax(r.getInt(4));
			result.add(g);
			if (this.games.get(g.getId())==null)
				this.games.put(g.getId(), g);
		}
		ps.close();
		
		return result;
	}

	public int add(int idGame, int idUser) throws Exception {
		Game g=this.games.get(idGame);
		if (g==null)
			throw new Exception("Unknown game");
		
		User user=this.findUserById(idUser);
		g.add(user);
		Match pendingMatch=g.findPendingMatch();
		if (pendingMatch==null) {
			pendingMatch=Match.build(g);
		}
		pendingMatch.add(user);
		g.add(pendingMatch);
		
		return pendingMatch.hashCode();
	}

	public Game findGameById(int id) {
		return this.games.get(id);
	}

	public Vector<JSONMessage> getMensajesPendientes(String email) {
		User user = this.usersByEmail.get(email);
		Vector<JSONMessage> v = user.getMensajesPendientes();
		user.borrarMensajesPendientes();
		
		return v;
	}
}
