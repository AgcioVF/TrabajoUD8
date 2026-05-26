package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Utils.MensajesUtils;
import control.Conexion;
import models.EstudianteModel;
import services.EstudianteService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MenuUserView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JButton bCrear, bEliminar, bSalir;
	private JTable tabla;
	private List<EstudianteModel> listaEstudiantes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuUserView frame = new MenuUserView();
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
	public MenuUserView() {
		setTitle("Users");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(230, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		bCrear = new JButton("Crear");
		bCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CrearUserView();
				dispose();
			}
		});
		bCrear.setToolTipText("Despliega el menu de creación de usuarios");
		bCrear.setBackground(new Color(153, 217, 251));
		bCrear.setContentAreaFilled(false);
		bCrear.setOpaque(true);
		bCrear.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bCrear.setFont(new Font("Arial", Font.PLAIN, 15));
		bCrear.setBounds(65, 353, 100, 50);
		contentPane.add(bCrear);

		bEliminar = new JButton("Eliminar");
		bEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarEstudiante();
			}
		});
		bEliminar.setToolTipText("Borra el elemento seleccionado");
		bEliminar.setBackground(new Color(153, 217, 251));
		bEliminar.setContentAreaFilled(false);
		bEliminar.setOpaque(true);
		bEliminar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bEliminar.setFont(new Font("Arial", Font.PLAIN, 15));
		bEliminar.setBounds(370, 353, 100, 50);
		contentPane.add(bEliminar);

		JLabel lblFiltrar = new JLabel("Filtrar por nombre:");
		lblFiltrar.setFont(new Font("Arial", Font.PLAIN, 15));
		lblFiltrar.setBounds(58, 14, 124, 31);
		contentPane.add(lblFiltrar);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				actualizarTabla(textField.getText());
			}
		});
		textField.setToolTipText("Muestra aquellos estudiantes que coinciden con el campo de texto");
		textField.setFont(new Font("Arial", Font.PLAIN, 15));
		textField.setBounds(189, 14, 157, 31);
		contentPane.add(textField);

		bSalir = new JButton("Salir");
		bSalir.setToolTipText("Regresa al menu estudiantes");
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
		bSalir.setToolTipText("Cierra la ventana");

		JScrollPane panelTabla = new JScrollPane();
		panelTabla.setBounds(10, 59, 516, 275);

		tabla = new JTable();
		tabla.setFont(new Font("Arial", Font.PLAIN, 15));
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setDefaultEditor(Object.class, null);
		panelTabla.setViewportView(tabla);
		mostrarTabla();

		contentPane.add(panelTabla);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void mostrarTabla() {
		try {
			listaEstudiantes = EstudianteService.getAllEstudiantes(Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
		} catch (SQLException e) {
			e.printStackTrace();
			MensajesUtils.errorBaseDatosTabla();
		}
		DefaultTableModel modelo = new DefaultTableModel();

		modelo.setColumnIdentifiers(new Object[] { "Nombre", "Email", "Edad", "Role" });

		for (EstudianteModel e : listaEstudiantes) {
			modelo.addRow(new Object[] { e.getNombre(), e.getEmail(), e.getEdad(), e.getRole() });
		}

		tabla.setModel(modelo);
	}

	public void actualizarTabla(String filtrar) {
		try {
			listaEstudiantes = EstudianteService.getEstudiantePorNombre(filtrar, Conexion.obtener());
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			MensajesUtils.errorConexion();
		} catch (SQLException e) {
			e.printStackTrace();
			MensajesUtils.errorBaseDatosTabla();
		}
		DefaultTableModel modelo = new DefaultTableModel();

		modelo.setColumnIdentifiers(new Object[] { "Nombre", "Email", "Edad", "Role" });

		for (EstudianteModel e : listaEstudiantes) {
			modelo.addRow(new Object[] { e.getNombre(), e.getEmail(), e.getEdad(), e.getRole() });
		}

		tabla.setModel(modelo);
	}

	public void eliminarEstudiante() {
		int fila = tabla.getSelectedRow();

		if (fila >= 0) {
			int confirmacion = JOptionPane.showConfirmDialog(null, "¿QUIERE PROCEDER CON LA ELIMINACIÓN?",
					"CONFIRMAR ELIMINACIÓN", JOptionPane.YES_NO_OPTION);

			EstudianteModel estudiante = listaEstudiantes.get(fila);
			if (confirmacion == 0) {
				try {
					EstudianteService.eliminarEstudiante(estudiante, Conexion.obtener());
				} catch (ClassNotFoundException e) {
					System.out.println(e);
					MensajesUtils.errorConexion();
					return;
				} catch (SQLException e) {
					System.out.println(e);
					MensajesUtils.errorFuncional("BORRAR EL ESTUDIANTE");
					return;
				}
				mostrarTabla();
			}
		} else {
			MensajesUtils.seleccionarFila();
		}
	}
}
