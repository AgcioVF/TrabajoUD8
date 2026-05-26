package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Utils.MensajesUtils;
import control.Conexion;
import models.ErasmusModel;
import services.ErasmusService;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.awt.Color;

public class MenuErasmusView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFiltrar;
	private JTable tabla;
	private List<ErasmusModel> listaErasmus;
	private JButton bCrear, bEliminar, bInscribir, bMostrar, bDetalles;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuErasmusView frame = new MenuErasmusView();
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
	public MenuErasmusView() {
		setResizable(false);
		setTitle("Erasmus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		bCrear = new JButton("Crear");
		bCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CrearErasmusView();
				dispose();
			}
		});
		bCrear.setToolTipText("Despliega el menu de creación de erasmus");
		bCrear.setBackground(new Color(153, 217, 251));
		bCrear.setContentAreaFilled(false);
		bCrear.setOpaque(true);
		bCrear.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bCrear.setFont(new Font("Arial", Font.PLAIN, 15));
		bCrear.setBounds(464, 27, 100, 50);
		contentPane.add(bCrear);

		bEliminar = new JButton("Eliminar");
		bEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarErasmus();
			}
		});
		bEliminar.setToolTipText("Borra el elemento seleccionado");
		bEliminar.setBackground(new Color(153, 217, 251));
		bEliminar.setContentAreaFilled(false);
		bEliminar.setOpaque(true);
		bEliminar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bEliminar.setFont(new Font("Arial", Font.PLAIN, 15));
		bEliminar.setBounds(464, 104, 100, 50);
		contentPane.add(bEliminar);

		JLabel lblFiltrar = new JLabel("Filtrar por nombre:");
		lblFiltrar.setFont(new Font("Arial", Font.PLAIN, 15));
		lblFiltrar.setBounds(58, 14, 124, 31);
		contentPane.add(lblFiltrar);

		textFiltrar = new JTextField();
		textFiltrar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String cadenaAFiltrar = textFiltrar.getText();
				actualizarTabla(cadenaAFiltrar);
			}
		});

		textFiltrar.setToolTipText("Muestra aquellos Erasmus que coinciden con el campo de texto");
		textFiltrar.setFont(new Font("Arial", Font.PLAIN, 15));
		textFiltrar.setBounds(189, 14, 157, 31);
		contentPane.add(textFiltrar);
		textFiltrar.setColumns(10);

		JButton bSalir = new JButton("Salir");
		bSalir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new MenuAdminView();
				dispose();
			}
		});
		bSalir.setToolTipText("Regresa al menu principal");
		bSalir.setBackground(new Color(186, 199, 252));
		bSalir.setContentAreaFilled(false);
		bSalir.setOpaque(true);
		bSalir.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bSalir.setFont(new Font("Arial", Font.PLAIN, 10));
		bSalir.setBounds(230, 378, 75, 25);
		contentPane.add(bSalir);

		JScrollPane panelTabla = new JScrollPane();
		panelTabla.setBounds(10, 59, 433, 309);

		tabla = new JTable();
		tabla.setFont(new Font("Arial", Font.PLAIN, 15));
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setDefaultEditor(Object.class, null);
		panelTabla.setViewportView(tabla);
		mostrarTabla();

		contentPane.add(panelTabla);

		bInscribir = new JButton("Inscribir");
		bInscribir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opcionesInscribir();
			}
		});
		bInscribir.setBackground(new Color(153, 217, 251));
		bInscribir.setContentAreaFilled(false);
		bInscribir.setOpaque(true);
		bInscribir.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bInscribir.setFont(new Font("Arial", Font.PLAIN, 15));
		bInscribir.setBounds(464, 181, 100, 50);
		contentPane.add(bInscribir);

		bMostrar = new JButton("Asistentes");
		bMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				opcionesMostrar();
			}
		});
		bMostrar.setToolTipText("Muestra los asistentes del erasmus");
		bMostrar.setBackground(new Color(153, 217, 251));
		bMostrar.setContentAreaFilled(false);
		bMostrar.setOpaque(true);
		bMostrar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bMostrar.setFont(new Font("Arial", Font.PLAIN, 14));
		bMostrar.setBounds(464, 258, 100, 50);
		contentPane.add(bMostrar);

		bDetalles = new JButton("Detalles");
		bDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detalles();
			}
		});
		bDetalles.setToolTipText("Muestra los datos del eramus");
		bDetalles.setBackground(new Color(153, 217, 251));
		bDetalles.setContentAreaFilled(false);
		bDetalles.setOpaque(true);
		bDetalles.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bDetalles.setFont(new Font("Arial", Font.PLAIN, 15));
		bDetalles.setBounds(464, 335, 100, 50);
		contentPane.add(bDetalles);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void mostrarTabla() {
		try {
			listaErasmus = ErasmusService.getAllErasmus(Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorBaseDatosTabla();
			return;
		}
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new Object[] { "DESTINO", "UNIVERSIDAD", "FECHA" });

		for (ErasmusModel e : listaErasmus) {
			modelo.addRow(new Object[] { e.getDestino(), e.getUniversidad(), e.getFecha() });
		}

		tabla.setModel(modelo);
	}

	public void actualizarTabla(String filtrar) {
		try {
			listaErasmus = ErasmusService.getErasmusPorDestino(filtrar, Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
			return;
		} catch (SQLException e) {
			System.out.println(e);
			MensajesUtils.errorBaseDatosTabla();
			return;
		}
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(new Object[] { "DESTINO", "UNIVERSIDAD", "FECHA" });

		for (ErasmusModel e : listaErasmus) {
			modelo.addRow(new Object[] { e.getDestino(), e.getUniversidad(), e.getFecha() });
		}

		tabla.setModel(modelo);
	}

	public void eliminarErasmus() {
		int fila = tabla.getSelectedRow();
		if (fila >= 0) {
			int confirmacion = MensajesUtils.confirmarEliminarErasmus();
			if (confirmacion == 0) {
				try {
					ErasmusService.eliminarErasmus(listaErasmus.get(fila), Conexion.obtener());
				} catch (ClassNotFoundException e) {
					System.out.println(e);
					MensajesUtils.errorConexion();
					return;
				} catch (SQLException e) {
					System.out.println(e);
					MensajesUtils.errorEliminarErasmus();
					return;
				}
				mostrarTabla();
			}
		} else {
			MensajesUtils.seleccionarFila();
		}
	}

	public void opcionesInscribir() {
		int fila = tabla.getSelectedRow();
		if (fila >= 0) {
			ErasmusModel erasmusSelec = listaErasmus.get(fila);

			if (erasmusSelec.getAsistentes() >= erasmusSelec.getCapacidad()) {
				MensajesUtils.erasmusCompleto();
				return;
			}
			new InscribirView(erasmusSelec);
			dispose();
		} else {
			MensajesUtils.seleccionarFila();
		}
	}

	public void opcionesMostrar() {
		int fila = tabla.getSelectedRow();
		if (fila >= 0) {
			ErasmusModel erasmusSelec = listaErasmus.get(fila);
			if (erasmusSelec.getAsistentes() <= 0) {
				MensajesUtils.erasmusVacio();
				return;
			} else {
				new CancelarView(erasmusSelec);
				dispose();
			}
		} else {
			MensajesUtils.seleccionarFila();
		}
	}

	public void detalles() {
		int fila = tabla.getSelectedRow();
		if (fila >= 0) {
			ErasmusModel erasmusSelec = listaErasmus.get(fila);
			new DetallesErasmusView(erasmusSelec);
			dispose();
		} else {
			MensajesUtils.seleccionarFila();
		}
	}

}
