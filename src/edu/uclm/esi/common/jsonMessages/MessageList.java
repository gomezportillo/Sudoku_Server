package edu.uclm.esi.common.jsonMessages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageList extends JSONMessage {
    @JSONable
    private JSONArray messages;

    public MessageList() {
        super(false);
        this.messages=new JSONArray();
    }

    public MessageList(JSONObject jso) {
        this();
        try {
            messages=jso.getJSONArray("messages");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void add(JSONObject jso) {
        messages.put(jso);
    }

    public int size() {
        return messages.length();
    }


    public JSONObject get(int i) {
        try {
            return messages.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
