package com.sudokus.dominio;

import org.json.JSONException;
import org.json.JSONObject;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class SudokuWinnerMessage extends JSONMessage{
	
	@JSONable
	private String winner;
	@JSONable
	private double time;

	public SudokuWinnerMessage(String winner) {
		super(false);
		this.setWinner(winner);
		this.setTime(System.currentTimeMillis());
	}

	public SudokuWinnerMessage(String winner, double time) {
		super(false);
		this.setWinner(winner);
		this.setTime(time);
	}

	public SudokuWinnerMessage (JSONObject jso) throws JSONException {
		this(jso.getString("winner"), jso.getDouble("time"));
	}

	public String getWinner() { return winner; }

	public void setWinner(String winner) { this.winner = winner; }
	
	private void setTime(double time){ this.time = time; }
	
	private double getTime() {
		return (System.currentTimeMillis() - this.time) / 1000;
	}
}