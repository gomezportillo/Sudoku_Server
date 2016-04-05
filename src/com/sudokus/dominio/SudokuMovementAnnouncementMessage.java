package com.sudokus.dominio;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class SudokuMovementAnnouncementMessage  extends JSONMessage{
	@JSONable
	private String row;
    @JSONable
    private String col;
    @JSONable
    private String value;
	
    public SudokuMovementAnnouncementMessage(String row, String col, String value) {
		super(false);
		this.setRow(row);
		this.setCol(col);
		this.setValue(value);		
	}
    
    public SudokuMovementAnnouncementMessage (JSONObject jso) throws JSONException {
        this(jso.getString("row"), jso.getString("col"), jso.getString("value"));
    }
    
	public String getRow() { return row; }

	public void setRow(String row) { this.row = row; }

	public String getCol() { return col; }

	public void setCol(String col) { this.col = col; }

	public String getValue() { return value; }

	public void setValue(String value) { this.value = value; }

	
    

}
