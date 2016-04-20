package com.sudokus.dominio;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class SudokuMovementMessage  extends JSONMessage{
	
	@JSONable
	private int row;
    @JSONable
    private int col;
    @JSONable
    private int value;
    @JSONable
    private int idUser;
    @JSONable
    private int idMatch;
    
	
    public SudokuMovementMessage(int row, int col, int value, int idUser, int idMatch) {
		super(false);
		this.setRow(row);
		this.setCol(col);
		this.setValue(value);
		this.idUser = idUser;
		this.idMatch = idMatch;
	}
    
    public SudokuMovementMessage (JSONObject jso) throws JSONException {
        this(jso.getInt("row"), jso.getInt("col"), jso.getInt("value"), jso.getInt("idUser"), jso.getInt("idMatch"));
    }
    
	public int getRow() { return row; }

	public void setRow(int row) { this.row = row; }

	public int getCol() { return col; }
	
	public void setCol(int col) { this.col = col; }

	public int getValue() { return value; }

	public void setValue(int value) { this.value = value; }

}
