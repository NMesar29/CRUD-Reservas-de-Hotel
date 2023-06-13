package com.hotel.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hotel.jdbc.factory.ConnectionFactory;
import com.hotel.jdbc.modelo.Reserva;

public class ReservaDao {

	final private Connection con;	
	
	public ReservaDao(Connection con) {
		this.con = con;
	}
	
	
	public void crearReserva(Reserva reserva){
		try {
			final PreparedStatement statement = con.prepareStatement("INSERT INTO TBRESERVAS"
					+ "(FECHA_ENTRADA, FECHA_SALIDA, VALOR, FORMA_PAGO) "
					+ "VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			try(statement){
				ejecutarReserva(statement, reserva);
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	private void ejecutarReserva(PreparedStatement statement, Reserva reserva) throws SQLException {
		statement.setString(1, reserva.getFechaEntrada());
		statement.setString(2, reserva.getFechaSalida());
		statement.setString(3, reserva.getValor());
		statement.setString(4, reserva.getFormaPago());
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		
		try(resultSet){
			while(resultSet.next()) {
				reserva.setId(resultSet.getInt(1));
				System.out.println(String.format("Fue insertado el producto de ID %s",
						reserva));
			}
		}
	}

	public List<Reserva> listarBusqueda(String palabraBusqueda) {
		
		List<Reserva> resultadoBusqueda = new ArrayList<>();
		
		System.out.println("Creando conexión");
		try {
			System.out.println("Preparando Statement");
			PreparedStatement statement = con.prepareStatement("SELECT tbh.id, tbh.nombre, tbh.apellido, tbh.fecha_nacimiento, "
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
						Reserva fila = new Reserva(resultSet.getInt("id_reserva"),
								resultSet.getString("fecha_entrada"),
								resultSet.getString("fecha_salida"),
								resultSet.getString("valor"),
								resultSet.getString("forma_pago"));
						
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
		System.out.println("Ejecutando Eliminar ReservaDAO");
		MetodoEliminarDAO metodoEliminar = new MetodoEliminarDAO();
		return metodoEliminar.eliminarRegistro(con, id);
		
	}


	public Integer modificar(Integer id, String fechaEntrada, String fechaSalida, String valor, String formaPago) {
		try {
			PreparedStatement statement = con.prepareStatement("UPDATE tbreservas SET "
					+ "fecha_entrada = ?, "
					+ "fecha_salida = ?, "
					+ "valor = ?, "
					+ "forma_pago = ? "
					+ "WHERE id = ?");
			try(statement){
				statement.setString(1, fechaEntrada);
				statement.setString(2, fechaSalida);
				statement.setString(3, valor);
				statement.setString(4, formaPago);
				statement.setInt(5, id);
				
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
	
				return updateCount;
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
}
