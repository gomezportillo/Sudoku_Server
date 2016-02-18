package com.maco.tresenraya.jsonMessages;

import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class TresEnRayaBoardMessage extends JSONMessage {
	@JSONable
	private String squares;
	@JSONable
	private String player1;
	@JSONable
	private String player2;
	@JSONable
	private String userWithTurn;

	public TresEnRayaBoardMessage(String board) throws JSONException {
		super(false);
		StringTokenizer st=new StringTokenizer(board, "#");
		this.squares=st.nextToken();
		this.player1=st.nextToken();
		if (st.hasMoreTokens()) {
			this.player2=st.nextToken();
			userWithTurn=st.nextToken();
		}
	}
	
	public TresEnRayaBoardMessage(JSONObject jso) throws JSONException {
		super(false);
		this.squares=jso.getString("squares");
		this.player1=jso.getString("player1");
		if (jso.optString("player2").length()>0) {
			this.player2=jso.getString("player2");
			this.userWithTurn=jso.getString("userWithTurn");
		}
	}

	public String getSquares() {
		return squares;
	}
	
	public String getPlayer1() {
		return player1;
	}
	
	public String getPlayer2() {
		return player2;
	}
	
	public String getUserWithTurn() {
		return userWithTurn;
	}
}
