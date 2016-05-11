package com.pedroma.juegosEnGrupo.server.dominio;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.server.domain.User;

public class Game {
	private int id;
	private String name;
	private int playersMin, playersMax;
	private Hashtable<Integer, User> players;
	private Hashtable<Integer, Match> matches;
	
	public Game() {
		this.players=new Hashtable<Integer, User>();
		this.matches=new Hashtable<Integer, Match>();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPlayersMax(int playersMax) {
		this.playersMax = playersMax;
	}
	
	public void setPlayersMin(int playersMin) {
		this.playersMin = playersMin;
	}
	
	public int getPlayersMax() {
		return playersMax;
	}
	
	public int getPlayersMin() {
		return playersMin;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject jso=new JSONObject();
		jso.put("id", this.id);
		jso.put("name", this.name);
		jso.put("playersMin", this.playersMin);
		jso.put("playersMax", this.playersMax);
		return jso;
	}

	public void add(User user) {
		this.players.put(user.getId(), user);
	}

	public Match findPendingMatch() {
		Enumeration<Match> e=this.matches.elements();
		Match match;
		while (e.hasMoreElements()) {
			match=e.nextElement();
			if (!match.isComplete())
				return match;
		}
		return null;
	}

	public void add(Match match) {
		this.matches.put(match.hashCode(), match);
	}

	public void remove(Match match) {
		this.matches.remove(match.hashCode());
	}
	
	public Match findMatchById(int idMatch, int idUser) throws Exception {
		Match match=this.matches.get(idMatch);
		if (match==null)
			throw new Exception("Match not found");
		if (!match.isPlaying(idUser))
			throw new Exception("You are not playing this match");
		return match;
	}
	
	public Iterator<Match> getAllMatches(){
		 return this.matches.values().iterator();
	}
}
