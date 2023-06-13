package com.hotel.jdbc.modelo;

public class Huesped {
	
	private Integer id;
	private String nombre;
	private String apellido;
	private String fechaNacimiento;
	private String nacionalidad;
	private String telefono;
	private String idReserva;
	
	public Huesped(String nombre, String apellido, java.util.Date fechaNacimiento, String nacionalidad, String telefono, String  idReserva) {
		this.nombre= nombre;
		this.apellido = apellido;
		this.fechaNacimiento = (new java.sql.Date(fechaNacimiento.getTime())).toString();
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
		
	}
	
	public Huesped(int id, String nombre, String apellido, String fechaNacimiento, String nacionalidad, String telefono, String idReserva) {
		this.id = id;
		this.nombre= nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getNacionaldiad() {
		return nacionalidad;
	}
	public void setNacionaldiad(String nacionaldiad) {
		this.nacionalidad = nacionaldiad;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(String idReserva) {
		this.idReserva = idReserva;
	}
	
	@Override
	public String toString() {
		return String.format("{Id: %s, Nombre: %s, "
				+ "Apellido: %s, Fecha Nacimiento: %s, Nacionalidad: %s, Telefono: %s, Id Reserva: %s}", 
			this.id,
			this.nombre,
			this.apellido,
			this.fechaNacimiento,
			this.nacionalidad,
			this.telefono,
			this.idReserva);
	}
	
}
