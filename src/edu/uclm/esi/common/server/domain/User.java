package edu.uclm.esi.common.server.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.LoginMessageAnnouncement;
import edu.uclm.esi.common.server.persistence.DAOUser;

public class User {
	private int id;
	private String email;
	private java.sql.Date fechaDeAlta;
	private Connection db;
	private String ip;
	private String userType;
	private Vector<JSONMessage> mensajesPendientes;

	public User() {
		this.mensajesPendientes = new Vector<JSONMessage>();
	}

	
	public User(Connection bd, String email, String userType) throws SQLException {
		this();
		this.db=bd;
		DAOUser.select(bd, email, this);
		this.userType=userType;
	}
	
	
	public static Connection identify(String email, String pwd) throws SQLException {
		return DAOUser.identificar(email, pwd);
	}

	public static Connection identifyWithGoogle(String email) throws SQLException {
		return DAOUser.identificarConGoogle(email);
	}

	public static void insert(String email, String pwd) throws SQLException {
		DAOUser.registrar(email, pwd);
	}

	public String getEmail() {
		return email;
	}

	public java.sql.Date getFechaDeAlta() {
		return fechaDeAlta;
	}

	public void setFechaDeAlta(java.sql.Date fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Connection getDB() {
		return db;
	}
	
	public String getUserType() {
		return userType;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject jso=new JSONObject();
		jso.put("type", "OK");
		jso.put("id", this.id);
		jso.put("email", this.email);
		return jso;
	}

	public void setIp(String ip) {
		this.ip=ip;
	}

	public String getIp() {
		return this.ip;
	}


	public Vector<JSONMessage> getMensajesPendientes() {
		return this.mensajesPendientes;
	}


	public void addMensajePendiente(JSONMessage jsm) {
		this.mensajesPendientes.add(jsm);
		
	}
}
