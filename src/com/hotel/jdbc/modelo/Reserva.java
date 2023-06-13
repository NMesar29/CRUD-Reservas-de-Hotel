package com.hotel.jdbc.modelo;

import java.sql.Date;

public class Reserva {
	private Integer id;
	private String fechaEntrada;
	private String fechaSalida;
	private String valor;
	private String formaPago;
	
	
	public Reserva(java.util.Date fechaEntrada, java.util.Date fechaSalida, String valor, String formaPago) {
		this.fechaEntrada = (new java.sql.Date(fechaEntrada.getTime())).toString();
		this.fechaSalida = (new java.sql.Date(fechaSalida.getTime())).toString();
		this.valor = valor;
		this.formaPago = formaPago;
	}
	
	//Constructor para Busqueda
	public Reserva(Integer id, String fechaEntrada, String fechaSalida, String valor, String formaPago) {
		super();
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}





	public Reserva(int int1, Date date, Date date2, String string, String string2) {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public String getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	
	@Override
	public String toString() {
		return String.format("{Id: %s, Fecha de Entrada: %s, "
				+ "Fecha de Salida: %s, Precio: %s, Forma de pago: %s}", 
			this.id,
			this.fechaEntrada,
			this.fechaSalida,
			this.valor,
			this.formaPago);
	}
	
}
