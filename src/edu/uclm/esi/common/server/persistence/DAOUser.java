package edu.uclm.esi.common.server.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.uclm.esi.common.server.domain.User;


public class DAOUser {
	
	public static void registrar(String email, String pwd) throws SQLException {
		Connection bd=BrokerAlarcos.get().getDBPrivilegiada();
		try {
			String sql="{call insertarUsuario (?, ?, ?, ?, ?)}";
			CallableStatement cs=bd.prepareCall(sql);
			cs.setString(1, email);
			cs.setString(2, pwd);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			String exito=cs.getString(3);
			// q1 y q2 se ponen simplemente con fines ilustrativos, para que se vea el resultado de la transacción del procedimiento almacenado.
			String q1=cs.getString(4);		
			String q2=cs.getString(5);
			if (exito!=null && !(exito.equals("OK")))
				throw new SQLException(exito);
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			bd.close();
		}
	}

	public static Connection identificar(String email, String pwd) throws SQLException {
		return BrokerAlarcos.get().getDB(email, pwd);
	}
	
	public static void registrarConGoogle(String email) throws SQLException {
		Connection bd=BrokerAlarcos.get().getDBPrivilegiada();
		try {
			String sql="{call insertarUsuarioGoogle (?, ?, ?, ?, ?)}";
			CallableStatement cs=bd.prepareCall(sql);
			cs.setString(1, email);
			cs.setString(2, BrokerAlarcos.get().GOOGLE_PWD);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			String exito=cs.getString(3);
			// q1 y q2 se ponen simplemente con fines ilustrativos, para que se vea el resultado de la transacción del procedimiento almacenado.
			String q1=cs.getString(4);		
			String q2=cs.getString(5);
			if (exito!=null && !(exito.equals("OK")))
				throw new SQLException(exito);
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			bd.close();
		}
	}

	public static Connection identificarConGoogle(String email) throws SQLException {
		try {
			registrarConGoogle(email);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return BrokerAlarcos.get().getGoogleDB(email);
	}

	public static void select(Connection bd, String email, User jugador) throws SQLException {
		String sql="Select id, fechaDeAlta from User where email=?";
		PreparedStatement ps=bd.prepareStatement(sql);
		ps.setString(1, email);
		ResultSet r=ps.executeQuery();
		if (r.next()) {
			jugador.setId(r.getInt(1));
			jugador.setEmail(email);
			jugador.setFechaDeAlta(r.getDate(2));
		}
		ps.close();
	}
}
