package com.sudokus.dominio;

import org.json.JSONException;
import org.json.JSONObject;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class SudokuRankingResponseMessage extends JSONMessage{
	@JSONable
	String rankings;

	public SudokuRankingResponseMessage(String r) {
		super(false);
		this.rankings = r;
	}

	public SudokuRankingResponseMessage (JSONObject jso) throws JSONException {
		this(jso.getString("rankings"));
	}

	public String getMensaje(){
		return this.rankings;
	}
}