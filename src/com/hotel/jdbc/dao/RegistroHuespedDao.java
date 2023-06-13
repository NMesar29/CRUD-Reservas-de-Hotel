package com.hotel.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.hotel.jdbc.modelo.Huesped;
import com.hotel.jdbc.modelo.Reserva;

public class RegistroHuespedDao {
	
	final private Connection con;
	private MetodoEliminarDAO metodoEliminar;
	
	
	public RegistroHuespedDao(Connection con) {
		this.con = con;
	}
	
	public void registrarHuespued(Huesped huesped) {
		try {
			final PreparedStatement statement = con.prepareStatement("INSERT INTO TBHUESPEDES"
					+ "(NOMBRE, APELLIDO, FECHA_NACIMIENTO,NACIONALIDAD,TELEFONO,ID_RESERVA) "
					+ "VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			try(statement){
				ejecutarRegistro(statement, huesped); 
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	private void ejecutarRegistro(PreparedStatement statement, Huesped huesped) throws SQLException {
		
		statement.setString(1, huesped.getNombre());
		statement.setString(2, huesped.getApellido());
		statement.setString(3, huesped.getFechaNacimiento());
		statement.setString(4, huesped.getNacionaldiad());
		statement.setString(5, huesped.getTelefono());
		statement.setString(6, huesped.getIdReserva());
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		try(resultSet){
			while(resultSet.next()) {
				huesped.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertado el producto de ID %s",
					huesped));
			}
		}
		
	}
	
	public List<Huesped> listarBusqueda(String palabraBusqueda) {
	
		List<Huesped> resultadoBusqueda = new ArrayList<>();
		
		System.out.println("Creando conexión");
		try {
			System.out.println("Preparando Statement");
			final PreparedStatement statement = con.prepareStatement("SELECT tbh.id, tbh.nombre, tbh.apellido, tbh.fecha_nacimiento, "
					+ "tbh.nacionalidad, tbh.telefono, "
					+ "tbr.fecha_entrada, tbr.fecha_salida, tbr.valor, tbr.forma_pago, tbr.id AS id_reserva "
					+ "FROM tbhuespedes AS tbh "
					+ "INNER JOIN tbreservas tbr ON tbh.id_reserva = tbr.id "
					+ "WHERE nombre= ? OR apellido= ? OR id_reserva= ?");
			try(statement){
				if(!palabraBusqueda.isEmpty()) {
					statement.setString(1, palabraBusqueda);
					statement.setString(2, palabraBusqueda);
					statement.setString(3, palabraBusqueda);
					System.out.println("Ejecutando Statement");
					statement.execute();
				}else {
					statement.execute("SELECT tbh.id, tbh.nombre, tbh.apellido, tbh.fecha_nacimiento, "
							+ "tbh.nacionalidad, tbh.telefono, "
							+ "tbr.fecha_entrada, tbr.fecha_salida, tbr.valor, tbr.forma_pago, tbr.id AS id_reserva "
							+ "FROM tbhuespedes AS tbh "
							+ "INNER JOIN tbreservas tbr ON tbh.id_reserva = tbr.id ");
				}
				
				
				System.out.println("Creando ResultSet");
				final ResultSet resultSet = statement.getResultSet();
				try(resultSet){
					while(resultSet.next()) {
						Huesped fila = new Huesped(resultSet.getInt("id"),
								resultSet.getString("nombre"),
								resultSet.getString("apellido"),
								resultSet.getString("fecha_nacimiento"),
								resultSet.getString("nacionalidad"),
								resultSet.getString("telefono"),
								resultSet.getString("id_reserva"));		
						
						resultadoBusqueda.add(fila);
					}
				}
			}	
			return resultadoBusqueda;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int eliminar(Integer id) {
		System.out.println("Ejecutando Eliminar HuespedDAO");
		MetodoEliminarDAO metodoEliminar = new MetodoEliminarDAO();
		return metodoEliminar.eliminarRegistro(con, id);	
	}

	public Integer modificar(Integer id, String nombre, String apellido, String fechaNacimiento, String nacionalidad,
			String telefono, Integer idReserva) {
		try {
			final PreparedStatement statement = con.prepareStatement("UPDATE tbhuespedes SET "
					+ "nombre = ?, "
					+ "apellido = ?, "
					+ "fecha_nacimiento = ?, "
					+ "nacionalidad = ?, "
					+ "telefono = ? "
					+ "WHERE id = ?");
			try(statement){
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				statement.setString(3, fechaNacimiento);
				statement.setString(4, nacionalidad);
				statement.setString(5, telefono);
				statement.setInt(6, id);
				
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				System.out.println("El nombre es: " +  nombre);
				return updateCount;
				
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}


}
