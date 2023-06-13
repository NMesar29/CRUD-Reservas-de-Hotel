package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.hotel.jdbc.dao.RegistroHuespedDao;
import com.hotel.jdbc.dao.ReservaDao;
import com.hotel.jdbc.factory.ConnectionFactory;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private Integer tablaSeleccionada;
	boolean visibleReserva = true;
	boolean visibleHuesped = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 297, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		scroll_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tablaSeleccionada = 2;
				System.out.println(tablaSeleccionada);
			}
		});
		
		
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(false);
		scroll_tableHuespedes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tablaSeleccionada = 1;
				System.out.println(tablaSeleccionada);			
			}
		});
		metodoBuscar();
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				metodoBuscar();
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(visibleReserva = scroll_table.isVisible()) {
					metodoEditar(tbReservas, modelo, "reserva");
				};
				if(visibleHuesped = scroll_tableHuespedes.isVisible()) {
					metodoEditar(tbHuespedes, modeloHuesped, "huesped");
				};
				metodoBuscar();
			}
		});
		
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(visibleReserva = scroll_table.isVisible()) {
					metodoEliminar(tbReservas, modelo, "reserva");
				};
				if(visibleHuesped = scroll_tableHuespedes.isVisible()) {
					metodoEliminar(tbHuespedes, modeloHuesped, "huesped");
				};
				
				metodoBuscar();
				System.out.println("Huespedes Visible = " + visibleHuesped);
				System.out.println("Reservas Visible = " + visibleReserva);
				
			}

		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }
    
    
    private void metodoBuscar() {
    	if(modelo.getRowCount()>0 || modeloHuesped.getRowCount()>0) {
			for(int i=modelo.getRowCount()-1;i>-1;i--) {
				modelo.removeRow(i);
			}
			for(int i=modeloHuesped.getRowCount()-1;i>-1;i--) {
				modeloHuesped.removeRow(i);
			}
		}
		
		System.out.println("Se ha presionado Buscar");
		String palabraBusqueda = txtBuscar.getText().toString();
		System.out.println("Texto de busqueda" + palabraBusqueda);
		
		
		ReservaDao reservaDao = new ReservaDao(new ConnectionFactory().recuperaConexion());
		var reservas = reservaDao.listarBusqueda(palabraBusqueda.toString());
		reservas.forEach(reserva->modelo.addRow(
				new Object[] {
						reserva.getId(),
						reserva.getFechaEntrada(),
						reserva.getFechaSalida(),
						reserva.getValor(),
						reserva.getFormaPago()
				}));
		
		RegistroHuespedDao huespedDao = new RegistroHuespedDao(new ConnectionFactory().recuperaConexion());
		var huespedes = huespedDao.listarBusqueda(palabraBusqueda.toString());
		huespedes.forEach(huesped->modeloHuesped.addRow(
				new Object[] {
						huesped.getId(),
						huesped.getNombre(),
						huesped.getApellido(),
						huesped.getFechaNacimiento(),
						huesped.getNacionaldiad(),
						huesped.getTelefono(),
						huesped.getIdReserva()
				}
				));
	}
    
	private void metodoEliminar(JTable tabla, DefaultTableModel modelo, String tipoVentana) {
		
		if(tieneFilaSeleccionada(tabla)) {
			JOptionPane.showMessageDialog(this, "Por favor, elija un registro para eliminar");
			return;
		}

		Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
				.ifPresentOrElse(fila -> {
					Integer id;
					int cantidadEliminada;
					if(tipoVentana == "reserva") {
						ReservaDao reservaDao = new ReservaDao(new ConnectionFactory().recuperaConexion());
						id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
						cantidadEliminada = reservaDao.eliminar(id);
					}else {
						RegistroHuespedDao huespedDao = new RegistroHuespedDao(new ConnectionFactory().recuperaConexion());
						id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 6).toString());	
						cantidadEliminada = huespedDao.eliminar(id);
					};
					
					System.out.println(id);
					modelo.removeRow(tabla.getSelectedRow());
					JOptionPane.showMessageDialog(this, cantidadEliminada + "item eliminado con éxito!");
					
					
				}, ()->JOptionPane.showMessageDialog(this, "Por favor elige un registro"));

	}
	
	private void metodoEditar(JTable tabla, DefaultTableModel modelo, String tipoVentana) {
		if(tieneFilaSeleccionada(tabla)) {
			JOptionPane.showMessageDialog(this, "Por favor, elija un registro para eliminar");
			return;
		}
		
		Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(),tabla.getSelectedColumn()))
				.ifPresentOrElse(fila ->{
					Integer filasModificadas = null;
					if(tipoVentana=="reserva") {
						Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
						String fechaEntrada = (String) modelo.getValueAt(tabla.getSelectedRow(), 1);
						String fechaSalida = (String) modelo.getValueAt(tabla.getSelectedRow(), 2);
						String formaPago = (String) modelo.getValueAt(tabla.getSelectedRow(), 4);
						
						Long precioReserva =nuevoPrecio(fechaEntrada, fechaSalida);
						String valor =  precioReserva.toString();
						
						ReservaDao reservaDao = new ReservaDao(new ConnectionFactory().recuperaConexion());
						
						filasModificadas = reservaDao.modificar(id, fechaEntrada, fechaSalida, valor, formaPago);
					
					}else {
						Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
						String nombre = (String) modelo.getValueAt(tabla.getSelectedRow(), 1);
						String apellido = (String) modelo.getValueAt(tabla.getSelectedRow(), 2);
						String fechaNacimiento = (String) modelo.getValueAt(tabla.getSelectedRow(), 3);
						String nacionalidad = (String) modelo.getValueAt(tabla.getSelectedRow(), 4);
						String telefono = (String) modelo.getValueAt(tabla.getSelectedRow(), 5);
						Integer idReserva = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 6).toString());
						
						RegistroHuespedDao huespedDao = new RegistroHuespedDao(new ConnectionFactory().recuperaConexion());
						
						filasModificadas = huespedDao.modificar(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
					}
					
					JOptionPane.showMessageDialog(this, String.format("%d item modificado  con éxito!", filasModificadas));
				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elija un registro a modificar"));
	}

	private Long nuevoPrecio(String fechaEntrada, String fechaSalida) {
		SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaEntradaFormateada = null;
		Date fechaSalidaFormateada = null;
		try {
			fechaEntradaFormateada = formatoDate.parse(fechaEntrada);
			fechaSalidaFormateada = formatoDate.parse(fechaSalida);
		} catch (ParseException e) {
		}
		
		Long precioDia = (long) 70000;
		Long valorReserva = (long) 0;
		Long diasReserva = (fechaSalidaFormateada.getTime()-fechaEntradaFormateada.getTime())/(3600000*24);
		valorReserva = precioDia*diasReserva;
		System.out.println("La fecha de Entrada es: " + fechaEntradaFormateada + ", y su clase es: " + fechaEntradaFormateada.getClass());
		System.out.println("La fecha de Salida es: " + fechaSalidaFormateada + ", y su clase es: " + fechaSalidaFormateada.getClass());
		System.out.println("Nuevo valor de la reserva es: " + valorReserva);
		
		return valorReserva;
	}
    

	
	private boolean tieneFilaSeleccionada(JTable tabla) {
		return tabla.getSelectedRowCount()==0 || tabla.getSelectedColumnCount()==0;
	}
	
}
