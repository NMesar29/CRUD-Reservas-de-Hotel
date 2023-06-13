package com.hotel.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MetodoEliminarDAO {

	
	
	public int eliminarRegistro(Connection con, int id) {
		
		System.out.println("Pre ejecución método Eliminar");
		try {
			PreparedStatement statement = con.prepareStatement("DELETE tbhuespedes FROM tbhuespedes "
					+ "INNER JOIN tbreservas on tbhuespedes.id_reserva = tbreservas.id "
					+ "WHERE id_reserva = ?");
			try(statement){
				statement.setInt(1, id);
				statement.execute();
				
				return statement.getUpdateCount();
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
