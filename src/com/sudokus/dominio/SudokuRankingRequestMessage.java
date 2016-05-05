package com.sudokus.dominio;

import org.json.JSONException;
import org.json.JSONObject;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class SudokuRankingRequestMessage extends JSONMessage{
	@JSONable
	int idGame;
	
    public SudokuRankingRequestMessage(int idGame) {
        super(false);
        this.idGame = idGame;
    }
    
    public SudokuRankingRequestMessage (JSONObject jso) throws JSONException {
        this(jso.getInt("idGame"));
    }
}