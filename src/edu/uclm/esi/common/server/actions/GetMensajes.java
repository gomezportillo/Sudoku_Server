package edu.uclm.esi.common.server.actions;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.MessageList;
import edu.uclm.esi.common.server.domain.Manager;

public class GetMensajes extends JSONAction{

	private String email;
	private Vector<JSONMessage> mensajes;
	@Override
	protected String postExecute() {
		this.mensajes = Manager.get().getMensajesPendientes(this.email);
		return SUCCESS;
	}

	@Override
	public void setCommand(String cmd) {
		try{
			JSONObject jso = new JSONObject(cmd);
			this.email = jso.get("email").toString();
		}catch(Exception e){

		}
	}

	@Override
	public String getResultado() {
		JSONMessage resultado;
		if(this.exception != null) {
			resultado = new ErrorMessage(this.exception.getMessage());
			return resultado.toString(); 
		} else{
			MessageList ml = new MessageList();
			for (JSONMessage mensaje : this.mensajes) {
				ml.add(mensaje.toJSONObject());
			}
			//por aqui hay que limpiar los mensajes que tiene el jugador cuando los mandamos
			return ml.toString();
		}
	}
}
