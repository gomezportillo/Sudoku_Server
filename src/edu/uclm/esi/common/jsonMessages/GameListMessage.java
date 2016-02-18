package edu.uclm.esi.common.jsonMessages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class GameListMessage extends JSONMessage {
	@JSONable
	private JSONArray games;

	public GameListMessage(String games) throws JSONException {
		super(false);
		this.games=new JSONArray(games);
	}
	
	public GameListMessage(JSONArray games) {
		super(false);
		this.games=games;
	}
	
	public String toString() {
		JSONObject result=new JSONObject();
		try {
			result.put("games", games);
			result.put("type", getType());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}

	public JSONArray getGames() {
		return games;
	}
}
